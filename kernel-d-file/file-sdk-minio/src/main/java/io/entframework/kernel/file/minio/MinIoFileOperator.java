/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.minio;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.config.FileServerProperties;
import io.entframework.kernel.file.api.config.MinIoProperties;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.file.api.enums.BucketAuthEnum;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.file.api.exception.FileException;
import io.entframework.kernel.file.api.exception.enums.FileExceptionEnum;
import io.entframework.kernel.file.minio.policy.BucketPolicy;
import io.entframework.kernel.file.minio.policy.PolicyType;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * MinIo文件操作客户端
 *
 * @date 2020/10/31 10:35
 */
@Slf4j
public class MinIoFileOperator implements FileOperatorApi {

	@Value("${server.servlet.context-path:}")
	private String contextPath;

	private final Object LOCK = new Object();

	/**
	 * 文件ContentType对应关系
	 */
	Map<String, String> contentType = new HashMap<>();

	/**
	 * MinIo文件操作客户端
	 */
	private MinioClient minioClient;

	/**
	 * MinIo的配置
	 */
	private final MinIoProperties minIoProperties;

	private final FileServerProperties fileServerProperties;

	public MinIoFileOperator(MinIoProperties minIoProperties, FileServerProperties fileServerProperties) {
		this.minIoProperties = minIoProperties;
		this.fileServerProperties = fileServerProperties;
		this.initClient();
	}

	@Override
	public void initClient() {
		String endpoint = minIoProperties.getEndpoint();
		String accessKey = minIoProperties.getAccessKey();
		String secretKey = minIoProperties.getSecretKey();
		// 创建minioClient实例
		minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
	}

	@Override
	public void destroyClient() {
		// empty
	}

	@Override
	public Object getClient() {
		return minioClient;
	}

