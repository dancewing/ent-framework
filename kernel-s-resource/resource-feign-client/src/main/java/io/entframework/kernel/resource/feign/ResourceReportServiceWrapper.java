package io.entframework.kernel.resource.feign;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.entframework.kernel.rule.function.Try;
import io.entframework.kernel.scanner.api.ResourceReportApi;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceParam;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ResourceReportServiceWrapper implements ResourceReportApi {
    private final ResourceReportApi feignClient;
    public ResourceReportServiceWrapper(ResourceReportApi feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public List<SysResourcePersistencePojo> reportResourcesAndGetResult(ResourceParam reportResourceReq) {
        return Try.call(() -> feignClient.reportResourcesAndGetResult(reportResourceReq)).ifFailure(e -> {
            //如果失败则放到线程中去处理
            RefreshManager refreshManager = new RefreshManager(reportResourceReq);
            refreshManager.start();
        }).toOptional().orElse(Collections.emptyList());
    }

    /**
     * 日志刷新管理器
     * <p>
     * 该类暂存所有将要写出到磁盘的日志，使用内存缓冲区减少对磁盘IO的操作
     * <p>
     * 该类维护一个最大日志数和一个刷新日志间隔，满足任意一个条件即可触发从内存写出日志到磁盘的操作
     *
     * @date 2020/10/31 15:05
     */
    class RefreshManager extends Thread {

        /**
         * Hutool日志对象
         */
        private final Log log = LogFactory.get();

        /**
         * 刷新日志间隔(默认3秒),单位毫秒
         */
        private final long sleepTime;

        /**
         * 刷新数据时间标志,每次刷新都记录当前的时间戳，方便定时刷新准确判断上次刷新和本次刷新的时间间隔
         */
        private final AtomicLong refreshMark = new AtomicLong();

        /**
         * 异步操作是否完成
         */
        public AtomicBoolean success = new AtomicBoolean(false);

        /**
         * 消息总数,队列的size方法会遍历一遍队列，所以自己维护大小
         */
        public AtomicInteger count = new AtomicInteger(0);

        /**
         * 未处理日志的消息队列
         */
        private final Queue<ResourceParam> queue = new ConcurrentLinkedQueue<>();

        public RefreshManager(ResourceParam reportResourceReq) {
            this.sleepTime = 3000;
            int queueDataCount = count.get();

            // 如果是第一条消息，刷新一次refreshMark
            if (queueDataCount == 0) {
                refreshMark.getAndSet(System.currentTimeMillis());
            }

            queue.offer(reportResourceReq);
            count.incrementAndGet();
        }

        /**
         * 往队列内新增一条数据
         *
         * @param logRecordDTO 日志对象
         * @date 2020/10/31 14:59
         */
        public void put(ResourceParam logRecordDTO) {

            int queueDataCount = count.get();

            // 如果是第一条消息，刷新一次refreshMark
            if (queueDataCount == 0) {
                refreshMark.getAndSet(System.currentTimeMillis());
            }

            queue.offer(logRecordDTO);
            count.incrementAndGet();
        }

        /**
         * 刷新日志到磁盘的操作
         *
         * @date 2020/10/31 15:48
         */
        private void refresh() {
            long current = System.currentTimeMillis();

            int total = count.get();
            if (total == 0) {
                return;
            }
            // 缓冲队列中所有的数据
            ResourceParam data = queue.poll();
            try {
                ResourceReportServiceWrapper.this.feignClient.reportResourcesAndGetResult(data);
                // 让睡眠线程本次不要再调本方法，睡眠至下次看refreshMark的值再决定要不要调用本方法
                refreshMark.getAndSet(current);
                success.getAndSet(true);
            } catch (Exception e) {

                // 有异常把日志刷回队列，不要丢掉(这里可能会导致日志顺序错乱)
                queue.offer(data);
                // 打印日志
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }

        }

        /**
         * 日志数据定时执行器
         * <p>
         * 用于定时检测是否可以发送数据
         *
         * @date 2020/10/31 15:57
         */
        private void timing() {
            try {
                // 如果是激活状态，且消息数大于零，且符合上次调用refresh方法到目前时间的间隔，那就调用一次refresh方法
                if ((refreshMark.get() + sleepTime) <= System.currentTimeMillis() && !success.get()) {
                    refresh();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
            }
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            try {
                for (; ; ) {
                    // 定时任务监听器
                    timing();
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
