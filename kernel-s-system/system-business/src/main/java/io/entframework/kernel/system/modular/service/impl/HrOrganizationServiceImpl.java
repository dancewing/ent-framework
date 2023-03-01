/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.db.api.context.DbOperatorContext;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.db.mybatis.mapper.GeneralMapperSupport;
import io.entframework.kernel.rule.constants.SymbolConstant;
import io.entframework.kernel.rule.constants.TreeConstants;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import io.entframework.kernel.system.api.RoleDataScopeServiceApi;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.organization.OrganizationExceptionEnum;
import io.entframework.kernel.system.api.pojo.organization.OrganizationTreeNode;
import io.entframework.kernel.system.api.pojo.request.HrOrganizationRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.api.util.DataScopeUtil;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import io.entframework.kernel.system.modular.entity.HrOrganizationDynamicSqlSupport;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.entity.SysUserDynamicSqlSupport;
import io.entframework.kernel.system.modular.factory.OrganizationFactory;
import io.entframework.kernel.system.modular.service.HrOrganizationService;
import io.entframework.kernel.system.modular.service.SysUserDataScopeService;
import jakarta.annotation.Resource;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 组织架构管理
 *
 * @date 2020/11/04 11:05
 */
public class HrOrganizationServiceImpl
		extends BaseServiceImpl<HrOrganizationRequest, HrOrganizationResponse, HrOrganization>
		implements HrOrganizationService {

	@Resource
	private GeneralMapperSupport generalMapperSupport;

	@Resource
	private SysUserDataScopeService sysUserDataScopeService;

	@Resource
	private RoleDataScopeServiceApi roleDataScopeServiceApi;

	public HrOrganizationServiceImpl() {
		super(HrOrganizationRequest.class, HrOrganizationResponse.class, HrOrganization.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(HrOrganizationRequest hrOrganizationRequest) {

		// 获取父id
		Long pid = hrOrganizationRequest.getOrgParentId();

		// 校验数据范围
		DataScopeUtil.quickValidateDataScope(pid);

		HrOrganization hrOrganization = this.converterService.convert(hrOrganizationRequest, getEntityClass());

		hrOrganization.setOrgId(null);

		// 填充parentIds
		this.fillParentIds(hrOrganization);

		// 设置状态为启用，未删除状态
		hrOrganization.setStatusFlag(StatusEnum.ENABLE);

		this.getRepository().insert(hrOrganization);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(HrOrganizationRequest hrOrganizationRequest) {

		HrOrganizationResponse hrOrganization = this.get(hrOrganizationRequest.getOrgId());
		Long organizationId = hrOrganization.getOrgId();

		// 校验数据范围
		DataScopeUtil.quickValidateDataScope(organizationId);

		// 该机构下有员工，则不能删
		long count = getRepository().count(SysUser.class,
				c -> c.where(SysUserDynamicSqlSupport.orgId, SqlBuilder.isEqualTo(hrOrganization.getOrgId()))
						.and(SysUserDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N)));
		if (count > 0) {
			throw new SystemModularException(OrganizationExceptionEnum.DELETE_ORGANIZATION_ERROR);
		}

		// 级联删除子节点，逻辑删除
		Set<Long> childIdList = DbOperatorContext.me().findSubListByParentId("hr_organization", "org_pids", "org_id",
				organizationId);
		childIdList.add(organizationId);

		UpdateDSLCompleter completer = c -> c.set(HrOrganizationDynamicSqlSupport.delFlag).equalTo(YesOrNotEnum.Y)
				.where(HrOrganizationDynamicSqlSupport.orgId, SqlBuilder.isIn(childIdList));

		this.getRepository().update(completer);

		// 删除角色对应的组织架构数据范围
		roleDataScopeServiceApi.delByOrgIds(childIdList);

		// 删除用户对应的组织架构数据范围
		sysUserDataScopeService.delete(childIdList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HrOrganizationResponse update(HrOrganizationRequest hrOrganizationRequest) {

		HrOrganization hrOrganization = this.queryOrganization(hrOrganizationRequest);
		Long id = hrOrganization.getOrgId();

		// 校验数据范围
		DataScopeUtil.quickValidateDataScope(id);

		hrOrganization = this.converterService.convert(hrOrganizationRequest, getEntityClass());

		// 填充parentIds
		this.fillParentIds(hrOrganization);

		// 不能修改状态，用修改状态接口修改状态
		// hrOrganization.setStatusFlag();

		// 更新这条记录
		this.getRepository().update(hrOrganization);

		return this.converterService.convert(hrOrganization, getResponseClass());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStatus(HrOrganizationRequest hrOrganizationRequest) {
		HrOrganization hrOrganization = this.queryOrganization(hrOrganizationRequest);
		hrOrganization.setStatusFlag(hrOrganizationRequest.getStatusFlag());
		this.getRepository().update(hrOrganization);
	}

	@Override
	public HrOrganizationResponse detail(HrOrganizationRequest hrOrganizationRequest) {
		return this.selectOne(hrOrganizationRequest);
	}

	@Override
	public PageResult<HrOrganizationResponse> findPage(HrOrganizationRequest hrOrganizationRequest) {
		return this.page(hrOrganizationRequest);
	}

	@Override
	public List<HrOrganizationResponse> findList(HrOrganizationRequest hrOrganizationRequest) {
		List<HrOrganizationResponse> organizationDTOS = this.select(hrOrganizationRequest);
		return new DefaultTreeBuildFactory<HrOrganizationResponse>().doTreeBuild(organizationDTOS);
	}

	@Override
	public List<OrganizationTreeNode> organizationTree(HrOrganizationRequest hrOrganizationRequest) {

		// 定义返回结果
		List<OrganizationTreeNode> treeNodeList = CollUtil.newArrayList();

		// 组装节点
		List<HrOrganization> hrOrganizationList = this.findListByDataScope(hrOrganizationRequest);
		for (HrOrganization hrOrganization : hrOrganizationList) {
			OrganizationTreeNode treeNode = OrganizationFactory.parseOrganizationTreeNode(hrOrganization);
			treeNodeList.add(treeNode);
		}

		// 设置树节点上，用户绑定的组织机构数据范围
		if (hrOrganizationRequest.getUserId() != null) {
			List<Long> orgIds = sysUserDataScopeService.findOrgIdsByUserId(hrOrganizationRequest.getUserId());
			if (ObjectUtil.isNotEmpty(orgIds)) {
				for (OrganizationTreeNode organizationTreeNode : treeNodeList) {
					for (Long orgId : orgIds) {
						if (organizationTreeNode.getId().equals(orgId)) {
							organizationTreeNode.setSelected(true);
						}
					}
				}
			}
		}

		// 构建树并返回
		return new DefaultTreeBuildFactory<OrganizationTreeNode>().doTreeBuild(treeNodeList);
	}

	@Override
	public Set<Long> findAllLevelParentIdsByOrganizations(Set<Long> organizationIds) {

		// 定义返回结果
		Set<Long> allLevelParentIds = new HashSet<>(organizationIds);

		// 查询出这些节点的pids字段
		List<HrOrganization> organizationList = this.getRepository().select(getEntityClass(),
				c -> c.where(HrOrganizationDynamicSqlSupport.orgId, SqlBuilder.isIn(organizationIds)));
		if (organizationList == null || organizationList.isEmpty()) {
			return allLevelParentIds;
		}

		// 把所有的pids分割，并放入到set中
		for (HrOrganization hrOrganization : organizationList) {

			// 获取pids值
			String pids = hrOrganization.getOrgPids();

			// 去掉所有的左中括号
			pids = CharSequenceUtil.removeAll(pids, SymbolConstant.LEFT_SQUARE_BRACKETS);

			// 去掉所有的右中括号
			pids = CharSequenceUtil.removeAll(pids, SymbolConstant.RIGHT_SQUARE_BRACKETS);

			// 按逗号分割这个字符串，得到pid的数组
			String[] finalPidArray = pids.split(StrPool.COMMA);

			// 遍历这些值，放入到最终的set
			for (String pid : finalPidArray) {
				allLevelParentIds.add(Convert.toLong(pid));
			}
		}
		return allLevelParentIds;
	}

	@Override
	public List<HrOrganizationResponse> orgList() {

		QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder = SqlBuilder
				.select(HrOrganizationDynamicSqlSupport.selectList).from(getEntityClass()).where();

		// 如果是超级管理员 或 数据范围是所有，则不过滤数据范围
		boolean needToDataScope = true;
		if (LoginContext.me().getSuperAdminFlag()) {
			Set<DataScopeTypeEnum> dataScopeTypes = LoginContext.me().getLoginUser().getDataScopeTypeEnums();
			if (dataScopeTypes != null && dataScopeTypes.contains(DataScopeTypeEnum.ALL)) {
				needToDataScope = false;
			}
		}

		// 如果需要数据范围过滤，则获取用户的数据范围，拼接查询条件
		if (needToDataScope) {
			Set<Long> dataScope = LoginContext.me().getLoginUser().getDataScopeOrganizationIds();

			// 数据范围没有，直接返回空
			if (ObjectUtil.isEmpty(dataScope)) {
				return new ArrayList<>();
			}

			// 根据组织机构数据范围的上级组织，用于展示完整的树形结构
			Set<Long> allLevelParentIdsByOrganizations = this.findAllLevelParentIdsByOrganizations(dataScope);

			// 拼接查询条件
			builder.and(HrOrganizationDynamicSqlSupport.orgId, SqlBuilder.isIn(allLevelParentIdsByOrganizations));
		}

		// 只查询未删除的
		builder.and(HrOrganizationDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N));

		// 根据排序升序排列，序号越小越在前
		builder.orderBy(HrOrganizationDynamicSqlSupport.orgSort.descending());

		SelectStatementProvider selectStatement = builder.build().render(RenderingStrategies.MYBATIS3);
		// 返回数据
		List<HrOrganization> list = this.generalMapperSupport.selectMany(selectStatement);
		return list.stream().filter(Objects::nonNull)
				.map(hrOrganization -> this.converterService.convert(hrOrganization, getResponseClass())).toList();
	}

	/**
	 * 获取系统组织机构
	 *
	 * @date 2020/11/04 11:05
	 */
	private HrOrganization queryOrganization(HrOrganizationRequest hrOrganizationRequest) {
		HrOrganization hrOrganization = this.getRepository().get(getEntityClass(), hrOrganizationRequest.getOrgId());
		if (ObjectUtil.isEmpty(hrOrganization)) {
			throw new SystemModularException(OrganizationExceptionEnum.CANT_FIND_ORG, hrOrganizationRequest.getOrgId());
		}
		return hrOrganization;
	}

	/**
	 * 填充该节点的pIds
	 * <p>
	 * 如果pid是顶级节点，pids就是 [-1],
	 * <p>
	 * 如果pid不是顶级节点，pids就是父节点的pids + [pid] + ,
	 *
	 * @date 2020/11/5 13:45
	 */
	private void fillParentIds(HrOrganization hrOrganization) {
		if (TreeConstants.DEFAULT_PARENT_ID.equals(hrOrganization.getOrgParentId())) {
			hrOrganization.setOrgPids(SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID
					+ SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
		}
		else {
			// 获取父组织机构
			HrOrganizationRequest hrOrganizationRequest = new HrOrganizationRequest();
			hrOrganizationRequest.setOrgId(hrOrganization.getOrgParentId());
			HrOrganization parentOrganization = this.queryOrganization(hrOrganizationRequest);

			// 设置本节点的父ids为 (上一个节点的pids + (上级节点的id) )
			hrOrganization.setOrgPids(parentOrganization.getOrgPids() + SymbolConstant.LEFT_SQUARE_BRACKETS
					+ parentOrganization.getOrgId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
		}
	}

	/**
	 * 根据数据范围获取组织机构列表
	 *
	 * @date 2021/2/8 20:22
	 */
	private List<HrOrganization> findListByDataScope(HrOrganizationRequest hrOrganizationRequest) {

		QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder = SqlBuilder
				.select(HrOrganizationDynamicSqlSupport.selectList).from(HrOrganizationDynamicSqlSupport.hrOrganization)
				.where();

		// 数据范围过滤
		// 如果是超级管理员，或者数据范围权限是所有，则不过滤数据范围
		boolean needToDataScope = true;
		Set<DataScopeTypeEnum> dataScopeTypes = LoginContext.me().getLoginUser().getDataScopeTypeEnums();
		if (LoginContext.me().getSuperAdminFlag()
				|| (dataScopeTypes != null && dataScopeTypes.contains(DataScopeTypeEnum.ALL))) {
			needToDataScope = false;
		}

		// 过滤数据范围的SQL拼接
		if (needToDataScope) {
			// 获取用户数据范围信息
			Set<Long> dataScope = LoginContext.me().getLoginUser().getDataScopeOrganizationIds();

			// 如果数据范围为空，则返回空数组
			if (ObjectUtil.isEmpty(dataScope)) {
				return new ArrayList<>();
			}

			// 根据组织机构数据范围的上级组织，用于展示完整的树形结构
			Set<Long> allLevelParentIdsByOrganizations = this.findAllLevelParentIdsByOrganizations(dataScope);
			// 拼接查询条件
			builder.and(HrOrganizationDynamicSqlSupport.orgId, SqlBuilder.isIn(allLevelParentIdsByOrganizations));
		}
		SelectStatementProvider selectStatement = builder.build().render(RenderingStrategies.MYBATIS3);

		return this.generalMapperSupport.selectMany(selectStatement);
	}

}
