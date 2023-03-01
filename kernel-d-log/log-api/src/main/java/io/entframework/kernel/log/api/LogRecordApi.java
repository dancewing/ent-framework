/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.log.api;

import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 日志记录的api，只用于记录日志
 *
 * @date 2020/10/27 16:19
 */
public interface LogRecordApi {

    /**
     * 同步记录日志
     * @param logRecordDTO 日志记录的参数
     * @date 2020/10/27 17:38
     */
    @PostMapping(path = "/client/sys-log/add")
    boolean add(@RequestBody LogRecordDTO logRecordDTO);

    /**
     * 异步同步记录日志 调用本方法直接返回结果之后再异步记录日志
     * @param logRecordDTO 日志记录的参数
     * @date 2020/10/27 17:38
     */
    @PostMapping(path = "/client/sys-log/add-async")
    boolean addAsync(@RequestBody LogRecordDTO logRecordDTO);

    /**
     * 批量同步记录日志
     * @param logRecords 待输出日志列表
     * @date 2020/11/2 下午2:59
     */
    @PostMapping(path = "/client/sys-log/add-batch")
    boolean addBatch(@RequestBody List<LogRecordDTO> logRecords);

}
