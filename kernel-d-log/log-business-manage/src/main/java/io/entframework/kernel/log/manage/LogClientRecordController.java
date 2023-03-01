/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.log.manage;

import io.entframework.kernel.log.api.LogRecordApi;
import io.entframework.kernel.log.api.pojo.record.LogRecordDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;

@RestController
public class LogClientRecordController {

    @Resource
    private LogRecordApi logRecordApi;

    @PostMapping(path = "/client/sys-log/add")
    public Boolean add(@RequestBody LogRecordDTO logRecordDTO) {
        return logRecordApi.add(logRecordDTO);
    }

    @PostMapping(path = "/client/sys-log/add-async")
    public boolean addAsync(@RequestBody LogRecordDTO logRecordDTO) {
        return logRecordApi.addAsync(logRecordDTO);
    }

    @PostMapping(path = "/client/sys-log/add-batch")
    public boolean addBatch(@RequestBody List<LogRecordDTO> logRecords) {
        return logRecordApi.addBatch(logRecords);
    }

}
