/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.file.modular.controller;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import io.entframework.kernel.file.modular.service.SysFileInfoService;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.rule.util.HttpServletUtil;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件信息管理
 * <p>
 * 该模块简要说明： 1.文件支持版本朔源，每次操作均会产生数据 2.文件支持版本回滚，升级后可选择某一版本进行回退
 * <p>
 * 文件管理接口有两种使用方式： 1.合同文件场景：文件必须保持原样，合同内容升级不影响已签署合同，业务需要关联文件ID<br>
 * 文件升级不会对之前的数据造成影响 2.UI文件场景：文件升级后业务所有关联的文件全部升级，业务需要关联文件CODE<br>
 *
 * @date 2020/12/27 13:39
 */
@RestController
@ApiResource(name = "文件信息相关接口")
public class SysFileController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * <p>
     * 支持上传到数据库，参数fileLocation传递5即可
     * <p>
     * fileLocation传递其他值或不传值，不能决定文件上传到本地还是阿里云或其他地方
     *
     * @date 2020/12/27 13:17
     */
    @PostResource(name = "上传文件", path = "/sys-file-info/upload", requiredPermission = false)
    public ResponseData<SysFileInfoResponse> upload(@RequestPart("file") MultipartFile file,
            @Validated(BaseRequest.add.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.uploadFile(file, sysFileInfoRequest);
        return ResponseData.ok(fileUploadInfoResult);
    }

    /**
     * 富文本tinymce上传文件 需要返回格式 //json格式 { "location": "folder/sub-folder/new-location.png" }
     *
     * @date 2021/1/17 11:17
     */
    @PostResource(name = "上传文件", path = "/sys-file-info/tinymce-upload", requiredPermission = false)
    public Map<String, String> tinymceUpload(@RequestPart("file") MultipartFile file,
            SysFileInfoRequest sysFileInfoRequest) {
        Map<String, String> resultMap = new HashMap<>(1);
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N);
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.uploadFile(file, sysFileInfoRequest);
        resultMap.put("location",
                FileConstants.FILE_PUBLIC_PREVIEW_URL + "?fileId=" + fileUploadInfoResult.getFileId());
        return resultMap;
    }

    /**
     * 私有文件预览
     *
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "私有文件预览", path = FileConstants.FILE_PRIVATE_PREVIEW_URL, requiredPermission = false)
    public void privatePreview(@Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.Y);
        this.sysFileInfoService.preview(sysFileInfoRequest, response);
    }

    /**
     * 公有文件预览
     *
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有文件预览", path = FileConstants.FILE_PUBLIC_PREVIEW_URL, requiredPermission = false,
            requiredLogin = false)
    public void publicPreview(@Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N);
        this.sysFileInfoService.preview(sysFileInfoRequest, response);
    }

    /**
     * 通用文件预览，通过传bucket名称和object名称
     *
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "文件预览，通过bucketName和objectName", path = FileConstants.FILE_PREVIEW_BY_OBJECT_NAME,
            requiredPermission = false, requiredLogin = false)
    public void previewByBucketNameObjectName(
            @Validated(SysFileInfoRequest.previewByObjectName.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoService.previewByBucketAndObjName(sysFileInfoRequest, response);
    }

    /**
     * 私有文件下载
     *
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "私有文件下载", path = "/sys-file-info/private-download", requiredPermission = false)
    public void privateDownload(@Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.Y);
        this.sysFileInfoService.download(sysFileInfoRequest, response);
    }

    /**
     * 公有文件下载
     *
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有文件下载", path = "/sys-file-info/public-download", requiredLogin = false,
            requiredPermission = false)
    public void publicDownload(@Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        sysFileInfoRequest.setSecretFlag(YesOrNotEnum.N);
        this.sysFileInfoService.download(sysFileInfoRequest, response);
    }

    /**
     * 替换文件
     * <p>
     * 注意：调用本接口之后还需要调用确认接口，本次替换操作才会生效
     *
     * @date 2020/12/16 15:34
     */
    @PostResource(name = "替换文件", path = "/sys-file-info/update", requiredPermission = false)
    public ResponseData<SysFileInfoResponse> update(@RequestPart("file") MultipartFile file,
            @Validated(BaseRequest.update.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.updateFile(file, sysFileInfoRequest);
        return ResponseData.ok(fileUploadInfoResult);
    }

    /**
     * 版本回退
     *
     * @date 2020/12/16 15:34
     */
    @PostResource(name = "版本回退", path = "/sys-file-info/version-back", requiredPermission = false)
    public ResponseData<SysFileInfoResponse> versionBack(
            @Validated(SysFileInfoRequest.versionBack.class) SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.versionBack(sysFileInfoRequest);
        return ResponseData.ok(fileUploadInfoResult);
    }

    /**
     * 根据附件IDS查询附件信息
     * @param fileIds 附件IDS
     * @return 附件返回类
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "根据附件IDS查询附件信息", path = "/sys-file-info/get-file-info-list-by-file-ids",
            requiredPermission = false)
    public ResponseData<List<SysFileInfoResponse>> getFileInfoListByFileIds(
            @RequestParam(value = "fileIds") String fileIds) {
        List<SysFileInfoResponse> list = this.sysFileInfoService.getFileInfoListByFileIds(fileIds);
        return ResponseData.ok(list);
    }

    /**
     * 公有打包下载文件
     *
     * @date 2020/12/27 13:17
     */
    @GetResource(name = "公有打包下载文件", path = "/sys-file-info/public-packaging-download", requiredPermission = false,
            requiredLogin = false)
    public void publicPackagingDownload(@RequestParam(value = "fileIds") String fileIds) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        this.sysFileInfoService.packagingDownload(fileIds, YesOrNotEnum.N.getValue(), response);
    }

    /**
     * 私有打包下载文件
     *
     * @date 2020/12/27 13:18
     */
    @GetResource(name = "私有打包下载文件", path = "/sys-file-info/private-packaging-download", requiredPermission = false)
    public void privatePackagingDownload(@RequestParam(value = "fileIds") String fileIds) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        this.sysFileInfoService.packagingDownload(fileIds, YesOrNotEnum.Y.getValue(), response);
    }

    /**
     * 删除文件信息（真删除文件信息）
     *
     * @date 2020/11/29 11:19
     */
    @PostResource(name = "删除文件信息（真删除文件信息）", path = "/sys-file-info/delete-really", requiredPermission = false)
    public ResponseData<Void> deleteReally(
            @RequestBody @Validated(BaseRequest.delete.class) SysFileInfoRequest sysFileInfoRequest) {
        this.sysFileInfoService.deleteReally(sysFileInfoRequest);
        return ResponseData.OK_VOID;
    }

    /**
     * 分页查询文件信息表
     *
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "分页查询文件信息表", path = "/sys-file-info/file-info-list-page", requiredPermission = false)
    public ResponseData<PageResult<SysFileInfoResponse>> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
        return ResponseData.ok(this.sysFileInfoService.fileInfoListPage(sysFileInfoRequest));
    }

    /**
     * 查看详情文件信息表
     *
     * @date 2020/11/29 11:29
     */
    @GetResource(name = "查看详情文件信息表", path = "/sys-file-info/detail", requiredPermission = false)
    public ResponseData<SysFileInfo> detail(
            @Validated(BaseRequest.detail.class) SysFileInfoRequest sysFileInfoRequest) {
        return ResponseData.ok(sysFileInfoService.detail(sysFileInfoRequest));
    }

}
