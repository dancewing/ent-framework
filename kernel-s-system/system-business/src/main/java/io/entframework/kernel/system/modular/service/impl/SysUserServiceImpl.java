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
 * ?????????????????????
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

        // ???????????????????????????????????????id
        Long organizationId = sysUserRequest.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ??????bean???????????????????????????????????????
        SysUser sysUser = this.converterService.convert(sysUserRequest, getEntityClass());
        SysUserCreateFactory.fillAddSysUser(sysUser);

        // ????????????????????????
        sysUser.setAvatar(FileConstants.DEFAULT_AVATAR_FILE_ID);

        // ????????????
        this.getRepository().insert(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ???????????????????????????
        if (YesOrNotEnum.Y == sysUser.getSuperAdminFlag()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_DELETE_ADMIN);
        }

        // ????????????????????????????????????
        Long organizationId = sysUser.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ??????????????????????????????Y
        sysUser.setDelFlag(YesOrNotEnum.Y);
        this.getRepository().updateByPrimaryKey(sysUser);

        Long userId = sysUser.getUserId();

        // ??????????????????????????????-?????????????????????
        sysUserRoleService.delByUserId(userId);

        // ??????????????????????????????-???????????????????????????
        sysUserDataScopeService.delByUserId(userId);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserResponse update(SysUserRequest sysUserRequest) {

        // ???????????????????????????????????????id
        Long organizationId = sysUserRequest.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ???????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        this.converterService.copy(sysUserRequest, sysUser);

        // ??????????????????
        SysUserCreateFactory.fillEditSysUser(sysUser);
        this.getRepository().updateByPrimaryKeySelective(sysUser);

        Long sysUserId = sysUser.getUserId();
        // ??????????????????????????????
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

        // ???????????????????????????id
        sysUserRequest.setUserId(LoginContext.me().getLoginUser().getUserId());
        SysUser sysUser = this.querySysUser(sysUserRequest);

        this.converterService.copy(sysUserRequest, sysUser);
        // ???????????????????????????
        // SysUserCreateFactory.fillUpdateInfo(sysUserRequest, sysUser);

        this.getRepository().updateByPrimaryKeySelective(sysUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(SysUserRequest sysUserRequest) {

        // ?????????????????????????????????
        UserStatusEnum statusFlag = sysUserRequest.getStatusFlag();
        UserStatusEnum.validateUserStatus(statusFlag);

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????
        if (YesOrNotEnum.Y == sysUser.getSuperAdminFlag()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
        }

        Long id = sysUser.getUserId();

        // ???????????????????????????????????????????????????
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

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserRequest sysUserRequest) {

        // ?????????????????????userId
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ???????????????????????????
        if (sysUserRequest.getNewPassword().equals(sysUserRequest.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_REPEAT);
        }

        // ???????????????
        if (!passwordStoredEncryptApi.checkPassword(sysUserRequest.getPassword(), sysUser.getPassword())) {
            throw new SystemModularException(SysUserExceptionEnum.USER_PWD_ERROR);
        }

        sysUser.setPassword(passwordStoredEncryptApi.encrypt(sysUserRequest.getNewPassword()));
        this.getRepository().updateByPrimaryKey(sysUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????
        String password = SystemConfigExpander.getDefaultPassWord();
        sysUser.setPassword(passwordStoredEncryptApi.encrypt(password));

        this.getRepository().updateByPrimaryKey(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(SysUserRequest sysUserRequest) {

        // ???????????????id
        Long fileId = sysUserRequest.getAvatar();

        // ???????????????????????????id
        LoginUser loginUser = LoginContext.me().getLoginUser();
        sysUserRequest.setUserId(loginUser.getUserId());

        // ??????????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        sysUser.setAvatar(fileId);
        this.getRepository().updateByPrimaryKey(sysUser);

        // ?????????????????????session??????
        SimpleUserInfo simpleUserInfo = loginUser.getSimpleUserInfo();
        simpleUserInfo.setAvatar(fileId);
        sessionManagerApi.updateSession(LoginContext.me().getToken(), loginUser);

        // ??????????????????????????????
        sysUserCacheOperatorApi.remove(String.valueOf(sysUser.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ?????????????????????????????????????????????
        Long organizationId = sysUser.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        // ?????????????????????
        sysUserRoleService.assignRoles(sysUserRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantData(SysUserRequest sysUserRequest) {
        SysUser sysUser = this.querySysUser(sysUserRequest);

        // ????????????????????????????????????
        Long organizationId = sysUser.getOrgId();

        // ??????????????????????????????????????????
        DataScopeUtil.quickValidateDataScope(organizationId);

        sysUserDataScopeService.assignData(sysUserRequest);
    }

    @Override
    public SysUserResponse detail(SysUserRequest sysUserRequest) {
        // ????????????????????????
        SysUser sysUser = this.querySysUser(sysUserRequest);
        SysUserResponse sysUserResponse = this.converterService.convert(sysUser, getResponseClass());
        // ????????????????????????
        sysUserResponse.setGrantRoleIdList(sysUserRoleService.findRoleIdsByUserId(sysUser.getUserId()));

        return sysUserResponse;
    }

    @Override
    public PageResult<SysUserResponse> findPage(SysUserRequest sysUserRequest) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        // ???????????????????????????????????????
        Set<DataScopeTypeEnum> dataScopeTypeEnums = loginUser.getDataScopeTypeEnums();

        // ????????????????????????????????????????????????
        Set<Long> dataScopeOrganizationIds = loginUser.getDataScopeOrganizationIds();

        // ?????????????????????????????????????????????
        Set<Long> dataScopeUserIds = loginUser.getDataScopeUserIds();

        // ??????????????????????????????
        if (dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEPT_WITH_CHILD)
                || dataScopeTypeEnums.contains(DataScopeTypeEnum.DEFINE)) {
            sysUserRequest.setScopeOrgIds(dataScopeOrganizationIds);
            sysUserRequest.setUserScopeIds(null);
        }
        // ????????????????????????????????????
        else if (dataScopeTypeEnums.contains(DataScopeTypeEnum.SELF)) {
            sysUserRequest.setScopeOrgIds(null);
            sysUserRequest.setUserScopeIds(dataScopeUserIds);
        }
        // ???????????????????????????????????????????????????????????????????????????????????????
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
        excelExportParam.setFileName("??????????????????");
        excelExportParam.setResponse(response);

        officeExcelApi.easyExportDownload(excelExportParam);
    }

    @Override
    public List<UserSelectTreeNode> userSelectTree(SysUserRequest sysUserRequest) {
        // ??????????????????
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
        // ??????????????????
        return new DefaultTreeBuildFactory<UserSelectTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public SysUser getUserByAccount(String account) {

        SysUser query = new SysUser();
        query.setAccount(account);
        query.setDelFlag(YesOrNotEnum.N);

        List<SysUser> list = this.getRepository().selectBy(query);

        // ???????????????
        if (list.isEmpty()) {
            throw new SystemModularException(SysUserExceptionEnum.USER_NOT_EXIST, account);
        }

        // ??????????????????
        if (list.size() > 1) {
            throw new SystemModularException(SysUserExceptionEnum.ACCOUNT_HAVE_MANY, account);
        }

        return list.get(0);
    }

    @Override
    public String getUserAvatarUrl(Long fileId) {

        // ???????????????????????????
        return fileInfoClientApi.getFileAuthUrl(fileId);
    }

    @Override
    public String getUserAvatarUrl(Long fileId, String token) {

        // ???????????????????????????
        return fileInfoClientApi.getFileAuthUrl(fileId, token);
    }

    @Override
    public List<UserSelectTreeNode> getUserTreeNodeList(Long orgId, List<UserSelectTreeNode> treeNodeList) {
        // ??????????????????
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

            // ????????????treeNodeList????????????????????????????????????????????????????????????
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

        // ????????????????????????????????????????????????
        UserLoginInfoDTO userValidateInfo = this.getUserLoginInfo(sysUser.getAccount());

        // ??????LoginUser????????????????????????
        LoginUser loginUser = userValidateInfo.getLoginUser();

        // ???????????????token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                false, null);
        String jwtToken = JwtContext.me().generateTokenDefaultPayload(defaultJwtPayload);
        loginUser.setToken(jwtToken);

        synchronized (this) {

            // ??????ws-url ????????????????????????
            loginUser.setWsUrl(WebSocketConfigExpander.getWebSocketWsUrl());

            // ?????????????????????????????????
            sessionManagerApi.createSession(jwtToken, loginUser, false);

            // ????????????????????????????????????????????????????????????????????????
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

        // 1. ???????????????????????????
        SysUser sysUser = this.getUserByAccount(account);
        Long userId = sysUser.getUserId();

        // 2. ????????????????????????
        List<Long> roleIds = sysUserRoleService.findRoleIdsByUserId(userId);
        if (ObjectUtil.isEmpty(roleIds)) {
            throw new SystemModularException(AuthExceptionEnum.ROLE_IS_EMPTY);
        }
        List<SysRoleResponse> roleResponseList = roleServiceApi.getRolesByIds(roleIds);

        // 3. ???????????????????????????
        DataScopeDTO dataScopeResponse = dataScopeApi.getDataScope(userId, roleResponseList);

        // 4. ???????????????????????????url
        Set<String> resourceCodeList = roleServiceApi.getRoleResourceCodeList(roleIds);
        Set<String> resourceUrlsListByCodes = resourceServiceApi.getResourceUrlsListByCodes(resourceCodeList);

        // 5. ???????????????????????????code??????
        Set<String> roleButtonCodes = roleServiceApi.getRoleButtonCodes(roleIds);

        // 6. ??????????????????
        return UserLoginInfoFactory.userLoginInfoDTO(sysUser, roleResponseList, dataScopeResponse,
                resourceUrlsListByCodes, roleButtonCodes);
    }

    @Override
    public LoginUser getEffectiveLoginUser(LoginUser loginUser) {

        // ?????????C????????????????????????????????????????????????
        if (loginUser.isCustomerFlag()) {
            return loginUser;
        }

        UserLoginInfoDTO userLoginInfoDTO = this.getUserLoginInfo(loginUser.getAccount());
        LoginUser newLoginUser = userLoginInfoDTO.getLoginUser();

        // ???????????????????????????????????????
        newLoginUser.setToken(loginUser.getToken());
        newLoginUser.setTenantCode(loginUser.getTenantCode());
        newLoginUser.setWsUrl(loginUser.getWsUrl());
        newLoginUser.setOtherInfos(loginUser.getOtherInfos());

        return newLoginUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserLoginInfo(Long userId, LocalDateTime date, String ip) {

        // ????????????id????????????????????????
        SysUser sysUser = this.getRepository().get(getEntityClass(), userId);

        if (sysUser != null && YesOrNotEnum.N == sysUser.getDelFlag()) {
            // ????????????????????????
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

        // ????????????
        List<OnlineUserDTO> result = loginUsers.stream().map(OnlineUserCreateFactory::createOnlineUser).toList();

        // ???????????????????????????account????????????
        if (CharSequenceUtil.isNotBlank(onlineUserRequest.getAccount())) {
            return result.stream().filter(i -> i.getAccount().equals(onlineUserRequest.getAccount())).toList();
        }
        else {
            return result;
        }
    }

    @Override
    public SysUserResponse getUserInfoByUserId(Long userId) {

        // ?????????????????????
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

        // ????????????????????????id
        Optional<SysUser> sysUser = this.getRepository().selectByPrimaryKey(getEntityClass(), userId);
        if (sysUser.isEmpty()) {
            return "";
        }
        return this.getUserAvatarUrl(sysUser.get().getAvatar());
    }

    /**
     * ??????????????????
     *
     * @date 2020/3/26 9:54
     */
    private SysUser querySysUser(SysUserRequest sysUserRequest) {

        // ?????????????????????????????????
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
        // ????????????
        sysUserCacheOperatorApi.put(userIdKey, sysUser);

        return sysUser;
    }

}
