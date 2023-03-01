package io.entframework.kernel.file.api.config;

import io.entframework.kernel.file.api.constants.FileConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件服务的一些配置信息
 */
@Data
@ConfigurationProperties(prefix = "kernel.file.server")
public class FileServerProperties {

	/**
	 * 获取服务部署的机器host，例如http://localhost 这个配置为了用在文件url的拼接上
	 */
	private String deployHost = FileConstants.DEFAULT_SERVER_DEPLOY_HOST;

	/**
	 * 获取文件生成auth url的失效时间
	 */
	private long fileTimeoutSeconds = FileConstants.DEFAULT_FILE_TIMEOUT_SECONDS;

}
