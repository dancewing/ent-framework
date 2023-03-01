package io.entframework.kernel.resource.modular.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 资源
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_resource", sqlSupport = SysResourceDynamicSqlSupport.class, tableProperty = "sysResource")
public class SysResource {

    /**
     * 资源id
     */
    @Id
    @Column(name = "resource_id", jdbcType = JDBCType.BIGINT)
    private Long resourceId;

    /**
     * 应用编码
     */
    @Column(name = "app_code", jdbcType = JDBCType.VARCHAR)
    private String appCode;

    /**
     * 资源编码
     */
    @Column(name = "resource_code", jdbcType = JDBCType.VARCHAR)
    private String resourceCode;

    /**
     * 资源名称
     */
    @Column(name = "resource_name", jdbcType = JDBCType.VARCHAR)
    private String resourceName;

    /**
     * 项目编码，一般为spring.application.name
     */
    @Column(name = "project_code", jdbcType = JDBCType.VARCHAR)
    private String projectCode;

    /**
     * 类名称
     */
    @Column(name = "class_name", jdbcType = JDBCType.VARCHAR)
    private String className;

    /**
     * 方法名称
     */
    @Column(name = "method_name", jdbcType = JDBCType.VARCHAR)
    private String methodName;

    /**
     * 资源模块编码，一般为控制器类名排除Controller
     */
    @Column(name = "modular_code", jdbcType = JDBCType.VARCHAR)
    private String modularCode;

    /**
     * 资源模块名称，一般为控制器名称
     */
    @Column(name = "modular_name", jdbcType = JDBCType.VARCHAR)
    private String modularName;

    /**
     * 资源初始化的服务器ip地址
     */
    @Column(name = "ip_address", jdbcType = JDBCType.VARCHAR)
    private String ipAddress;

    /**
     * 是否是视图类型：Y-是，N-否 如果是视图类型，url需要以 '/view' 开头， 视图类型的接口会渲染出html界面，而不是json数据，
     * 视图层一般会在前后端不分离项目出现
     */
    @Column(name = "view_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum viewFlag;

    /**
     * 资源url
     */
    @Column(name = "url", jdbcType = JDBCType.VARCHAR)
    private String url;

    /**
     * http请求方法
     */
    @Column(name = "http_method", jdbcType = JDBCType.VARCHAR)
    private String httpMethod;

    /**
     * 是否需要登录：Y-是，N-否
     */
    @Column(name = "required_login_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum requiredLoginFlag;

    /**
     * 自动上报：Y-是，N-否
     */
    @Column(name = "auto_report", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum autoReport;

    /**
     * 是否需要鉴权：Y-是，N-否
     */
    @Column(name = "required_permission_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum requiredPermissionFlag;

    /**
     * 创建人
     */
    @Column(name = "create_user", jdbcType = JDBCType.BIGINT)
    private Long createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @Column(name = "update_user", jdbcType = JDBCType.BIGINT)
    private Long updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime updateTime;

    /**
     * 创建人账号
     */
    @Column(name = "create_user_name", jdbcType = JDBCType.VARCHAR)
    private String createUserName;

    /**
     * 修改人账号
     */
    @Column(name = "update_user_name", jdbcType = JDBCType.VARCHAR)
    private String updateUserName;

    /**
     * 需要进行参数校验的分组
     */
    @Column(name = "validate_groups", jdbcType = JDBCType.LONGVARCHAR)
    private String validateGroups;

    /**
     * 接口参数的字段描述
     */
    @Column(name = "param_field_descriptions", jdbcType = JDBCType.LONGVARCHAR)
    private String paramFieldDescriptions;

    /**
     * 接口返回结果的字段描述
     */
    @Column(name = "response_field_descriptions", jdbcType = JDBCType.LONGVARCHAR)
    private String responseFieldDescriptions;

    public SysResource resourceId(Long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public SysResource appCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    public SysResource resourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
        return this;
    }

    public SysResource resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public SysResource projectCode(String projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public SysResource className(String className) {
        this.className = className;
        return this;
    }

    public SysResource methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public SysResource modularCode(String modularCode) {
        this.modularCode = modularCode;
        return this;
    }

    public SysResource modularName(String modularName) {
        this.modularName = modularName;
        return this;
    }

    public SysResource ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public SysResource viewFlag(YesOrNotEnum viewFlag) {
        this.viewFlag = viewFlag;
        return this;
    }

    public SysResource url(String url) {
        this.url = url;
        return this;
    }

    public SysResource httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public SysResource requiredLoginFlag(YesOrNotEnum requiredLoginFlag) {
        this.requiredLoginFlag = requiredLoginFlag;
        return this;
    }

    public SysResource autoReport(YesOrNotEnum autoReport) {
        this.autoReport = autoReport;
        return this;
    }

    public SysResource requiredPermissionFlag(YesOrNotEnum requiredPermissionFlag) {
        this.requiredPermissionFlag = requiredPermissionFlag;
        return this;
    }

    public SysResource createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public SysResource createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public SysResource updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public SysResource updateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public SysResource createUserName(String createUserName) {
        this.createUserName = createUserName;
        return this;
    }

    public SysResource updateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
        return this;
    }

    public SysResource validateGroups(String validateGroups) {
        this.validateGroups = validateGroups;
        return this;
    }

    public SysResource paramFieldDescriptions(String paramFieldDescriptions) {
        this.paramFieldDescriptions = paramFieldDescriptions;
        return this;
    }

    public SysResource responseFieldDescriptions(String responseFieldDescriptions) {
        this.responseFieldDescriptions = responseFieldDescriptions;
        return this;
    }

}