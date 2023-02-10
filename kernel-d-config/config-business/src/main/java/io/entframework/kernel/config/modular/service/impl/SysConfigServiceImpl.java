/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.config.modular.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.config.api.ConfigInitCallbackApi;
import io.entframework.kernel.config.api.ConfigInitStrategyApi;
import io.entframework.kernel.config.api.constants.ConfigConstants;
import io.entframework.kernel.config.api.context.ConfigContext;
import io.entframework.kernel.config.api.exception.ConfigException;
import io.entframework.kernel.config.api.exception.enums.ConfigExceptionEnum;
import io.entframework.kernel.config.api.pojo.ConfigInitItem;
import io.entframework.kernel.config.api.pojo.ConfigInitRequest;
import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.config.modular.entity.SysConfigDynamicSqlSupport;
import io.entframework.kernel.config.modular.pojo.request.SysConfigRequest;
import io.entframework.kernel.config.modular.pojo.response.SysConfigResponse;
import io.entframework.kernel.config.modular.service.SysConfigService;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.rule.constants.RuleConstants;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统参数配置service接口实现类
 *
 * @date 2020/4/14 11:16
 */
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigRequest, SysConfigResponse, SysConfig> implements SysConfigService {

	public SysConfigServiceImpl() {
		super(SysConfigRequest.class, SysConfigResponse.class, SysConfig.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysConfigRequest sysConfigRequest) {

		// 1.构造实体
		SysConfig sysConfig = this.converterService.convert(sysConfigRequest, getEntityClass());
		sysConfig.setStatusFlag(StatusEnum.ENABLE);

		// 2.保存到库中
		this.getRepository().insert(sysConfig);

		// 3.添加对应context
		ConfigContext.me().putConfig(sysConfigRequest.getConfigCode(), sysConfigRequest.getConfigValue());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysConfigRequest sysConfigRequest) {

		// 1.根据id获取常量
		SysConfig sysConfig = this.querySysConfig(sysConfigRequest);

		// 2.不能删除系统参数
		if (YesOrNotEnum.Y == sysConfig.getSysFlag()) {
			throw new ConfigException(ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
		}

		// 3.设置状态为已删除
		sysConfig.setStatusFlag(StatusEnum.DISABLE);
		sysConfig.setDelFlag(YesOrNotEnum.Y);
		this.getRepository().insert(sysConfig);

		// 4.删除对应context
		ConfigContext.me().deleteConfig(sysConfigRequest.getConfigCode());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysConfigResponse update(SysConfigRequest sysConfigRequest) {

		// 1.根据id获取常量信息
		SysConfig sysConfig = this.querySysConfig(sysConfigRequest);

		// 2.请求参数转化为实体
		this.converterService.copy(sysConfigRequest, sysConfig);
		// 不能修改状态，用修改状态接口修改状态
		sysConfig.setStatusFlag(null);

		// 3.更新记录
		this.getRepository().updateByPrimaryKey(sysConfig);

		// 4.更新对应常量context
		ConfigContext.me().putConfig(sysConfigRequest.getConfigCode(), sysConfigRequest.getConfigValue());

		return this.converterService.convert(sysConfig, getResponseClass());
	}

	@Override
	public SysConfig detail(SysConfigRequest sysConfigRequest) {
		return this.querySysConfig(sysConfigRequest);
	}

	@Override
	public PageResult<SysConfigResponse> findPage(SysConfigRequest sysConfigRequest) {
		return this.page(sysConfigRequest);
	}

	@Override
	public List<SysConfigResponse> findList(SysConfigRequest sysConfigRequest) {
		return this.select(sysConfigRequest);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void initConfig(ConfigInitRequest configInitRequest) {

		if (configInitRequest == null || configInitRequest.getSysConfigs() == null) {
			throw new ConfigException(ConfigExceptionEnum.CONFIG_INIT_ERROR);
		}

		// 如果当前已经初始化过配置，则不能初始化
		SysConfigRequest query = new SysConfigRequest();
		query.setConfigCode(RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME);
		SysConfigResponse tempSysConfig = this.selectOne(query);
		String alreadyInit = tempSysConfig.getConfigValue();
		if (Convert.toBool(alreadyInit)) {
			throw new ConfigException(ConfigExceptionEnum.CONFIG_INIT_ALREADY);
		}

		// 获取初始化回调接口的所有实现类
		Map<String, ConfigInitCallbackApi> beans = SpringUtil.getBeansOfType(ConfigInitCallbackApi.class);

		// 调用初始化之前回调
		if (ObjectUtil.isNotNull(beans)) {
			for (ConfigInitCallbackApi initCallbackApi : beans.values()) {
				initCallbackApi.initBefore();
			}
		}

		// 添加系统已经初始化的配置
		Map<String, String> sysConfigs = configInitRequest.getSysConfigs();
		sysConfigs.put(RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME, "true");

		// 针对每个配置执行更新库和刷新缓存的操作
		for (Map.Entry<String, String> entry : sysConfigs.entrySet()) {
			String configCode = entry.getKey();
			String configValue = entry.getValue();

			// 获取库数据库这条记录
			query = new SysConfigRequest();
			query.setConfigCode(configCode);
			List<SysConfig> queryConfigs = this.getRepository()
					.select(getEntityClass(), c -> c.where(SysConfigDynamicSqlSupport.configCode, SqlBuilder.isEqualTo(configCode)));
			if (queryConfigs == null || queryConfigs.isEmpty()) {
				continue;
			}
			SysConfig sysConfig = queryConfigs.get(0);
			sysConfig.setConfigValue(configValue);
			this.getRepository().updateByPrimaryKey(sysConfig);

			// 更新缓存
			ConfigContext.me().putConfig(configCode, configValue);
		}

		// 调用初始化之后回调
		if (ObjectUtil.isNotNull(beans)) {
			for (ConfigInitCallbackApi initCallbackApi : beans.values()) {
				initCallbackApi.initAfter();
			}
		}
	}

	@Override
	public Boolean getInitConfigFlag() {
		SysConfigRequest request = new SysConfigRequest();
		request.setConfigCode(RuleConstants.SYSTEM_CONFIG_INIT_FLAG_NAME);
		SysConfigResponse sysConfig = this.selectOne(request);

		// 配置为空，还没初始化
		if (sysConfig == null) {
			return true;
		} else {
			String configValue = sysConfig.getConfigValue();
			if (CharSequenceUtil.isEmpty(configValue)) {
				return true;
			} else {
				return Convert.toBool(sysConfig.getConfigValue());
			}
		}
	}

	@Override
	public List<ConfigInitItem> getInitConfigs() {
		List<ConfigInitItem> configInitItemList = new ArrayList<>();
		Map<String, ConfigInitStrategyApi> beans = SpringUtil.getBeansOfType(ConfigInitStrategyApi.class);
		for (ConfigInitStrategyApi value : beans.values()) {
			configInitItemList.addAll(value.getInitConfigs());
		}
		return configInitItemList;
	}

	@Override
	public String getServerDeployHost() {

		// 获取后端部署的服务器
		SysConfigRequest request = new SysConfigRequest();
		request.setConfigCode(ConfigConstants.SYS_SERVER_DEPLOY_HOST);
		SysConfigResponse sysConfig = this.selectOne(request);

		if (sysConfig != null) {
			return sysConfig.getConfigValue();
		} else {
			return FileConstants.DEFAULT_SERVER_DEPLOY_HOST;
		}
	}

	/**
	 * 获取系统参数配置
	 *
	 * @date 2020/4/14 11:19
	 */
	private SysConfig querySysConfig(SysConfigRequest sysConfigRequest) {
		Optional<SysConfig> sysConfig = this.getRepository().selectByPrimaryKey(getEntityClass(), sysConfigRequest.getConfigId());
		String userTip = CharSequenceUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(),
				"id: " + sysConfigRequest.getConfigId());
		if (sysConfig.isEmpty()) {
			throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST, userTip);
		}
		SysConfig sc = sysConfig.get();
		if (YesOrNotEnum.Y == sc.getDelFlag()) {
			throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST, userTip);
		}
		return sc;
	}

}
