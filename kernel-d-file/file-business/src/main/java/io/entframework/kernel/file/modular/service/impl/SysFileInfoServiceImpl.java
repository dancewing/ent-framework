/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.enums.FileStatusEnum;
import io.entframework.kernel.file.api.exception.FileException;
import io.entframework.kernel.file.api.exception.enums.FileExceptionEnum;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.file.api.util.DownloadUtil;
import io.entframework.kernel.file.api.util.PdfFileTypeUtil;
import io.entframework.kernel.file.api.util.PicFileTypeUtil;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import io.entframework.kernel.file.modular.entity.SysFileInfoDynamicSqlSupport;
import io.entframework.kernel.file.modular.factory.FileInfoFactory;
import io.entframework.kernel.file.modular.factory.FileOperatorFactory;
import io.entframework.kernel.file.modular.service.SysFileInfoService;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class SysFileInfoServiceImpl extends BaseServiceImpl<SysFileInfoRequest, SysFileInfoResponse, SysFileInfo>
		implements SysFileInfoService {

	public SysFileInfoServiceImpl() {
		super(SysFileInfoRequest.class, SysFileInfoResponse.class, SysFileInfo.class);
	}

	@Resource
	private FileOperatorFactory fileOperatorFactory;

	@Resource
	private FileServerProperties fileServerProperties;

	@Resource
	private LoginUserApi loginUserApi;

	@Override
	public SysFileInfoResponse getFileInfoResult(Long fileId) {

		// 查询库中文件信息
		SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
		sysFileInfoRequest.setFileId(fileId);
		SysFileInfo sysFileInfo = this.querySysFileInfo(sysFileInfoRequest);
		FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfo.getFileLocation());
		// 获取文件字节码
		byte[] fileBytes;
		try {
			fileBytes = fileOperatorApi.getFileBytes(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName());
		}
		catch (Exception e) {
			log.error("获取文件流异常，具体信息为：{}", e.getMessage());
			throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR, e.getMessage());
		}

		// 设置文件字节码
		SysFileInfoResponse sysFileInfoResult = this.converterService.convert(sysFileInfo, getResponseClass());
		sysFileInfoResult.setFileBytes(fileBytes);

		return sysFileInfoResult;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

		// 文件请求转换存入数据库的附件信息
		SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);

		// 默认版本号从1开始
		sysFileInfo.setFileVersion(1);

		// 文件编码生成
		sysFileInfo.setFileCode(IdWorker.getId());

		// 保存附件到附件信息表
		this.getRepository().insert(sysFileInfo);

		// 返回文件信息体
		SysFileInfoResponse fileUploadInfoResult = this.converterService.convert(sysFileInfo, getResponseClass());
		FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfo.getFileLocation());
		// 拼接文件可直接访问的url
		String fileAuthUrl;
		if (YesOrNotEnum.Y == sysFileInfoRequest.getSecretFlag()) {
			fileAuthUrl = fileOperatorApi.getFileAuthUrl(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName(),
					fileServerProperties.getFileTimeoutSeconds() * 1000, loginUserApi.getToken());
		}
		else {
			fileAuthUrl = fileOperatorApi.getFileUnAuthUrl(sysFileInfo.getFileBucket(),
					sysFileInfo.getFileObjectName());
		}
		fileUploadInfoResult.setFileUrl(fileAuthUrl);

		return fileUploadInfoResult;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysFileInfoResponse updateFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {

		Long fileCode = sysFileInfoRequest.getFileCode();

		// 转换存入数据库的附件信息
		SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);
		sysFileInfo.setDelFlag(YesOrNotEnum.Y);
		sysFileInfo.setFileCode(fileCode);

		// 查询该code下的最新版本号附件信息
		SysFileInfo query = new SysFileInfo();
		query.setFileCode(fileCode);
		query.setDelFlag(YesOrNotEnum.N);
		query.setFileStatus(FileStatusEnum.NEW);
		List<SysFileInfo> fileInfos = this.getRepository().selectBy(query);
		if (ObjectUtil.isEmpty(fileInfos)) {
			throw new FileException(FileExceptionEnum.NOT_EXISTED);
		}
		SysFileInfo fileInfo = fileInfos.get(0);
		// 设置版本号在原本的基础上加一
		sysFileInfo.setFileVersion(fileInfo.getFileVersion() + 1);

		// 存储新版本文件信息
		this.getRepository().updateByPrimaryKey(sysFileInfo);

		// 返回文件信息体
		SysFileInfoResponse fileUploadInfoResult = this.converterService.convert(sysFileInfo, getResponseClass());
		return fileUploadInfoResult;
	}

	@Override
	public void download(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

		// 根据文件id获取文件信息结果集
		SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

		// 如果文件加密等级不符合，文件不允许被访问
		if (YesOrNotEnum.Y == sysFileInfoResponse.getSecretFlag()
				&& YesOrNotEnum.N == sysFileInfoRequest.getSecretFlag()) {
			throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
		}

		DownloadUtil.download(sysFileInfoResponse.getFileOriginName(), sysFileInfoResponse.getFileBytes(), response);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData<Void> deleteReally(SysFileInfoRequest sysFileInfoRequest) {

		// 查询该Code的所有历史版本
		SelectDSLCompleter completer = c -> c
				.where(SysFileInfoDynamicSqlSupport.fileCode, SqlBuilder.isEqualTo(sysFileInfoRequest.getFileCode()))
				.or(SysFileInfoDynamicSqlSupport.fileId, SqlBuilder.isEqualTo(sysFileInfoRequest.getFileId()));
		List<SysFileInfo> fileInfos = this.getRepository().select(getEntityClass(), completer);
		// 批量删除
		fileInfos.forEach(sysFileInfo -> {
			this.getRepository().deleteByPrimaryKey(getEntityClass(), sysFileInfo.getFileId());
		});

		// 删除具体文件
		for (SysFileInfo fileInfo : fileInfos) {
			FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(fileInfo.getFileLocation());
			// 如果文件是在数据库存储，则特殊处理
			fileOperatorApi.deleteFile(fileInfo.getFileBucket(), fileInfo.getFileObjectName());
		}
		return ResponseData.OK_VOID;
	}

	@Override
	public PageResult<SysFileInfoResponse> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
		PageResult<SysFileInfoResponse> page = this.page(sysFileInfoRequest);

		// 拼接图片url地址
		for (SysFileInfoResponse sysFileInfoListResponse : page.getItems()) {
			// 判断是否是可以预览的文件
			if (PicFileTypeUtil.getFileImgTypeFlag(sysFileInfoListResponse.getFileSuffix())) {
				sysFileInfoListResponse.setFileUrl(this.getFileAuthUrl(sysFileInfoListResponse.getFileId()));
			}
		}

		return page;
	}

	@Override
	public void packagingDownload(String fileIds, String secretFlag, HttpServletResponse response) {

		// 获取文件信息
		List<Long> fileIdList = Arrays.stream(fileIds.split(",")).map(s -> Long.parseLong(s.trim())).toList();
		List<SysFileInfoResponse> fileInfoResponseList = this.getFileInfoListByFileIds(fileIdList);

		// 输出流等信息
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(bos);

		try {
			for (int i = 0; i < fileInfoResponseList.size(); i++) {
				SysFileInfoResponse sysFileInfoResponse = fileInfoResponseList.get(i);
				if (ObjectUtil.isNotEmpty(sysFileInfoResponse)) {
					String fileOriginName = sysFileInfoResponse.getFileOriginName();
					// 判断公有文件下载时是否包含私有文件
					if (secretFlag.equals(YesOrNotEnum.N.getValue())
							&& !secretFlag.equals(sysFileInfoResponse.getSecretFlag().getValue())) {
						throw new FileException(FileExceptionEnum.SECRET_FLAG_INFO_ERROR, fileOriginName);
					}

					FileOperatorApi fileOperatorApi = fileOperatorFactory
							.getFileOperatorApi(sysFileInfoResponse.getFileLocation());
					byte[] fileBytes = fileOperatorApi.getFileBytes(sysFileInfoResponse.getFileBucket(),
							sysFileInfoResponse.getFileObjectName());
					ZipEntry entry = new ZipEntry(i + 1 + "." + fileOriginName);
					entry.setSize(fileBytes.length);
					zip.putNextEntry(entry);
					zip.write(fileBytes);
				}
			}
			zip.finish();

			// 下载文件
			DownloadUtil.download(DateUtil.now() + "-打包下载" + FileConstants.FILE_POSTFIX_SEPARATOR + "zip",
					bos.toByteArray(), response);
		}
		catch (Exception e) {
			log.error("获取文件流异常，具体信息为：{}", e.getMessage());
			throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR, e.getMessage());
		}
		finally {
			try {
				zip.closeEntry();
				zip.close();
				bos.close();
			}
			catch (IOException e) {
				log.error("关闭数据流失败，具体信息为：{}", e.getMessage());
			}
		}
	}

	@Override
	public List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds) {
		List<Long> fileIdList = Arrays.stream(fileIds.split(",")).map(s -> Long.parseLong(s.trim())).toList();
		return this.getFileInfoListByFileIds(fileIdList);
	}

	@Override
	public void preview(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

		// 根据文件id获取文件信息结果集
		SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());

		// 如果文件加密等级不符合，文件不允许被访问
		if (YesOrNotEnum.Y == sysFileInfoResponse.getSecretFlag()
				&& YesOrNotEnum.N == sysFileInfoRequest.getSecretFlag()) {
			throw new FileException(FileExceptionEnum.FILE_DENIED_ACCESS);
		}

		// 获取文件后缀
		String fileSuffix = sysFileInfoResponse.getFileSuffix().toLowerCase();

		// 获取文件字节码
		byte[] fileBytes = sysFileInfoResponse.getFileBytes();

		// 附件预览
		this.renderPreviewFile(response, fileSuffix, fileBytes);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysFileInfoResponse versionBack(SysFileInfoRequest sysFileInfoRequest) {

		SysFileInfo fileInfo = this.querySysFileInfo(sysFileInfoRequest);

		// 判断有没有这个文件
		if (ObjectUtil.isEmpty(fileInfo)) {
			String userTip = FileExceptionEnum.FILE_NOT_FOUND.getUserTip();
			String errorMessage = CharSequenceUtil.format(userTip, "文件:" + fileInfo.getFileId() + "未找到！");
			throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
		}

		// 把之前的文件刷回
		UpdateDSLCompleter updateDSLCompleter = c -> c.set(SysFileInfoDynamicSqlSupport.fileStatus)
				.equalTo(FileStatusEnum.OLD)
				.where(SysFileInfoDynamicSqlSupport.fileCode, SqlBuilder.isEqualTo(fileInfo.getFileCode()))
				.and(SysFileInfoDynamicSqlSupport.fileStatus, SqlBuilder.isEqualTo(FileStatusEnum.NEW));
		this.getRepository().update(updateDSLCompleter);

		// 修改文件状态
		updateDSLCompleter = c -> c.set(SysFileInfoDynamicSqlSupport.fileStatus).equalTo(FileStatusEnum.NEW)
				.set(SysFileInfoDynamicSqlSupport.delFlag).equalTo(YesOrNotEnum.N)
				.where(SysFileInfoDynamicSqlSupport.fileId, SqlBuilder.isEqualTo(fileInfo.getFileId()));

		this.getRepository().update(updateDSLCompleter);

		// 返回
		return BeanUtil.toBean(fileInfo, SysFileInfoResponse.class);
	}

	@Override
	public void previewByBucketAndObjName(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {

		// 判断文件是否需要鉴权，需要鉴权的需要带token访问
		sysFileInfoRequest.setSecretFlag(YesOrNotEnum.Y);
		long count = this.countBy(sysFileInfoRequest);
		if (count > 0 && !LoginContext.me().hasLogin()) {
			throw new FileException(FileExceptionEnum.FILE_PERMISSION_DENIED);
		}
		FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfoRequest.getFileLocation());
		// 获取文件字节码
		byte[] fileBytes;
		try {
			fileBytes = fileOperatorApi.getFileBytes(sysFileInfoRequest.getFileBucket(),
					sysFileInfoRequest.getFileObjectName());
		}
		catch (Exception e) {
			log.error("获取文件流异常，具体信息为：{}", e.getMessage());
			throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR, e.getMessage());
		}
		// 获取文件后缀
		String fileSuffix = FileUtil.getSuffix(sysFileInfoRequest.getFileObjectName());
		// 附件预览
		this.renderPreviewFile(response, fileSuffix, fileBytes);
	}

	@Override
	public SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest) {
		return this.querySysFileInfo(sysFileInfoRequest);
	}

	@Override
	public List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList) {
		SelectDSLCompleter completer = c -> c.where(SysFileInfoDynamicSqlSupport.fileId, SqlBuilder.isIn(fileIdList));
		List<SysFileInfo> list = this.getRepository().select(getEntityClass(), completer);

		// bean转化
		return list.stream().map(i -> this.converterService.convert(i, getResponseClass())).toList();
	}

	@Override
	public SysFileInfoResponse getFileInfoWithoutContent(Long fileId) {

		SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
		sysFileInfoRequest.setFileId(fileId);

		// 获取文件的基本信息
		SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);

		// 转化实体
		return this.converterService.convert(sysFileInfo, getResponseClass());
	}

	@Override
	public String getFileAuthUrl(Long fileId) {
		return this.getFileAuthUrl(fileId, LoginContext.me().getToken());
	}

	@Override
	public String getFileAuthUrl(Long fileId, String token) {
		// 获取文件的基本信息
		SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
		sysFileInfoRequest.setFileId(fileId);
		SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);

		FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfo.getFileLocation());
		return fileOperatorApi.getFileAuthUrl(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName(),
				fileServerProperties.getFileTimeoutSeconds(), token);
	}

	@Override
	public String getFileUnAuthUrl(Long fileId) {
		// 获取文件的基本信息
		SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
		sysFileInfoRequest.setFileId(fileId);
		SysFileInfo sysFileInfo = querySysFileInfo(sysFileInfoRequest);
		FileOperatorApi fileOperatorApi = fileOperatorFactory.getFileOperatorApi(sysFileInfoRequest.getFileLocation());
		return fileOperatorApi.getFileUnAuthUrl(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName());
	}

	/**
	 * 渲染被预览的文件到servlet的response流中
	 *
	 * @date 2020/11/29 17:13
	 */
	private void renderPreviewFile(HttpServletResponse response, String fileSuffix, byte[] fileBytes) {

		// 如果文件后缀是图片或者pdf，则直接输出流
		if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix) || PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
			try {
				// 设置contentType
				if (PicFileTypeUtil.getFileImgTypeFlag(fileSuffix)) {
					response.setContentType(MediaType.IMAGE_PNG_VALUE);
				}
				else if (PdfFileTypeUtil.getFilePdfTypeFlag(fileSuffix)) {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
				}

				// 获取outputStream
				ServletOutputStream outputStream = response.getOutputStream();

				// 输出字节流
				IoUtil.write(outputStream, true, fileBytes);
			}
			catch (IOException e) {
				throw new FileException(FileExceptionEnum.WRITE_BYTES_ERROR, e.getMessage());
			}
		}
		else {
			// 不支持别的文件预览
			throw new FileException(FileExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
		}
	}

	/**
	 * 获取文件信息表
	 *
	 * @date 2020/11/29 13:40
	 */
	private SysFileInfo querySysFileInfo(SysFileInfoRequest sysFileInfoRequest) {
		Optional<SysFileInfo> sysFileInfo = this.getRepository().selectByPrimaryKey(getEntityClass(),
				sysFileInfoRequest.getFileId());
		if (sysFileInfo.isEmpty()) {
			throw new FileException(FileExceptionEnum.NOT_EXISTED, sysFileInfoRequest.getFileId());
		}
		SysFileInfo sfi = sysFileInfo.get();
		if (sfi.getDelFlag() == YesOrNotEnum.Y) {
			throw new FileException(FileExceptionEnum.NOT_EXISTED, sysFileInfoRequest.getFileId());
		}
		return sfi;
	}

}