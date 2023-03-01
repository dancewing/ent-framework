/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.system.modular.home.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.dao.repository.GeneralRepository;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.HomePageServiceApi;
import io.entframework.kernel.system.api.PositionServiceApi;
import io.entframework.kernel.system.api.UserServiceApi;
import io.entframework.kernel.system.api.pojo.home.HomeCompanyInfo;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.user.OnlineUserDTO;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import io.entframework.kernel.system.modular.entity.HrOrganizationDynamicSqlSupport;
import io.entframework.kernel.system.modular.entity.SysMenu;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCountDynamicSqlSupport;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrlDynamicSqlSupport;
import io.entframework.kernel.system.modular.home.pojo.OnlineUserStat;
import io.entframework.kernel.system.modular.home.service.HomePageService;
import io.entframework.kernel.system.modular.home.service.SysStatisticsUrlService;
import io.entframework.kernel.system.modular.service.HrOrganizationService;
import io.entframework.kernel.system.modular.service.SysMenuService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static io.entframework.kernel.rule.constants.SymbolConstant.LEFT_SQUARE_BRACKETS;
import static io.entframework.kernel.rule.constants.SymbolConstant.RIGHT_SQUARE_BRACKETS;

/**
 * 首页服务实现类
 *
 * @date 2022/2/11 20:41
 */
@Service
public class HomePageServiceImpl implements HomePageService, HomePageServiceApi {

	@Resource
	private UserServiceApi userServiceApi;

	@Resource
	private HrOrganizationService hrOrganizationService;

	@Resource
	private PositionServiceApi positionServiceApi;

	@Resource(name = "requestCountCacheApi")
	private CacheOperatorApi<Map<Long, Integer>> requestCountCacheApi;

	@Resource
	private SysStatisticsUrlService sysStatisticsUrlService;

	@Resource
	private GeneralRepository generalRepository;

	@Resource
	private SysMenuService sysMenuService;

	@Override
	public OnlineUserStat getOnlineUserList(OnlineUserRequest onlineUserRequest) {

		OnlineUserStat onlineUserStat = new OnlineUserStat();

		// 获取在线总人数
		List<OnlineUserDTO> onlineUserDTOS = userServiceApi.onlineUserList(onlineUserRequest);

		// 同一用户多个会话去重
		HashSet<String> onlineUserList = new HashSet<>();
		for (OnlineUserDTO onlineUserDTO : onlineUserDTOS) {
			if (ObjectUtil.isNotEmpty(onlineUserDTO.getRealName())) {
				onlineUserList.add(onlineUserDTO.getRealName());
			}
		}
		onlineUserStat.setTotalNum(onlineUserList.size());

		// 统计前20个人
		Set<String> newSet = onlineUserList.stream().limit(20).collect(Collectors.toSet());
		onlineUserStat.setTotalUserNames(newSet);

		return onlineUserStat;
	}

	@Override
	public HomeCompanyInfo getHomeCompanyInfo() {
		HomeCompanyInfo homeCompanyInfo = new HomeCompanyInfo();

		// 获取组织机构总数量
		homeCompanyInfo.setOrganizationNum(hrOrganizationService.countBy(new HrOrganizationRequest()));

		// 获取企业人员总数量
		SysUserRequest sysUserRequest = new SysUserRequest();
		List<Long> allUserIdList = userServiceApi.queryAllUserIdList(sysUserRequest);
		homeCompanyInfo.setEnterprisePersonNum(allUserIdList.size());

		// 获取所有职位总数
		long positionNum = positionServiceApi.positionNum();
		homeCompanyInfo.setPositionNum(positionNum);

		// 获取当前登录人的组织机构id
		LoginUser loginUser = LoginContext.me().getLoginUser();
		Long organizationId = loginUser.getOrganizationId();

		// 获取当前公司的所有子公司数量(含当前公司)
		List<HrOrganization> organizations = generalRepository.select(HrOrganization.class, c -> c
				.where(HrOrganizationDynamicSqlSupport.orgPids,
						SqlBuilder.isLike(StringUtils.wrap(
								LEFT_SQUARE_BRACKETS + organizationId + RIGHT_SQUARE_BRACKETS, SymbolConstant.PERCENT)))
				.or(HrOrganizationDynamicSqlSupport.orgId, SqlBuilder.isEqualTo(organizationId)));
		homeCompanyInfo.setCurrentDeptNum(organizations.size());

		// 设置当前所属机构和所有子机构的人数
		List<Long> orgIds = organizations.stream().map(HrOrganization::getOrgId).toList();
		long currentOrgPersonNum = 0;
		homeCompanyInfo.setCurrentCompanyPersonNum(currentOrgPersonNum);

		return homeCompanyInfo;
	}

