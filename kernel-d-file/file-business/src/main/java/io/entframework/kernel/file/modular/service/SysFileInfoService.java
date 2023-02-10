/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.file.modular.service;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.file.api.FileInfoClientApi;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件信息表 服务类
 *
 * @date 2020/6/7 22:15
 */
public interface SysFileInfoService extends BaseService<SysFileInfoRequest, SysFileInfoResponse, SysFileInfo>, FileInfoClientApi {

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件id
     * @return 文件信息结果集
     * @date 2020/11/29 14:16
     */
    SysFileInfoResponse getFileInfoResult(Long fileId);

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file 要上传的文件
     * @return 文件上传信息体
     * @date 2020/12/16 15:47
     */
    SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest);

    /**
     * 更新文件，返回文件的唯一标识
     *
     * @param file               要上传的文件
     * @param sysFileInfoRequest 如果是替换，需带上code以便版本升级
     * @return 文件上传返回体
     * @date 2020/12/16 15:46
     */
    SysFileInfoResponse updateFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件下载
     *
     * @param sysFileInfoRequest 文件下载参数
     * @param response           响应结果
     * @date 2020/11/29 13:39
     */
    void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 删除文件信息（真删除文件信息）
     *
     * @param sysFileInfoRequest 删除参数
     * @date 2020/11/29 13:44
     */
    ResponseData<Void> deleteReally(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 分页查询文件信息表
     *
     * @param sysFileInfoRequest 查询参数
     * @return 查询分页结果
     * @date 2020/11/29 14:09
     */
    PageResult<SysFileInfoResponse> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 打包下载文件
     *
     * @param fileIds    文件ID集合，使用,号分割
     * @param secretFlag 是否私有文件
     * @param response   响应结果
     * @date 2020/12/7 下午4:47
     */
    void packagingDownload(String fileIds, String secretFlag, HttpServletResponse response);

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIds 附件IDS
     * @date 2020/12/27 12:52
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds);

    /**
     * 文件预览
     *
     * @date 2020/11/29 11:29
     */
    void preview(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 版本回退
     *
     * @date 2020/12/27 13:49
     */
    SysFileInfoResponse versionBack(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 文件预览，通过参数中传递fileBucket和fileObjectName
     *
     * @param sysFileInfoRequest 文件预览参数
     * @param response           响应结果
     * @date 2020/11/29 13:45
     */
    void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response);

    /**
     * 查看详情文件信息表
     *
     * @param sysFileInfoRequest 查看参数
     * @return 文件信息
     * @date 2020/11/29 14:08
     */
    SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest);

    /**
     * 根据附件IDS查询附件信息
     *
     * @param fileIdList 文件ID列表
     * @date 2020/12/27 12:57
     */
    List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList);

}
