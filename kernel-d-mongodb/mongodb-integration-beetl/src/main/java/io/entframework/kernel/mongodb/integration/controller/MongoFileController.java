/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.mongodb.integration.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.mongodb.api.MongoFileApi;
import io.entframework.kernel.mongodb.file.entity.MongoFileEntity;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Mongodb文件管理接口控制器
 *
 * @date 2021/03/31 17:28
 */
@RestController
@ApiResource(name = "Mongodb文件接口控制器")
public class MongoFileController {

    @Resource
    private MongoFileApi<MongoFileEntity, String> mongoFileApi;

    /**
     * 新增文件
     *
     * @date 2021/03/31 17:28
     */
    @PostResource(name = "Mongodb文件新增", path = "/view/mongodb/file/add")
    public ResponseData<MongoFileEntity> mongodbFileAdd(@RequestPart("file") MultipartFile file) {
        return ResponseData.ok(mongoFileApi.saveFile(file));
    }

    /**
     * 根据id删除文件
     *
     * @date 2021/03/31 17:28
     */
    @PostResource(name = "Mongodb文件删除", path = "/view/mongodb/file/del")
    public ResponseData<Void> mongodbFileDel(@RequestParam String id) {
        mongoFileApi.removeFile(id);
        return ResponseData.OK_VOID;
    }

    /**
     * 获取分页文件列表
     *
     * @date 2021/03/31 17:28
     */
    @GetResource(name = "Mongodb文件列表", path = "/view/mongodb/file/list")
    public ResponseData<PageResult<MongoFileEntity>> mongodbFileList(MongoFileEntity mongoFileEntity) {
        return ResponseData.ok(mongoFileApi.getFilesByPage(mongoFileEntity));
    }

    /**
     * 根据id下载文件
     *
     * @date 2021/03/31 17:28
     */
    @GetResource(name = "Mongodb文件下载", path = "/view/mongodb/file/down")
    public ResponseEntity mongodbFileDown(@RequestParam String id) throws UnsupportedEncodingException {
        Optional<MongoFileEntity> file = mongoFileApi.getFileById(id);
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; fileName=" + URLEncoder.encode(file.get().getName(), "utf-8"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream").body(file.get().getContent());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

}