	@Override
	public List<SysMenu> getCommonFunctions() {

		// 获取当前用户常用功能，按使用次数排序
		LoginUser loginUser = LoginContext.me().getLoginUser();
		List<SysStatisticsCount> statList = generalRepository.select(SysStatisticsCount.class,
				c -> c.where(SysStatisticsCountDynamicSqlSupport.userId, SqlBuilder.isEqualTo(loginUser.getUserId()))
						.orderBy(SysStatisticsCountDynamicSqlSupport.statCount.descending()));
		List<Long> statUrlIdList = statList.stream().map(SysStatisticsCount::getStatUrlId).collect(Collectors.toList());

		// 获取系统常驻常用功能
		List<SysStatisticsUrl> alwaysShowList = generalRepository.select(SysStatisticsUrl.class,
				c -> c.where(SysStatisticsUrlDynamicSqlSupport.alwaysShow, SqlBuilder.isEqualTo(YesOrNotEnum.Y)));

		// 将常驻功能放在统计的常用功能最前边
		if (ObjectUtil.isNotEmpty(alwaysShowList)) {
			statUrlIdList.addAll(0, alwaysShowList.stream().map(SysStatisticsUrl::getStatUrlId).toList());
		}

		// 如果statUrlId大于8，则只截取8个
		if (statUrlIdList.size() > 8) {
			statUrlIdList = statUrlIdList.subList(0, 8);
		}

		// 获取菜单id集合
		List<Long> usualMenuIds = sysStatisticsUrlService.getMenuIdsByStatUrlIdList(statUrlIdList);

		// 获取菜单对应的图标和名称信息
		List<SysMenu> list = sysMenuService.getMenuStatInfoByMenuIds(usualMenuIds);

		// 菜单的icon需要转为大写驼峰
		for (SysMenu sysMenu : list) {
			if (sysMenu.getIcon() != null) {
				String replace = sysMenu.getIcon().replace("-", "_");
				sysMenu.setIcon(CharSequenceUtil.upperFirst(CharSequenceUtil.toCamelCase(replace)));
			}
		}

		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveStatisticsCacheToDb() {
		// key是用户id，value的key是statUrlId，最后的value是次数
		Map<String, Map<Long, Integer>> userRequestStats = requestCountCacheApi.getAllKeyValues();
		Set<String> keys = userRequestStats.keySet();
		Set<Long> userIds = keys.stream().map(NumberUtils::toLong).collect(Collectors.toSet());
		// 缓存为空，则不进行更新操作
		if (ObjectUtil.isEmpty(userIds)) {
			return;
		}

		// 将缓存的数据合并到库中
		List<SysStatisticsCount> cacheCountList = new ArrayList<>();
		for (Long userId : userIds) {
			// 获取用户的访问记录缓存，key是statUrlId，value是次数
			Map<Long, Integer> userCounts = userRequestStats.get(String.valueOf(userId));
			for (Map.Entry<Long, Integer> userCountItem : userCounts.entrySet()) {
				Long statUrlId = userCountItem.getKey();
				Integer count = userCountItem.getValue();
				SysStatisticsCount sysStatisticsCount = new SysStatisticsCount();
				sysStatisticsCount.setUserId(userId);
				sysStatisticsCount.setStatUrlId(statUrlId);
				sysStatisticsCount.setStatCount(count);
				cacheCountList.add(sysStatisticsCount);
			}
		}

		// 查询缓存用户在库中的统计记录
		List<SysStatisticsCount> sysStatisticsCounts = generalRepository.select(SysStatisticsCount.class,
				c -> c.where(SysStatisticsCountDynamicSqlSupport.userId, SqlBuilder.isIn(userIds)));
		for (SysStatisticsCount cacheItem : cacheCountList) {
			boolean haveRecord = false;
			for (SysStatisticsCount dbItem : sysStatisticsCounts) {
				// 如果库里有这次统计数据，则更新这个记录
				if (dbItem.getStatUrlId().equals(cacheItem.getStatUrlId())
						&& dbItem.getUserId().equals(cacheItem.getUserId())) {
					haveRecord = true;
					cacheItem.setStatCountId(dbItem.getStatCountId());
				}
			}
			// 如果库里没有这次的缓存记录，从新生成一个id
			if (!haveRecord) {
				generalRepository.insert(cacheItem);
			}
			else {
				generalRepository.updateByPrimaryKey(cacheItem);
			}
		}
	}

}
