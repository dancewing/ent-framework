package io.entframework.kernel.resource.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_resource")
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
     * 是否是视图类型：Y-是，N-否
     * 如果是视图类型，url需要以 '/view' 开头，
     * 视图类型的接口会渲染出html界面，而不是json数据，
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
}