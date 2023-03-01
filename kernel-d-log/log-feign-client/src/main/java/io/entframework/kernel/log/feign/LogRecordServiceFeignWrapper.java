package io.entframework.kernel.log.feign;

import io.entframework.kernel.log.api.LogRecordApi;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import io.entframework.kernel.rule.function.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Slf4j
public class LogRecordServiceFeignWrapper implements LogRecordApi {

    private final LogRecordApi feignClient;

    public LogRecordServiceFeignWrapper(LogRecordApi feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public boolean add(LogRecordDTO logRecordDTO) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        return Try.call(() -> feignClient.add(logRecordDTO)).ifFailure(e -> {
            log.error("feignClient.add error: ", e);
        }).toOptional().orElse(false);
    }

    @Override
    public boolean addAsync(LogRecordDTO logRecordDTO) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        return Try.call(() -> feignClient.addAsync(logRecordDTO)).ifFailure(e -> {
            log.error("feignClient.addAsync error: ", e);
        }).toOptional().orElse(false);
    }

    @Override
    public boolean addBatch(List<LogRecordDTO> logRecords) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        return Try.call(() -> feignClient.addBatch(logRecords)).ifFailure(e -> {
            log.error("feignClient.addBatch error: ", e);
        }).toOptional().orElse(false);
    }

}
