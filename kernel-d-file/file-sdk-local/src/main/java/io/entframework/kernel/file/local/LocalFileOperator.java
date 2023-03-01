/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.system.SystemUtil;
import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.config.LocalFileProperties;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.enums.BucketAuthEnum;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.file.api.exception.FileException;
import io.entframework.kernel.file.api.exception.enums.FileExceptionEnum;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地文件的操作
 *
 * @date 2020/10/26 13:38
 */
public class LocalFileOperator implements FileOperatorApi {

	private final LocalFileProperties localFileProperties;

	private final FileServerProperties fileServerProperties;

	private String currentSavePath = "";

	@Value("${server.servlet.context-path:}")
	private String contextPath;

	public LocalFileOperator(LocalFileProperties localFileProperties, FileServerProperties fileServerProperties) {
		this.localFileProperties = localFileProperties;
		this.fileServerProperties = fileServerProperties;
		initClient();
	}

	@Override
	public void initClient() {
		if (SystemUtil.getOsInfo().isWindows()) {
			String savePathWindows = localFileProperties.getLocalFileSavePathWin();
			if (!FileUtil.exist(savePathWindows)) {
				FileUtil.mkdir(savePathWindows);
			}
			currentSavePath = savePathWindows;
		}
		else {
			String savePathLinux = localFileProperties.getLocalFileSavePathLinux();
			if (!FileUtil.exist(savePathLinux)) {
				FileUtil.mkdir(savePathLinux);
			}
			currentSavePath = savePathLinux;
		}
	}

	@Override
	public void destroyClient() {
		// empty
	}

	@Override
	public Object getClient() {
		// empty
		return null;
	}

	@Override
	public boolean doesBucketExist(String bucketName) {
		String absolutePath = currentSavePath + File.separator + bucketName;
		return FileUtil.exist(absolutePath);
	}

	@Override
	public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
		// empty
	}

	@Override
	public boolean isExistingFile(String bucketName, String key) {
		String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
		return FileUtil.exist(absoluteFile);
	}

	@Override
	public void storageFile(String bucketName, String key, byte[] bytes) {

		// 判断bucket存在不存在
		String bucketPath = currentSavePath + File.separator + bucketName;
		if (!FileUtil.exist(bucketPath)) {
			FileUtil.mkdir(bucketPath);
		}

		// 存储文件
		String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
		FileUtil.writeBytes(bytes, absoluteFile);
	}

	@Override
	public void storageFile(String bucketName, String key, InputStream inputStream) {

		// 判断bucket存在不存在
		String bucketPath = currentSavePath + File.separator + bucketName;
		if (!FileUtil.exist(bucketPath)) {
			FileUtil.mkdir(bucketPath);
		}

		// 存储文件
		String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
		FileUtil.writeFromStream(inputStream, absoluteFile);
	}

	@Override
	public byte[] getFileBytes(String bucketName, String key) {

		// 判断文件存在不存在
		String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
		if (!FileUtil.exist(absoluteFile)) {
			// 组装返回信息
			String errorMessage = CharSequenceUtil.format("bucket={},key={}", bucketName, key);
			throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
		}
		else {
			return FileUtil.readBytes(absoluteFile);
		}
	}

	@Override
	public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
		// empty
	}

	@Override
	public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {

		// 判断文件存在不存在
		String originFile = currentSavePath + File.separator + originBucketName + File.separator + originFileKey;
		if (!FileUtil.exist(originFile)) {
			// 组装返回信息
			String errorMessage = CharSequenceUtil.format("bucket={},key={}", originBucketName, originFileKey);
			throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
		}
		else {

			// 拷贝文件
			String destFile = currentSavePath + File.separator + newBucketName + File.separator + newFileKey;
			FileUtil.copy(originFile, destFile, true);
		}
	}

	@Override
	public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis, String token) {
		return fileServerProperties.getDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME
				+ "?fileBucket=" + bucketName + "&fileObjectName=" + key + "&token=" + token;
	}

	@Override
	public String getFileUnAuthUrl(String bucketName, String key) {
		return fileServerProperties.getDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME
				+ "?fileBucket=" + bucketName + "&fileObjectName=" + key;
	}

	@Override
	public void deleteFile(String bucketName, String key) {

		// 判断文件存在不存在
		String file = currentSavePath + File.separator + bucketName + File.separator + key;
		if (!FileUtil.exist(file)) {
			return;
		}

		// 删除文件
		FileUtil.del(file);

	}

	@Override
	public FileStorageEnum getFileLocationEnum() {
		return FileStorageEnum.LOCAL;
	}

	@Override
	public void close() throws IOException {
		// No need to close local file client
	}

	@Override
	public String getBucket(Long fileId) {
		return String.valueOf(fileId);
	}

}