	@Override
	public boolean doesBucketExist(String bucketName) {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		}
		catch (Exception e) {
			// 组装提示信息
			throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
		}
	}

	@Override
	public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
		setFileAcl(bucketName, "*", bucketAuthEnum);
	}

	@Override
	public boolean isExistingFile(String bucketName, String key) {
		InputStream inputStream = null;
		try {
			inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(key).build());
			if (inputStream != null) {
				return true;
			}
		}
		catch (Exception e) {
			return false;
		}
		finally {
			IoUtil.close(inputStream);
		}
		return false;
	}

	@Override
	public void storageFile(String bucketName, String key, byte[] bytes) {
		if (bytes != null && bytes.length > 0) {
			// 字节数组转字节数组输入流
			ByteArrayInputStream byteArrayInputStream = IoUtil.toStream(bytes);

			// 获取文件类型
			ByteArrayInputStream tmp = IoUtil.toStream(bytes);
			String type = FileTypeUtil.getType(tmp);
			String fileContentType = getFileContentType(String.format("%s%s", ".", type));

			try {
				PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(key)
						.stream(byteArrayInputStream, bytes.length, -1).contentType(fileContentType).build();

				minioClient.putObject(putObjectArgs);
			}
			catch (Exception e) {

				// 组装提示信息
				throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
			}
		}
	}

	@Override
	public void storageFile(String bucketName, String key, InputStream inputStream) {
		if (inputStream != null) {
			byte[] bytes = IoUtil.readBytes(inputStream);
			storageFile(bucketName, key, bytes);
		}
	}

	@Override
	public byte[] getFileBytes(String bucketName, String key) {
		try {
			InputStream inputStream = minioClient
					.getObject(GetObjectArgs.builder().bucket(bucketName).object(key).build());
			return IoUtil.readBytes(inputStream);
		}
		catch (Exception e) {
			// 组装提示信息
			throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
		}
	}

	@Override
	public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
		try {
			BucketPolicy policy = this.doGetBucketPolicy(bucketName);
			SetBucketPolicyArgs.Builder builder = SetBucketPolicyArgs.builder();
			builder.bucket(bucketName);
			if (bucketAuthEnum.equals(BucketAuthEnum.PRIVATE)) {
				policy.setPolicy(PolicyType.NONE, key);
			}
			else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ)) {
				policy.setPolicy(PolicyType.READ_ONLY, key);
			}
			else if (bucketAuthEnum.equals(BucketAuthEnum.PUBLIC_READ_WRITE)) {
				policy.setPolicy(PolicyType.READ_WRITE, key);
			}
			else if (bucketAuthEnum.equals(BucketAuthEnum.MINIO_WRITE_ONLY)) {
				policy.setPolicy(PolicyType.WRITE_ONLY, key);
			}
			builder.config(policy.getJson());
			minioClient.setBucketPolicy(builder.build());
		}
		catch (Exception e) {
			// 组装提示信息
			throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
		}
	}

	private BucketPolicy doGetBucketPolicy(String bucketName) {
		try {
			String policy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().build());
			if (StringUtils.isNotEmpty(policy)) {
				return BucketPolicy.parseJson(new StringReader(policy), bucketName);
			}
		}
		catch (Exception ex) {
			log.error("Can't get policy:" + ex.getMessage());
		}
		return new BucketPolicy(bucketName);
	}

	@Override
	public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {
		try {
			CopyObjectArgs.Builder builder = CopyObjectArgs.builder();
			builder.bucket(newBucketName).object(newFileKey)
					.source(CopySource.builder().bucket(originBucketName).object(originFileKey).build());
			minioClient.copyObject(builder.build());
		}
		catch (Exception e) {
			// 组装提示信息
			throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
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
		try {
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(key).build());
		}
		catch (Exception e) {
			// 组装提示信息
			throw new FileException(FileExceptionEnum.MINIO_FILE_ERROR, e.getMessage());
		}

	}

	@Override
	public FileStorageEnum getFileLocationEnum() {
		return FileStorageEnum.MINIO;
	}

	/**
	 * 获取文件后缀对应的contentType
	 *
	 * @date 2020/11/2 18:08
	 */
	private Map<String, String> getFileContentType() {
		synchronized (LOCK) {
			if (contentType.size() == 0) {
				contentType.put(".bmp", "application/x-bmp");
				contentType.put(".gif", "image/gif");
				contentType.put(".fax", "image/fax");
				contentType.put(".ico", "image/x-icon");
				contentType.put(".jfif", "image/jpeg");
				contentType.put(".jpe", "image/jpeg");
				contentType.put(".jpeg", "image/jpeg");
				contentType.put(".jpg", "image/jpeg");
				contentType.put(".png", "image/png");
				contentType.put(".rp", "image/vnd.rn-realpix");
				contentType.put(".tif", "image/tiff");
				contentType.put(".tiff", "image/tiff");
				contentType.put(".doc", "application/msword");
				contentType.put(".ppt", "application/x-ppt");
				contentType.put(".pdf", "application/pdf");
				contentType.put(".xls", "application/vnd.ms-excel");
				contentType.put(".txt", "text/plain");
				contentType.put(".java", "java/*");
				contentType.put(".html", "text/html");
				contentType.put(".avi", "video/avi");
				contentType.put(".movie", "video/x-sgi-movie");
				contentType.put(".mp4", "video/mpeg4");
				contentType.put(".mp3", "audio/mp3");
			}
		}
		return contentType;
	}

	/**
	 * 获取文件后缀对应的contentType
	 *
	 * @date 2020/11/2 18:05
	 */
	private String getFileContentType(String fileSuffix) {
		String contentType = getFileContentType().get(fileSuffix);
		if (ObjectUtil.isEmpty(contentType)) {
			return "application/octet-stream";
		}
		else {
			return contentType;
		}
	}

	@Override
	public void close() throws IOException {
		destroyClient();
	}

	@Override
	public String getBucket(Long fileId) {
		return this.minIoProperties.getBucket();
	}

}
