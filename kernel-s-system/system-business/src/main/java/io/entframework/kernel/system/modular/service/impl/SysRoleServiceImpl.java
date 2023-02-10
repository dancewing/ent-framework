/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.auth.api.exception.AuthException;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.resource.api.pojo.ResourceTreeNode;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.tree.antd.CheckedKeys;
import io.entframework.kernel.system.api.constants.SystemConstants;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.role.SysRoleExceptionEnum;
import io.entframework.kernel.system.api.pojo.request.SysRoleDataScopeRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleRequest;
import io.entframework.kernel.system.api.pojo.request.SysRoleResourceRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.SysRoleDataScopeResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleMenuResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleResourceResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.api.util.DataScopeUtil;
import io.entframework.kernel.system.modular.entity.SysRole;
import io.entframework.kernel.system.modular.entity.SysRoleDynamicSqlSupport;
import io.entframework.kernel.system.modular.entity.SysRoleMenu;
import io.entframework.kernel.system.modular.service.*;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统角色service接口实现类
 *
 * @date 2020/11/5 上午11:33
 */
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleRequest, SysRoleResponse, SysRole> implements SysRoleService {

	@Resource
	private SysUserRoleService sysUserRoleService;

	@Resource
	private SysRoleResourceService sysRoleResourceService;

	@Resource
	private SysRoleDataScopeService sysRoleDataScopeService;

	@Resource
	private SysRoleMenuService roleMenuService;

	@Resource
	private SysMenuResourceService sysMenuResourceService;

	@Resource
	private ResourceServiceApi resourceServiceApi;

	@Resource
	private CacheOperatorApi<SysRole> roleInfoCacheApi;

	@Resource(name = "roleResourceCacheApi")
	private CacheOperatorApi<List<String>> roleResourceCacheApi;

	@Resource(name = "roleDataScopeCacheApi")
	private CacheOperatorApi<List<Long>> roleDataScopeCacheApi;

	public SysRoleServiceImpl() {
		super(SysRoleRequest.class, SysRoleResponse.class, SysRole.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(SysRoleRequest sysRoleRequest) {

		SysRole sysRole = this.converterService.convert(sysRoleRequest, getEntityClass());
		// 默认设置为启用
		sysRole.setStatusFlag(StatusEnum.ENABLE);

		// 默认设置为普通角色
		sysRole.setRoleSystemFlag(YesOrNotEnum.N);

		// 默认数据范围
		sysRole.setDataScopeType(DataScopeTypeEnum.SELF);

		this.getRepository().insert(sysRole);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);

		// 超级管理员不能删除
		if (YesOrNotEnum.Y == sysRole.getRoleSystemFlag()) {
			throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
		}

		// 逻辑删除，设为删除标志
		sysRole.setDelFlag(YesOrNotEnum.Y);

		this.getRepository().updateByPrimaryKey(sysRole);

		Long roleId = sysRole.getRoleId();

		// 级联删除该角色对应的角色-数据范围关联信息
		sysRoleDataScopeService.delByRoleId(roleId);

		// 级联删除该角色对应的用户-角色表关联信息
		SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
		sysUserRoleRequest.setRoleId(roleId);
		sysUserRoleService.deleteBy(sysUserRoleRequest);

		// 级联删除该角色对应的角色-菜单表关联信息
		sysRoleResourceService.deleteRoleResourceListByRoleId(roleId);

		// 删除角色缓存信息
		roleInfoCacheApi.remove(String.valueOf(roleId));

		// 删除角色的数据范围缓存
		roleDataScopeCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysRoleResponse update(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);

		// 不能修改超级管理员编码
		if (SystemConstants.SUPER_ADMIN_ROLE_CODE.equals(sysRole.getRoleCode()) &&
				!sysRole.getRoleCode().equals(sysRoleRequest.getRoleCode())) {
			throw new SystemModularException(SysRoleExceptionEnum.SUPER_ADMIN_ROLE_CODE_ERROR);
		}

		// 拷贝属性
		this.converterService.copy(sysRoleRequest, sysRole);

		// 不能修改状态，用修改状态接口修改状态
		sysRole.setStatusFlag(null);

		this.getRepository().updateByPrimaryKey(sysRole);

		// 删除角色缓存信息
		roleInfoCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
		return this.converterService.convert(sysRole, getResponseClass());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStatus(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);
		sysRole.setStatusFlag(sysRoleRequest.getStatusFlag());
		this.getRepository().updateByPrimaryKey(sysRole);
	}

	@Override
	public SysRoleResponse detail(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);
		SysRoleResponse roleResponse = this.converterService.convert(sysRole, getResponseClass());
		// 填充数据范围类型枚举
		roleResponse.setDataScopeTypeEnum(sysRole.getDataScopeType());

		return roleResponse;
	}

	@Override
	public PageResult<SysRoleResponse> findPage(SysRoleRequest sysRoleRequest) {

		return this.page(sysRoleRequest);
	}

	@Override
	public List<SimpleDict> findList(SysRoleRequest sysRoleParam) {
		List<SimpleDict> dictList = CollUtil.newArrayList();

		// 只查询正常状态 // 根据角色名称或编码模糊查询

		// 根据排序升序排列，序号越小越在前
		this.getRepository().select(getEntityClass(), c -> c.where(SysRoleDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
				.and(SysRoleDynamicSqlSupport.roleName,
						SqlBuilder.isLike(sysRoleParam.getRoleName()).filter(ObjectUtil::isNotNull),
						SqlBuilder.or(SysRoleDynamicSqlSupport.roleCode,
								SqlBuilder.isLike(sysRoleParam.getRoleName()).filter(ObjectUtil::isNotNull)))
				.orderBy(SysRoleDynamicSqlSupport.roleSort)).forEach(sysRole -> {
			SimpleDict simpleDict = new SimpleDict();
			simpleDict.setId(sysRole.getRoleId());
			simpleDict.setName(sysRole.getRoleName() + SymbolConstant.LEFT_SQUARE_BRACKETS
					+ sysRole.getRoleCode() + SymbolConstant.RIGHT_SQUARE_BRACKETS);
			dictList.add(simpleDict);
		});
		return dictList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grantMenu(SysRoleRequest sysRoleMenuRequest) {

		// 清除该角色之前的菜单
		roleMenuService.deleteByRoleId(sysRoleMenuRequest.getRoleId());

		// 新增菜单
		CheckedKeys<Long> menuIdList = sysRoleMenuRequest.getGrantMenuIdList();
		if (ObjectUtil.isNotNull(menuIdList)) {
			List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

			// 角色ID
			Long roleId = sysRoleMenuRequest.getRoleId();

			for (Long menuId : menuIdList.getChecked()) {
				SysRoleMenu item = new SysRoleMenu();
				item.setRoleId(roleId);
				item.setMenuId(menuId);
				sysRoleMenus.add(item);
			}

			for (Long menuId : menuIdList.getHalfChecked()) {
				SysRoleMenu item = new SysRoleMenu();
				item.setRoleId(roleId);
				item.setMenuId(menuId);
				sysRoleMenus.add(item);
			}
			roleMenuService.batchCreateEntity(sysRoleMenus);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grantDataScope(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);

		// 获取当前用户是否是超级管理员
		boolean superAdmin = LoginContext.me().getSuperAdminFlag();

		// 获取请求参数的数据范围类型
		DataScopeTypeEnum dataScopeType = sysRoleRequest.getDataScopeType();

		// 如果登录用户不是超级管理员，则进行数据权限校验
		if (!superAdmin) {

			// 只有超级管理员可以授权全部范围
			if (DataScopeTypeEnum.ALL == dataScopeType) {
				throw new AuthException(AuthExceptionEnum.ONLY_SUPER_ERROR);
			}

			// 数据范围类型为自定义，则判断当前用户有没有该公司的权限
			if (DataScopeTypeEnum.DEFINE == dataScopeType) {
				if (ObjectUtil.isEmpty(sysRoleRequest.getGrantOrgIdList())) {
					throw new SystemModularException(SysRoleExceptionEnum.PLEASE_FILL_DATA_SCOPE);
				}
				for (Long orgId : sysRoleRequest.getGrantOrgIdList()) {
					DataScopeUtil.quickValidateDataScope(orgId);
				}
			}
		}

		sysRole.setDataScopeType(sysRoleRequest.getDataScopeType());
		this.getRepository().updateByPrimaryKey(sysRole);

		// 绑定角色数据范围关联
		sysRoleDataScopeService.grantDataScope(sysRoleRequest);

		// 删除角色的数据范围缓存
		roleDataScopeCacheApi.remove(String.valueOf(sysRoleRequest.getRoleId()));
	}

	@Override
	public List<SimpleDict> dropDown() {
		List<SimpleDict> dictList = CollUtil.newArrayList();

		Set<Long> roleIds = new HashSet<>();
		// 如果当前登录用户不是超级管理员，则查询自己拥有的角色
		if (!LoginContext.me().getSuperAdminFlag()) {

			// 查询自己拥有的
			List<SimpleRoleInfo> roles = LoginContext.me().getLoginUser().getSimpleRoleInfoList();

			// 取出所有角色id
			Set<Long> loginUserRoleIds = roles.stream().map(SimpleRoleInfo::getRoleId).collect(Collectors.toSet());
			if (ObjectUtil.isEmpty(loginUserRoleIds)) {
				return dictList;
			}
			roleIds.addAll(loginUserRoleIds);
		}

		// 只查询正常状态
		SelectDSLCompleter completer = c -> c
				.where(SysRoleDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(StatusEnum.ENABLE))
				.and(SysRoleDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N))
				.and(SysRoleDynamicSqlSupport.roleId, SqlBuilder.isIn(roleIds).filter(ObjectUtil::isNotEmpty));

		this.getRepository().select(getEntityClass(), completer).forEach(sysRole -> {
			SimpleDict simpleDict = new SimpleDict();
			simpleDict.setId(sysRole.getRoleId());
			simpleDict.setCode(sysRole.getRoleCode());
			simpleDict.setName(sysRole.getRoleName());
			dictList.add(simpleDict);
		});
		return dictList;
	}

	@Override
	public List<Long> getRoleDataScope(SysRoleRequest sysRoleRequest) {
		SysRole sysRole = this.querySysRole(sysRoleRequest);
		return sysRoleDataScopeService.getRoleDataScopeIdList(CollUtil.newArrayList(sysRole.getRoleId()));
	}

	@Override
	public List<SysRoleResponse> getRolesByIds(List<Long> roleIds) {
		ArrayList<SysRoleResponse> sysRoleResponses = new ArrayList<>();
		for (Long roleId : roleIds) {
			SysRoleRequest sysRoleRequest = new SysRoleRequest();
			sysRoleRequest.setRoleId(roleId);
			SysRoleResponse detail = this.detail(sysRoleRequest);
			sysRoleResponses.add(detail);
		}
		return sysRoleResponses;
	}

	@Override
	public List<Long> getRoleDataScopes(List<Long> roleIds) {

		ArrayList<Long> result = new ArrayList<>();

		if (ObjectUtil.isEmpty(roleIds)) {
			return result;
		}

		for (Long roleId : roleIds) {
			// 从缓存获取数据范围
			String key = String.valueOf(roleId);
			List<Long> scopes = roleDataScopeCacheApi.get(key);
			if (scopes != null) {
				result.addAll(scopes);
			}

			SysRoleDataScopeRequest sysRoleDataScopeRequest = new SysRoleDataScopeRequest();
			sysRoleDataScopeRequest.setRoleId(roleId);
			// 从数据库查询数据范围
			List<SysRoleDataScopeResponse> list = this.sysRoleDataScopeService.select(sysRoleDataScopeRequest);
			if (!list.isEmpty()) {
				List<Long> realScopes = list.stream().map(SysRoleDataScopeResponse::getOrganizationId)
						.toList();
				result.addAll(realScopes);

				// 添加结果到缓存中
				roleDataScopeCacheApi.put(key, realScopes);
			}
		}

		return result;
	}

	@Override
	public List<Long> getMenuIdsByRoleIds(List<Long> roleIds) {

		if (roleIds == null || roleIds.isEmpty()) {
			return new ArrayList<>();
		}

		// 获取角色绑定的菜单
		List<SysRoleMenu> roleMenus = this.roleMenuService.getRoleMenus(roleIds);
		if (roleMenus == null || roleMenus.isEmpty()) {
			return new ArrayList<>();
		}

		return roleMenus.stream().map(SysRoleMenu::getMenuId).toList();
	}

	@Override
	public Set<String> getRoleResourceCodeList(List<Long> roleIdList) {

		HashSet<String> result = new HashSet<>();

		for (Long roleId : roleIdList) {

			// 从缓存获取所有角色绑定的资源
			String key = String.valueOf(roleId);
			List<String> resourceCodesCache = roleResourceCacheApi.get(key);
			if (ObjectUtil.isNotEmpty(resourceCodesCache)) {
				result.addAll(resourceCodesCache);
				continue;
			}

			SysRoleResourceRequest sysRoleResourceRequest = new SysRoleResourceRequest();
			sysRoleResourceRequest.setRoleId(roleId);
			// 从数据库查询角色绑定的资源
			List<SysRoleResourceResponse> sysRoleResources = sysRoleResourceService.select(sysRoleResourceRequest);

			List<String> sysResourceCodes = sysRoleResources.parallelStream().map(SysRoleResourceResponse::getResourceCode)
					.toList();
			if (ObjectUtil.isNotEmpty(sysResourceCodes)) {
				result.addAll(sysResourceCodes);
				roleResourceCacheApi.put(key, sysResourceCodes);
			}
		}

		// 获取角色的所有菜单
		List<SysRoleMenu> list = this.roleMenuService.getRoleMenus(roleIdList);
		List<Long> menuIds = list.stream().map(SysRoleMenu::getMenuId).toList();

		// 获取菜单和按钮所有绑定的资源
		ArrayList<Long> businessIds = new ArrayList<>(menuIds);

		// 获取菜单和按钮
		List<String> menuButtonResources = this.sysMenuResourceService.getResourceCodesByBusinessId(businessIds);
		result.addAll(menuButtonResources);
		return result;
	}

	@Override
	public List<SysRoleResourceResponse> getRoleResourceList(List<Long> roleIdList) {
		return sysRoleResourceService.getRoleResources(roleIdList);
	}

	@Override
	public Set<String> getRoleButtonCodes(List<Long> roleIdList) {
		/*
		 * LambdaQueryWrapper<SysRoleMenuButton> queryWrapper = new
		 * LambdaQueryWrapper<>(); queryWrapper.in(SysRoleMenuButton::getRoleId,
		 * roleIdList); queryWrapper.select(SysRoleMenuButton::getButtonCode);
		 * List<SysRoleMenuButton> list = sysRoleMenuButtonService.list(queryWrapper);
		 * return
		 * list.stream().map(SysRoleMenuButton::getButtonCode).collect(Collectors.toSet(
		 * ));
		 */

		// TODO
		return Collections.EMPTY_SET;
	}

	// TODO
	@Override
	public List<SysRoleMenuResponse> getRoleMenuList(List<Long> roleIdList) {
		List<SysRoleMenu> roleMenus = roleMenuService.getRoleMenus(roleIdList);
		return roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuResponse.class))
				.toList();
	}

	// TODO
	@Override
	public List<SysRoleMenuResponse> getRoleMenuButtonList(List<Long> roleIdList) {
		List<SysRoleMenu> roleMenus = roleMenuService.getRoleMenus(roleIdList);
		return roleMenus.parallelStream().map(item -> BeanUtil.copyProperties(item, SysRoleMenuResponse.class))
				.toList();
	}

	/**
	 * 获取系统角色
	 *
	 * @param sysRoleRequest 请求信息
	 * @date 2020/11/5 下午4:12
	 */
	private SysRole querySysRole(SysRoleRequest sysRoleRequest) {

		// 从缓存中获取角色信息
		String key = String.valueOf(sysRoleRequest.getRoleId());
		SysRole sysRoleCache = roleInfoCacheApi.get(key);
		if (sysRoleCache != null) {
			return sysRoleCache;
		}

		Optional<SysRole> sysRole = this.getRepository().selectByPrimaryKey(getEntityClass(), sysRoleRequest.getRoleId());
		if (sysRole.isEmpty()) {
			throw new SystemModularException(SysRoleExceptionEnum.ROLE_NOT_EXIST);
		}

		// 加入缓存
		roleInfoCacheApi.put(key, sysRole.get());

		return sysRole.get();
	}

	@Override
	public List<ResourceTreeNode> getRoleResourceTree(Long roleId, Boolean treeBuildFlag) {

		// 查询当前角色已有的接口
		List<SysRoleResourceResponse> resourceList = this.getRoleResourceList(Collections.singletonList(roleId));

		// 该角色已拥有权限
		List<String> alreadyList = new ArrayList<>();
		for (SysRoleResourceResponse sysRoleResponse : resourceList) {
			alreadyList.add(sysRoleResponse.getResourceCode());
		}

		Set<String> restrictCodes = new HashSet<>();
		LoginUserApi loginUserApi = LoginContext.me();
		if (!loginUserApi.getSuperAdminFlag()) {
			// 获取权限列表
			List<Long> roleIds = loginUserApi.getLoginUser().getSimpleRoleInfoList().parallelStream()
					.map(SimpleRoleInfo::getRoleId).toList();
			restrictCodes = this.getRoleResourceCodeList(roleIds);
		}

		return this.resourceServiceApi.getResourceList(alreadyList, restrictCodes, treeBuildFlag);
	}

}
