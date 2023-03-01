/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.scanner;

import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.rule.listener.ApplicationReadyListener;
import io.entframework.kernel.scanner.api.DevOpsReportApi;
import io.entframework.kernel.scanner.api.ResourceCollectorApi;
import io.entframework.kernel.scanner.api.ResourceReportApi;
import io.entframework.kernel.scanner.api.constants.ScannerConstants;
import io.entframework.kernel.scanner.api.holder.InitScanFlagHolder;
import io.entframework.kernel.scanner.api.pojo.devops.DevOpsReportProperties;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceDefinition;
import io.entframework.kernel.scanner.api.pojo.resource.ResourceParam;
import io.entframework.kernel.scanner.api.pojo.resource.SysResourcePersistencePojo;
import io.entframework.kernel.scanner.api.pojo.scanner.ScannerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;

/**
 * 监听项目初始化完毕，汇报资源到服务（可为远程服务，可为本服务）
 *
 * @author jeff_qian
 * @date 2020/10/19 22:27
 */
@Slf4j
public class ResourceReportListener extends ApplicationReadyListener implements Ordered {

	@Override
	public void eventCallback(ApplicationReadyEvent event) {

		ConfigurableApplicationContext applicationContext = event.getApplicationContext();

		// 获取有没有开资源扫描开关
		ScannerProperties scannerProperties = applicationContext.getBean(ScannerProperties.class);
		if (scannerProperties.getOpen() == null || !scannerProperties.getOpen()) {
			log.info("资源扫描未打开");
			return;
		}

		// 如果项目还没进行资源扫描
		if (InitScanFlagHolder.getFlag() == null || !InitScanFlagHolder.getFlag()) {
			log.info("项目还没进行资源扫描");
			// 获取当前系统的所有资源
			ResourceCollectorApi resourceCollectorApi = applicationContext.getBean(ResourceCollectorApi.class);
			Map<String, Map<String, ResourceDefinition>> modularResources = resourceCollectorApi.getModularResources();

			// 持久化资源
			ResourceReportApi resourcePersistentApi = applicationContext.getBean(ResourceReportApi.class);
			List<SysResourcePersistencePojo> persistencePojos = resourcePersistentApi
					.reportResourcesAndGetResult(new ResourceParam(scannerProperties.getAppCode(), modularResources));

			// 向DevOps一体化平台汇报资源
			DevOpsReportProperties devOpsReportProperties = applicationContext.getBean(DevOpsReportProperties.class);
			// 如果配置了相关属性则进行DevOps资源汇报
			if (ObjectUtil.isAllNotEmpty(devOpsReportProperties, devOpsReportProperties.getServerHost(),
					devOpsReportProperties.getProjectInteractionSecretKey(),
					devOpsReportProperties.getProjectUniqueCode(), devOpsReportProperties.getServerHost())) {
				DevOpsReportApi devOpsReportApi = applicationContext.getBean(DevOpsReportApi.class);
				try {
					devOpsReportApi.reportResources(devOpsReportProperties, persistencePojos);
				}
				catch (Exception e) {
					log.error("向DevOps平台汇报异常出现网络错误，如无法联通DevOps平台可关闭相关配置。", e);
				}
			}
			// 设置标识已经扫描过
			InitScanFlagHolder.setFlag();
		}

	}

	@Override
	public int getOrder() {
		return ScannerConstants.REPORT_RESOURCE_LISTENER_SORT;
	}

}
