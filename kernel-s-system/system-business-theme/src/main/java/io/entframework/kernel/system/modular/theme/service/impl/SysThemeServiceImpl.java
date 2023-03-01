/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.theme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.api.util.IdWorker;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.file.api.FileInfoClientApi;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.theme.SysThemeExceptionEnum;
import io.entframework.kernel.system.api.pojo.theme.*;
import io.entframework.kernel.system.modular.theme.entity.*;
import io.entframework.kernel.system.modular.theme.factory.DefaultThemeFactory;
import io.entframework.kernel.system.modular.theme.service.SysThemeService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateFieldService;
import io.entframework.kernel.system.modular.theme.service.SysThemeTemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 系统主题service接口实现类
 *
 * @date 2021/12/17 16:17
 */
@Slf4j
public class SysThemeServiceImpl extends BaseServiceImpl<SysThemeRequest, SysThemeResponse, SysTheme>
		implements SysThemeService {

	@Resource
	private SysThemeTemplateService sysThemeTemplateService;

	@Resource
	private SysThemeTemplateFieldService sysThemeTemplateFieldService;

	@Resource
	private FileInfoClientApi fileInfoClientApi;

	@Resource(name = "themeCacheApi")
	private CacheOperatorApi<DefaultTheme> themeCacheApi;

	public SysThemeServiceImpl() {
		super(SysThemeRequest.class, SysThemeResponse.class, SysTheme.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysThemeRequest sysThemeRequest) {
		// 查询模板状态
		SysThemeTemplateResponse sysThemeTemplate = sysThemeTemplateService.get(sysThemeRequest.getTemplateId());

		// 判断模板启用状态：如果为禁用状态不允许使用
		if (YesOrNotEnum.N == sysThemeTemplate.getStatusFlag()) {
			throw new SystemModularException(SysThemeExceptionEnum.THEME_TEMPLATE_IS_DISABLE);
		}

		SysTheme sysTheme = new SysTheme();

		// 拷贝属性
		BeanUtil.copyProperties(sysThemeRequest, sysTheme);

		// 设置默认启用状态-禁用N
		sysTheme.setStatusFlag(YesOrNotEnum.N);

		this.getRepository().insert(sysTheme);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysThemeRequest sysThemeRequest) {
		SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

		// 已启用的主题不允许删除
		if (YesOrNotEnum.Y == sysTheme.getStatusFlag()) {
			throw new SystemModularException(SysThemeExceptionEnum.THEME_NOT_ALLOW_DELETE);
		}

		// 删除保存的图片
		String themeValue = sysTheme.getThemeValue();
		Map<String, String> themeMap = JSON.parseObject(themeValue, Map.class);

		// 获取map的key
		List<String> themeKeys = new ArrayList<>(themeMap.keySet());

		// 获取图片文件的名称
		List<String> fileNames = new ArrayList<>();
		if (themeKeys.size() > 0) {
			List<SysThemeTemplateField> sysThemeTemplateFields = getRepository().select(SysThemeTemplateField.class,
					c -> c.where(SysThemeTemplateFieldDynamicSqlSupport.fieldCode, SqlBuilder.isIn(themeKeys)).and(
							SysThemeTemplateFieldDynamicSqlSupport.fieldType,
							SqlBuilder.isEqualTo(ThemeFieldTypeEnum.FILE)));

			fileNames = sysThemeTemplateFields.stream().map(SysThemeTemplateField::getFieldCode).toList();
		}

		// 删除图片
		if (fileNames.size() > 0) {
			for (String themeKey : themeKeys) {
				String themeValueStr = themeMap.get(themeKey);
				for (String fileName : fileNames) {
					if (CharSequenceUtil.isNotBlank(themeKey) && CharSequenceUtil.isNotBlank(fileName)
							&& themeKey.equals(fileName)) {
						SysFileInfoRequest sysFileInfoRequest = new SysFileInfoRequest();
						sysFileInfoRequest.setFileId(Long.parseLong(themeValueStr));
						fileInfoClientApi.deleteReally(sysFileInfoRequest);
					}
				}
			}
		}

		this.getRepository().deleteByPrimaryKey(getEntityClass(), getDefaultTemplateId());

		// 清除主题缓存
		this.clearThemeCache();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysThemeResponse update(SysThemeRequest sysThemeRequest) {
		SysTheme sysTheme = this.converterService.convert(sysThemeRequest, getEntityClass());

		this.getRepository().updateByPrimaryKey(sysTheme);
		// 清除主题缓存
		this.clearThemeCache();

		return this.converterService.convert(sysTheme, getResponseClass());
	}

	@Override
	public PageResult<SysThemeResponse> findPage(SysThemeRequest sysThemeRequest) {
		return this.page(sysThemeRequest);
	}

	@Override
	public SysThemeResponse detail(SysThemeRequest sysThemeRequest) {
		SysThemeResponse sysTheme = this.converterService.convert(this.querySysThemeById(sysThemeRequest),
				getResponseClass());

		// 设置动态属性表单
		String themeValueJson = sysTheme.getThemeValue();
		JSONObject jsonObject = JSON.parseObject(themeValueJson);
		sysTheme.setDynamicForm(jsonObject.getInnerMap());

		// 遍历表单属性，找到所有文件类型的，组装文件的图片和名称等信息
		HashMap<String, AntdvFileInfo[]> tempFileList = new HashMap<>();
		for (Map.Entry<String, Object> keyValues : jsonObject.entrySet()) {
			String key = keyValues.getKey();
			String value = jsonObject.getString(key);
			// 判断是否是文件类型
			boolean keyFileFlag = sysThemeTemplateFieldService.getKeyFileFlag(key);
			if (keyFileFlag) {
				AntdvFileInfo antdvFileInfo = new AntdvFileInfo();
				// 设置唯一id
				antdvFileInfo.setUid(IdWorker.getIdStr());
				// 设置文件名称
				SysFileInfoResponse fileInfoWithoutContent = null;
				try {
					fileInfoWithoutContent = fileInfoClientApi.getFileInfoWithoutContent(Long.valueOf(value));
					antdvFileInfo.setName(fileInfoWithoutContent.getFileOriginName());
				}
				catch (Exception e) {
					// 未查询到文件继续查询下一个文件
				}
				// 设置文件访问url
				String fileAuthUrl = fileInfoClientApi.getFileAuthUrl(Long.valueOf(value));
				antdvFileInfo.setThumbUrl(fileAuthUrl);
				tempFileList.put(key, new AntdvFileInfo[] { antdvFileInfo });
			}
		}

		// 设置临时文件的展示
		sysTheme.setTempFileList(tempFileList);
		return sysTheme;
	}

	@Override
	public void updateThemeStatus(SysThemeRequest sysThemeRequest) {
		SysTheme sysTheme = this.querySysThemeById(sysThemeRequest);

		// 已经启用系统主题不允许禁用
		if (YesOrNotEnum.Y == sysTheme.getStatusFlag()) {
			throw new SystemModularException(SysThemeExceptionEnum.UNIQUE_ENABLE_NOT_DISABLE);
		}
		else {
			// 如果当前系统禁用，启用该系统主题，同时禁用已启用的系统主题
			sysTheme.setStatusFlag(YesOrNotEnum.Y);

			SysTheme request = new SysTheme();
			request.setStatusFlag(YesOrNotEnum.Y);
			List<SysTheme> results = this.getRepository().selectBy(request);

			if (results.size() > 1) {
				SysTheme theme = results.get(0);
				theme.setStatusFlag(YesOrNotEnum.N);
				this.getRepository().updateByPrimaryKey(theme);
			}
		}
		this.getRepository().updateByPrimaryKey(sysTheme);

		// 清除主题缓存
		this.clearThemeCache();
	}

	@Override
	public DefaultTheme currentThemeInfo(SysThemeRequest sysThemeParam) {

		// 获取缓存中是否有默认主题
		DefaultTheme defaultTheme = themeCacheApi.get(SystemConstants.THEME_GUNS_PLATFORM);
		if (defaultTheme != null) {
			return defaultTheme;
		}

		// 查询系统中激活的主题
		DefaultTheme result = this.querySystemTheme();

		// 将主题信息中的文件id，拼接为文件url的形式
		this.parseFileUrls(result);

		// 缓存系统中激活的主题
		themeCacheApi.put(SystemConstants.THEME_GUNS_PLATFORM, result);

		return result;
	}

	/**
	 * 查询单个系统主题
	 *
	 * @date 2021/12/17 16:30
	 */
	private SysTheme querySysThemeById(SysThemeRequest sysThemeRequest) {
		Optional<SysTheme> sysTheme = this.getRepository().selectByPrimaryKey(getEntityClass(),
				sysThemeRequest.getThemeId());
		if (sysTheme.isEmpty()) {
			throw new SystemModularException(SysThemeExceptionEnum.THEME_NOT_EXIST);
		}
		return sysTheme.get();
	}

	/**
	 * 查找系统中默认的主题
	 *
	 * @date 2022/1/11 9:44
	 */
	private DefaultTheme querySystemTheme() {
		// 查询编码为GUNS_PLATFORM的主题模板id
		Long defaultTemplateId = getDefaultTemplateId();
		if (defaultTemplateId == null) {
			return DefaultThemeFactory.getSystemDefaultTheme();
		}

		// 查找改模板激活的主题，如果没有就返回默认主题
		SysTheme request = new SysTheme();
		request.setTemplateId(defaultTemplateId);
		request.setStatusFlag(YesOrNotEnum.Y);
		Optional<SysTheme> sysTheme = this.getRepository().selectOne(request);
		if (sysTheme.isEmpty()) {
			log.error("当前系统主题模板编码为GUNS_PLATFORM的主题不存在，请检查数据库数据是否正常！");
			return DefaultThemeFactory.getSystemDefaultTheme();
		}

		// 解析主题中的json字符串
		String themeValue = sysTheme.get().getThemeValue();
		if (CharSequenceUtil.isNotBlank(themeValue)) {
			JSONObject jsonObject = JSON.parseObject(themeValue);
			return DefaultThemeFactory.parseDefaultTheme(jsonObject);
		}
		else {
			return DefaultThemeFactory.getSystemDefaultTheme();
		}
	}

	/**
	 * 将属性中所有是文件类型的文件id转化为文件url
	 *
	 * @date 2022/1/11 11:12
	 */
	private DefaultTheme parseFileUrls(DefaultTheme theme) {
		// 查询编码为GUNS_PLATFORM的主题模板id
		Long defaultTemplateId = getDefaultTemplateId();
		if (defaultTemplateId == null) {
			return theme;
		}

		// 获取主题模板中所有是文件的字段
		List<SysThemeTemplateRel> relList = this.getRepository().select(SysThemeTemplateRel.class,
				c -> c.where(SysThemeTemplateRelDynamicSqlSupport.templateId, SqlBuilder.isEqualTo(defaultTemplateId)));

		if (ObjectUtil.isEmpty(relList)) {
			return theme;
		}

		// 所有是文件类型的字段编码
		List<String> fieldCodes = relList.stream().map(SysThemeTemplateRel::getFieldCode).toList();

		// 查询字段中是文件的字段列表
		List<SysThemeTemplateField> fieldInfoList = this.getRepository().select(SysThemeTemplateField.class,
				c -> c.where(SysThemeTemplateFieldDynamicSqlSupport.fieldCode, SqlBuilder.isIn(fieldCodes)).and(
						SysThemeTemplateFieldDynamicSqlSupport.fieldType,
						SqlBuilder.isEqualTo(ThemeFieldTypeEnum.FILE)));

		if (ObjectUtil.isEmpty(fieldInfoList)) {
			return theme;
		}

		// 所有文件类型的字段名
		List<String> needToParse = fieldInfoList.stream().map(SysThemeTemplateField::getFieldCode)
				.map(CharSequenceUtil::toCamelCase).toList();

		// 其他属性
		Map<String, String> otherConfigs = theme.getOtherConfigs();

		for (String fieldName : needToParse) {
			PropertyDescriptor propertyDescriptor = null;
			try {
				propertyDescriptor = new PropertyDescriptor(fieldName, DefaultTheme.class);
				Method readMethod = propertyDescriptor.getReadMethod();
				String fieldValue = (String) readMethod.invoke(theme);
				if (!CharSequenceUtil.isEmpty(fieldValue)) {
					// 将文件id转化为文件url
					String fileUnAuthUrl = fileInfoClientApi.getFileUnAuthUrl(Long.valueOf(fieldValue));
					Method writeMethod = propertyDescriptor.getWriteMethod();
					writeMethod.invoke(theme, fileUnAuthUrl);
				}
			}
			catch (Exception e) {
				log.error("解析主题的文件id为url时出错", e);
			}

			// 判断其他属性有没有需要转化的
			for (Map.Entry<String, String> otherItem : otherConfigs.entrySet()) {
				if (fieldName.equals(otherItem.getKey())) {
					String otherFileId = otherItem.getValue();
					// 将文件id转化为文件url
					String fileUnAuthUrl = fileInfoClientApi.getFileUnAuthUrl(Long.valueOf(otherFileId));
					otherConfigs.put(otherItem.getKey(), fileUnAuthUrl);
				}
			}
		}

		return theme;
	}

	/**
	 * 获取默认系统的模板id
	 *
	 * @date 2022/1/11 11:35
	 */
	private Long getDefaultTemplateId() {
		// 查询编码为GUNS_PLATFORM的主题模板id
		SysThemeTemplateRequest request = new SysThemeTemplateRequest();
		request.setTemplateCode(SystemConstants.THEME_GUNS_PLATFORM);
		SysThemeTemplateResponse sysThemeTemplate = this.sysThemeTemplateService.selectOne(request);
		if (sysThemeTemplate == null) {
			log.error("当前系统主题模板编码GUNS_PLATFORM不存在，请检查数据库数据是否正常！");
			return null;
		}
		return sysThemeTemplate.getTemplateId();
	}

	/**
	 * 清除主题缓存
	 *
	 * @date 2022/1/12 12:49
	 */
	private void clearThemeCache() {
		themeCacheApi.remove(SystemConstants.THEME_GUNS_PLATFORM);
	}

}
