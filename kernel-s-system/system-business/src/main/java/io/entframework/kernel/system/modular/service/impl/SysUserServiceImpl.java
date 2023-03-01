/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.system.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.enums.DataScopeTypeEnum;
import io.entframework.kernel.auth.api.exception.enums.AuthExceptionEnum;
import io.entframework.kernel.auth.api.expander.AuthConfigExpander;
import io.entframework.kernel.auth.api.password.PasswordStoredEncryptApi;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.auth.api.pojo.login.basic.SimpleUserInfo;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.file.api.FileInfoClientApi;
import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.jwt.api.context.JwtContext;
import io.entframework.kernel.jwt.api.pojo.payload.DefaultJwtPayload;
import io.entframework.kernel.message.api.expander.WebSocketConfigExpander;
import io.entframework.kernel.office.api.OfficeExcelApi;
import io.entframework.kernel.office.api.pojo.report.ExcelExportParam;
import io.entframework.kernel.resource.api.ResourceServiceApi;
import io.entframework.kernel.rule.enums.TreeNodeEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.tree.factory.DefaultTreeBuildFactory;
import io.entframework.kernel.system.api.DataScopeApi;
import io.entframework.kernel.system.api.OrganizationServiceApi;
import io.entframework.kernel.system.api.RoleServiceApi;
import io.entframework.kernel.system.api.enums.DevopsCheckStatusEnum;
import io.entframework.kernel.system.api.enums.UserStatusEnum;
import io.entframework.kernel.system.api.exception.SystemModularException;
import io.entframework.kernel.system.api.exception.enums.user.SysUserExceptionEnum;
import io.entframework.kernel.system.api.expander.SystemConfigExpander;
import io.entframework.kernel.system.api.pojo.organization.DataScopeDTO;
import io.entframework.kernel.system.api.pojo.request.SysUserRequest;
import io.entframework.kernel.system.api.pojo.request.SysUserRoleRequest;
import io.entframework.kernel.system.api.pojo.response.HrOrganizationResponse;
import io.entframework.kernel.system.api.pojo.response.SysRoleResponse;
import io.entframework.kernel.system.api.pojo.response.SysUserResponse;
import io.entframework.kernel.system.api.pojo.response.SysUserRoleResponse;
import io.entframework.kernel.system.api.pojo.user.OnlineUserDTO;
import io.entframework.kernel.system.api.pojo.user.OnlineUserRequest;
import io.entframework.kernel.system.api.pojo.user.UserLoginInfoDTO;
import io.entframework.kernel.system.api.pojo.user.UserSelectTreeNode;
import io.entframework.kernel.system.api.util.DataScopeUtil;
import io.entframework.kernel.system.modular.entity.SysUser;
import io.entframework.kernel.system.modular.entity.SysUserDynamicSqlSupport;
import io.entframework.kernel.system.modular.factory.OnlineUserCreateFactory;
import io.entframework.kernel.system.modular.factory.SysUserCreateFactory;
import io.entframework.kernel.system.modular.factory.UserLoginInfoFactory;
import io.entframework.kernel.system.modular.service.SysUserDataScopeService;
import io.entframework.kernel.system.modular.service.SysUserRoleService;
import io.entframework.kernel.system.modular.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户服务实现类
 *
 * @date 2020/11/21 15:04
 */
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUserRequest, SysUserResponse, SysUser>
        implements SysUserService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private OfficeExcelApi officeExcelApi;

    @Resource
    private DataScopeApi dataScopeApi;

    @Resource
    private RoleServiceApi roleServiceApi;

    @Resource
    private ResourceServiceApi resourceServiceApi;

    @Resource
    private FileInfoClientApi fileInfoClientApi;

    @Resource
    private PasswordStoredEncryptApi passwordStoredEncryptApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private OrganizationServiceApi organizationServiceApi;

    @Resource
    private CacheOperatorApi<SysUser> sysUserCacheOperatorApi;

    public SysUserServiceImpl() {
        super(SysUserRequest.class, SysUserResponse.class, SysUser.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getOrgId();

        // 获取用户有无该企业的数据权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 请求bean转为实体，填充一些基本属性
        SysUser sysUser = this.converterService.convert(sysUserRequest, getEntityClass());
        SysUserCreateFactory.fillAddSysUser(sysUser);

        // 设置用户默认头像
        sysUser.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_ID);

        // 保存用户
        this.getRepository().insert(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 不能删除超级管理员
        if (YesOrNotEnum.Y == sysUser.getSuperAdminFlag()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // 获取被授权用户的所属机构
        Long organizationId = sysUser.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 逻辑删除，设置标识位Y
        sysUser.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysUser);

        Long userId = sysUser.getUserId();

        // 删除该用户对应的用户-角色表关联信息
        sysUserRoleService.delByUserId(userId);

        // 删除该用户对应的用户-数据范围表关联信息
        sysUserDataScopeService.delByUserId(userId);

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserResponse update(SysUserRequest sysUserRequest) {

        // 获取被添加用户的主组织机构id
        Long organizationId = sysUserRequest.getOrgId();

        // 获取用户有无该企业的数据权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 转化为实体
        SysUser sysUser = this.querySysUser(sysUserRequest);
        this.converterService.copy(sysUserRequest, sysUser);

        // 填充基础参数
        SysUserCreateFactory.fillEditSysUser(sysUser);
        this.getRepository().updateByPrimaryKeySelective(sysUser);

        Long sysUserId = sysUser.getUserId();
        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUserId));

        return this.converterService.convert(sysUser, getResponseClass());
    }

    @Override
    public SysUser update(SysUser entity) {
        return this.getRepository().update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(SysUserRequest sysUserRequest) {

        // 获取当前登录用户的id
        sysUserRequest.setUserId(LoginContext.me().getLoginUser().getUserId());
        SysUser sysUser = this.querySysUser(sysUserRequest);

        this.converterService.copy(sysUserRequest, sysUser);
        // 填充更新用户的信息
        // SysUserCreateFactory.fillUpdateInfo(sysUserRequest, sysUser);

        this.getRepository().updateByPrimaryKeySelective(sysUser);

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SysUserRequest sysUserRequest) {

        // 校验状态在不在枚举值里
        UserStatusEnum statusFlag = sysUserRequest.getStatusFlag();
        UserStatusEnum.validateUserStatus(statusFlag);

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 不能修改超级管理员状态
        if (YesOrNotEnum.Y == sysUser.getSuperAdminFlag()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getUserId();

        // 更新枚举，更新只能更新未删除状态的
        int update = this.getRepository().update(getEntityClass(), c -> {
            c.set(SysUserDynamicSqlSupport.statusFlag).equalTo(statusFlag)
                    .where(SysUserDynamicSqlSupport.userId, SqlBuilder.isEqualTo(id))
                    .and(SysUserDynamicSqlSupport.delFlag, SqlBuilder.isEqualTo(YesOrNotEnum.N));
            return c;
        });
        if (update == 0) {
            log.error(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR.getUserTip());
            throw new SystemModularException(SysUserExceptionEnum.UPDATE_USER_STATUS_ERROR);
        }

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserRequest sysUserRequest) {

        // 获取当前用户的userId
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 新密码与原密码相同
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // 原密码错误
        if (!passwordStoredEncryptApi.checkPassword(sysUserRequest.getPassword(), sysUser.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        sysUser.setPassword(passwordStoredEncryptApi.encrypt(sysUserRequest.getNewPassword()));
        this.getRepository().updateByPrimaryKey(sysUser);

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取系统配置的默认密码
        String password = SystemConfigExpander.getDefaultPassWord();
        sysUser.setPassword(passwordStoredEncryptApi.encrypt(password));

        this.getRepository().updateByPrimaryKey(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(SysUserRequest sysUserRequest) {

        // 新头像文件id
        Long fileId = sysUserRequest.getAvatar();

        // 从当前用户获取用户id
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        // 更新用户头像
        SysUser sysUser = this.querySysUser(sysUserRequest);
        sysUser.setAvatar(fileId);
        this.getRepository().updateByPrimaryKey(sysUser);

        // 更新当前用户的session信息
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();
        simpleUserInfo.setAvatar(fileId);
        sessionManagerApi.updateSession(LoginContext.me().getToken(), loginUser);

        // 清除缓存中的用户信息
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取要授权角色的用户的所属机构
        Long organizationId = sysUser.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        // 给用户授权角色
        sysUserRoleService.assignRoles(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantData(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // 获取被授权用户的所属机构
        Long organizationId = sysUser.getOrgId();

        // 判断当前用户有无该用户的权限
        DataScopeUtil.quickValidateDataScope(organizationId);

        sysUserDataScopeService.assignData(sysUserRequest);
    }

    @Override
    public SysUserResponse detail(SysUserRequest sysUserRequest) {
        // 获取用户基本信息
        SysUser sysUser = this.querySysUser(sysUserRequest);
        SysUserResponse sysUserResponse = this.converterService.convert(sysUser, getResponseClass());
        // 获取用户角色信息
        sysUserResponse.setGrantRoleIdList(sysUserRoleService.findRoleIdsByUserId(sysUser.getUserId()));

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserResponse> findPage(SysUserRequest sysUserRequest) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 获取当前用户数据范围的枚举
        Set<DataScopeTypeEnum> dataScopeTypeEnums = loginUser.getDataScopeTypeEnums();

        // 获取当前用户数绑定的组织机构范围
        Set<Long> dataScopeOrganizationIds = loginUser.getDataScopeOrganizationIds();

        // 获取当前用户绑定的用户数据范围
        Set<Long> dataScopeUserIds = loginUser.getDataScopeUserIds();

        // 如果是按部门数据划分
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT_WITH_CHILD)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEFINE)) {
            sysUserRequest.setScopeOrgIds(dataScopeOrganizationIds);
            sysUserRequest.setUserScopeIds(null);
        }
        // 如果包含了仅有自己的数据
        else if (dataScopeTypeEnums.contains(DataScopeTypeEnum.SELF)) {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(dataScopeUserIds);
        }
        // 其他情况，没有设置数据范围，则查所有，或者是包含了全部数据
        else {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(null);
        }

        sysUserRequest.setDelFlag(YesOrNotEnum.N);

        return this.page(sysUserRequest);
    }

    @Override
    public List<SysUserResponse> getUserList(SysUserRequest sysUserRequest) {
        return this.list(sysUserRequest);
    }

    @Override
    public void export(HttpServletResponse response) {
        ExcelExportParam excelExportParam = new ExcelExportParam();
        List<SysUserResponse> sysUserList = this.select(new SysUserRequest());

        excelExportParam.setClazz(SysUser.class);
        excelExportParam.setDataList(sysUserList);
        excelExportParam.setExcelTypeEnum(ExcelTypeEnum.XLS);
        excelExportParam.setFileName("系统用户导出");
        excelExportParam.setResponse(response);

        officeExcelApi.easyExportDownload(excelExportParam);
    }

    @Override
    public List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest) {
        // 定义返回结果
        List<UserSelectTreeNode> treeNodeList = CollUtil.newArrayList();
        List<HrOrganizationResponse> orgList = organizationServiceApi.orgList();
        UserSelectTreeNode orgTreeNode;
        for (HrOrganizationResponse hrOrganization : orgList) {
            orgTreeNode = new UserSelectTreeNode();
            orgTreeNode.setId(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setPId(String.valueOf(hrOrganization.getOrgParentId()));
            orgTreeNode.setName(hrOrganization.getOrgName());
            orgTreeNode.setNodeType(TreeNodeEnum.ORG.getCode());
            orgTreeNode.setValue(String.valueOf(hrOrganization.getOrgId()));
            orgTreeNode.setSort(hrOrganization.getOrgSort());
            treeNodeList.add(orgTreeNode);
            List<UserSelectTreeNode> userNodeList = this.getUserTreeNodeList(hrOrganization.getOrgId(), treeNodeList);
            if (!userNodeList.isEmpty()) {
                treeNodeList.addAll(userNodeList);
            }
        }
        // 构建树并返回
        return new DefaultTreeBuildFactory<UserSelectTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public SysUser getUserByAccount(String account) {

        SysUser query = new SysUser();
        query.setAccount(account);
        query.setDelFlag(YesOrNotEnum.N);

        List<SysUser> list = this.getRepository().selectBy(query);

        // 用户不存在
        if (list.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, account);
        }

        // 账号存在多个
        if (list.size() > 1) {
            throw new SystemModularException(SysUserExceptionEnum.ACCOUNT_HAVE_MANY, account);
        }

        return list.get(0);
    }

    @Override
    public String getUserAvatarUrl(Long fileId) {

        // 获取头像的访问地址
        return fileInfoClientApi.getFileAuthUrl(fileId);
    }

    @Override
    public String getUserAvatarUrl(Long fileId, String token) {

        // 获取头像的访问地址
        return fileInfoClientApi.getFileAuthUrl(fileId, token);
    }

    @Override
    public List<UserSelectTreeNode> getUserTreeNodeList(Long orgId, List<UserSelectTreeNode> treeNodeList) {
        // 定义返回结果
        List<UserSelectTreeNode> newTreeNodeList = CollUtil.newArrayList();
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setOrgId(orgId);
        List<SysUserResponse> userList = this.select(userRequest);
        UserSelectTreeNode userTreeNode;
        for (SysUserResponse user : userList) {
            userTreeNode = new UserSelectTreeNode();
            userTreeNode.setId(String.valueOf(user.getUserId()));
            userTreeNode.setPId(String.valueOf(user.getOrgId()));
            userTreeNode.setName(user.getRealName());
            userTreeNode.setNodeType(TreeNodeEnum.USER.getCode());
            userTreeNode.setValue(String.valueOf(user.getUserId()));

            // 判断参数treeNodeList是否包含这个用户，如果包含了就不用返回了
            boolean fillThisUser = true;
            for (UserSelectTreeNode userSelectTreeNode : treeNodeList) {
                if (userSelectTreeNode.getNodeId().equals(userTreeNode.getId())) {
                    fillThisUser = false;
                    break;
                }
            }
            if (fillThisUser) {
                newTreeNodeList.add(userTreeNode);
            }
        }
        return newTreeNodeList;
    }

    @Override
    public List<SimpleDict> selector(SysUserRequest sysUserRequest) {
        SelectDSLCompleter completer = c -> c.where(SysUserDynamicSqlSupport.superAdminFlag,
                SqlBuilder.isNotEqualTo(YesOrNotEnum.Y));
        List<SysUser> list = this.getRepository().select(getEntityClass(), completer);
        ArrayList<SimpleDict> results = new ArrayList<>();
        for (SysUser sysUser : list) {
            SimpleDict simpleDict = new SimpleDict();
            simpleDict.setId(sysUser.getUserId());
            simpleDict.setName(sysUser.getRealName());
            simpleDict.setCode(sysUser.getAccount());
            results.add(simpleDict);
        }

        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(SysUserRequest sysUserRequest) {
        List<Long> userIds = sysUserRequest.getUserIds();
        int k = 0;
        for (Long userId : userIds) {
            SysUserRequest tempRequest = new SysUserRequest();
            tempRequest.setUserId(userId);
            this.del(tempRequest);
            k++;
        }
        return k;
    }

    @Override
    public List<Long> getAllUserIds() {
        SelectDSLCompleter completer = c -> c
                .where(SysUserDynamicSqlSupport.delFlag, SqlBuilder.isNotEqualTo(YesOrNotEnum.Y))
                .and(SysUserDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(UserStatusEnum.ENABLE));

        List<SysUser> list = this.getRepository().select(getEntityClass(), completer);
        return list.stream().map(SysUser::getUserId).toList();
    }

    @Override
    public List<SysUserRequest> getAllUserIdList() {
        if (!SystemConfigExpander.getDevSwitchStatus()) {
            return new ArrayList<>();
        }
        SelectDSLCompleter completer = c -> c
                .where(SysUserDynamicSqlSupport.delFlag, SqlBuilder.isNotEqualTo(YesOrNotEnum.Y))
                .and(SysUserDynamicSqlSupport.statusFlag, SqlBuilder.isEqualTo(UserStatusEnum.ENABLE));

        List<SysUser> list = this.getRepository().select(getEntityClass(), completer);
        return list.stream().map(item -> BeanUtil.toBean(item, SysUserRequest.class)).toList();
    }

    @Override
    public String getTokenByUserId(Long userId) {
        if (!SystemConfigExpander.getDevSwitchStatus() || !LoginContext.me().getSuperAdminFlag()) {
            return null;
        }

        SysUser sysUser = this.getRepository().get(getEntityClass(), userId);

        // 获取用户密码的加密值和用户的状态
        UserLoginInfoDTO userValidateInfo = this.getUserLoginInfo(sysUser.getAccount());

        // 获取LoginUser，用于用户的缓存
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                false, null);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        synchronized (this) {

            // 获取ws-url 保存到用户信息中
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // 缓存用户信息，创建会话
            sessionManagerApi.createSession(jwtToken, loginUser, false);

            // 如果开启了单账号单端在线，则踢掉已经上线的该用户
            if (AuthConfigExpander.getSingleAccountLoginFlag()) {
                sessionManagerApi.removeSessionExcludeToken(jwtToken);
            }
        }

        return jwtToken;
    }

    @Override
    public Integer devopsApiCheck(SysUserRequest sysUserRequest) {
        String account = sysUserRequest.getAccount();
        String password = sysUserRequest.getPassword();
        SysUser sysUser = this.getUserByAccount(account);
        if (ObjectUtil.isEmpty(sysUser)) {
            return DevopsCheckStatusEnum.USER_NOT_EXIST.getCode();
        }
        else if (!passwordStoredEncryptApi.checkPassword(password, sysUser.getPassword())) {
            return DevopsCheckStatusEnum.ACCOUNT_PASSWORD_ERROR.getCode();
        }
        else if (!SystemConfigExpander.getDevSwitchStatus()) {
            return DevopsCheckStatusEnum.REQUESTER_NOT_OPEN_SWITCH.getCode();
        }
        return DevopsCheckStatusEnum.SUCCESSFUL.getCode();
    }

    @Override
    public UserLoginInfoDTO getUserLoginInfo(String account) {

        // 1. 获取用户和账号信息
        SysUser sysUser = this.getUserByAccount(account);
        Long userId = sysUser.getUserId();

        // 2. 获取用户角色信息
        List<Long> roleIds = sysUserRoleService.findRoleIdsByUserId(userId);
        if (ObjectUtil.isEmpty(roleIds)) {
            throw new SystemModularException(AuthExceptionEnum.ROLE_IS_EMPTY);
        }
        List<SysRoleResponse> roleResponseList = roleServiceApi.getRolesByIds(roleIds);

        // 3. 获取用户的数据范围
        DataScopeDTO dataScopeResponse = dataScopeApi.getDataScope(userId, roleResponseList);

        // 4. 获取用户的所有资源url
        Set<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
        Set<String> resourceUrlsListByCodes = resourceServiceApi.getResourceUrlsListByCodes(resourceCodeList);

        // 5. 获取用户的所有按钮code集合
        Set<String> roleButtonCodes = roleServiceApi.getRoleButtonCodes(roleIds);

        // 6. 组装响应结果
        return UserLoginInfoFactory.userLoginInfoDTO(sysUser, roleResponseList, dataScopeResponse,
                resourceUrlsListByCodes, roleButtonCodes);
    }

    @Override
    public LoginUser getEffectiveLoginUser(LoginUser loginUser) {

        // 如果是C端用户，直接返回缓存中的登录用户
        if (loginUser.isCustomerFlag()) {
            return loginUser;
        }

        UserLoginInfoDTO userLoginInfoDTO = this.getUserLoginInfo(loginUser.getAccount());
        LoginUser newLoginUser = userLoginInfoDTO.getLoginUser();

        // 设置登录用户原有的一些信息
        newLoginUser.setToken(loginUser.getToken());
        newLoginUser.setTenantCode(loginUser.getTenantCode());
        newLoginUser.setWsUrl(loginUser.getWsUrl());
        newLoginUser.setOtherInfos(loginUser.getOtherInfos());

        return newLoginUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserLoginInfo(Long userId, LocalDateTime date, String ip) {

        // 根据用户id获取用户信息实体
        SysUser sysUser = this.getRepository().get(getEntityClass(), userId);

        if (sysUser != null && YesOrNotEnum.N == sysUser.getDelFlag()) {
            // 更新用户登录信息
            sysUser.setLastLoginIp(ip);
            sysUser.setLastLoginTime(date);
            this.getRepository().update(sysUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserDataScopeListByOrgIdList(Set<Long> organizationIds) {
        if (organizationIds != null && !organizationIds.isEmpty()) {
            sysUserDataScopeService.delete(organizationIds);
        }
    }

    @Override
    public List<Long> getUserRoleIdList(Long userId) {
        SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
        sysUserRoleRequest.setUserId(userId);
        List<SysUserRoleResponse> list = sysUserRoleService.select(sysUserRoleRequest);
        return list.stream().map(SysUserRoleResponse::getRoleId).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoleListByRoleId(Long roleId) {
        if (roleId != null) {
            SysUserRoleRequest sysUserRoleRequest = new SysUserRoleRequest();
            sysUserRoleRequest.setRoleId(roleId);
            sysUserRoleService.deleteBy(sysUserRoleRequest);
        }
    }

    @Override
    public List<Long> getUserBindDataScope(Long userId) {
        return sysUserDataScopeService.findOrgIdsByUserId(userId);
    }

    @Override
    public List<OnlineUserDTO> onlineUserList(OnlineUserRequest onlineUserRequest) {
        List<LoginUser> loginUsers = sessionManagerApi.onlineUserList();

        // 对象转化
        List<OnlineUserDTO> result = loginUsers.stream().map(OnlineUserCreateFactory::createOnlineUser).toList();

        // 如果带了条件则根据account筛选结果
        if (CharSequenceUtil.isNotBlank(onlineUserRequest.getAccount())) {
            return result.stream().filter(i -> i.getAccount().equals(onlineUserRequest.getAccount())).toList();
        }
        else {
            return result;
        }
    }

    @Override
    public SysUserResponse getUserInfoByUserId(Long userId) {

        // 从缓存查询用户
        SysUser user = sysUserCacheOperatorApi.get(String.valueOf(userId));
        if (user != null) {
            return this.converterService.convert(user, getResponseClass());
        }
        SysUser query = new SysUser();
        query.setUserId(userId);
        Optional<SysUser> sysUser = this.getRepository().selectOne(query, true);
        if (sysUser.isPresent()) {
            SysUserResponse result = this.converterService.convert(sysUser.get(), getResponseClass());
            sysUserCacheOperatorApi.put(String.valueOf(userId), sysUser.get());
            return result;
        }
        return null;
    }

    @Override
    public List<Long> queryAllUserIdList(SysUserRequest sysUserRequest) {
        SysUser query = this.converterService.convert(sysUserRequest, getEntityClass());
        List<SysUser> sysUsers = this.getRepository().selectBy(query);
        return sysUsers.stream().map(SysUser::getUserId).toList();
    }

    @Override
    public Boolean userExist(Long userId) {
        SysUserRequest userRequest = new SysUserRequest();
        userRequest.setUserId(userId);

        return this.countBy(userRequest) > 0L;
    }

    @Override
    public String getUserAvatarUrlByUserId(Long userId) {

        // 获取用户头像文件id
        Optional<SysUser> sysUser = this.getRepository().selectByPrimaryKey(getEntityClass(), userId);
        if (sysUser.isEmpty()) {
            return "";
        }
        return this.getUserAvatarUrl(sysUser.get().getAvatar());
    }

    /**
     * 获取系统用户
     *
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {

        // 先从缓存中获取用户信息
        String userIdKey = String.valueOf(sysUserRequest.getUserId());
        SysUser sysUserResponse = sysUserCacheOperatorApi.get(userIdKey);
        if (sysUserResponse != null) {
            return sysUserResponse;
        }
        SysUser query = new SysUser();
        query.setUserId(sysUserRequest.getUserId());
        Optional<SysUser> optionalSysUser = this.getRepository().selectOne(query, true);
        if (optionalSysUser.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, sysUserRequest.getUserId());
        }
        SysUser sysUser = optionalSysUser.get();
        // 放入缓存
        sysUserCacheOperatorApi.put(userIdKey, sysUser);

        return sysUser;
    }

}
