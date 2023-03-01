package io.entframework.kernel.resource.modular.entity;

import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysResourceDynamicSqlSupport {

	public static final SysResource sysResource = new SysResource();

	public static final SqlColumn<Long> resourceId = sysResource.resourceId;

	public static final SqlColumn<String> appCode = sysResource.appCode;

	public static final SqlColumn<String> resourceCode = sysResource.resourceCode;

	public static final SqlColumn<String> resourceName = sysResource.resourceName;

	public static final SqlColumn<String> projectCode = sysResource.projectCode;

	public static final SqlColumn<String> className = sysResource.className;

	public static final SqlColumn<String> methodName = sysResource.methodName;

	public static final SqlColumn<String> modularCode = sysResource.modularCode;

	public static final SqlColumn<String> modularName = sysResource.modularName;

	public static final SqlColumn<String> ipAddress = sysResource.ipAddress;

	public static final SqlColumn<YesOrNotEnum> viewFlag = sysResource.viewFlag;

	public static final SqlColumn<String> url = sysResource.url;

	public static final SqlColumn<String> httpMethod = sysResource.httpMethod;

	public static final SqlColumn<YesOrNotEnum> requiredLoginFlag = sysResource.requiredLoginFlag;

	public static final SqlColumn<YesOrNotEnum> autoReport = sysResource.autoReport;

	public static final SqlColumn<YesOrNotEnum> requiredPermissionFlag = sysResource.requiredPermissionFlag;

	public static final SqlColumn<Long> createUser = sysResource.createUser;

	public static final SqlColumn<LocalDateTime> createTime = sysResource.createTime;

	public static final SqlColumn<Long> updateUser = sysResource.updateUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysResource.updateTime;

	public static final SqlColumn<String> createUserName = sysResource.createUserName;

	public static final SqlColumn<String> updateUserName = sysResource.updateUserName;

	public static final SqlColumn<String> validateGroups = sysResource.validateGroups;

	public static final SqlColumn<String> paramFieldDescriptions = sysResource.paramFieldDescriptions;

	public static final SqlColumn<String> responseFieldDescriptions = sysResource.responseFieldDescriptions;

	public static final BasicColumn[] selectList = BasicColumn.columnList(resourceId, appCode, resourceCode,
			resourceName, projectCode, className, methodName, modularCode, modularName, ipAddress, viewFlag, url,
			httpMethod, requiredLoginFlag, autoReport, requiredPermissionFlag, createUser, createTime, updateUser,
			updateTime, createUserName, updateUserName, validateGroups, paramFieldDescriptions,
			responseFieldDescriptions);

	public static final class SysResource extends AliasableSqlTable<SysResource> {

		public final SqlColumn<Long> resourceId = column("resource_id", JDBCType.BIGINT);

		public final SqlColumn<String> appCode = column("app_code", JDBCType.VARCHAR);

		public final SqlColumn<String> resourceCode = column("resource_code", JDBCType.VARCHAR);

		public final SqlColumn<String> resourceName = column("resource_name", JDBCType.VARCHAR);

		public final SqlColumn<String> projectCode = column("project_code", JDBCType.VARCHAR);

		public final SqlColumn<String> className = column("class_name", JDBCType.VARCHAR);

		public final SqlColumn<String> methodName = column("method_name", JDBCType.VARCHAR);

		public final SqlColumn<String> modularCode = column("modular_code", JDBCType.VARCHAR);

		public final SqlColumn<String> modularName = column("modular_name", JDBCType.VARCHAR);

		public final SqlColumn<String> ipAddress = column("ip_address", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> viewFlag = column("view_flag", JDBCType.CHAR);

		public final SqlColumn<String> url = column("url", JDBCType.VARCHAR);

		public final SqlColumn<String> httpMethod = column("http_method", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> requiredLoginFlag = column("required_login_flag", JDBCType.CHAR);

		public final SqlColumn<YesOrNotEnum> autoReport = column("auto_report", JDBCType.CHAR);

		public final SqlColumn<YesOrNotEnum> requiredPermissionFlag = column("required_permission_flag", JDBCType.CHAR);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> validateGroups = column("validate_groups", JDBCType.LONGVARCHAR);

		public final SqlColumn<String> paramFieldDescriptions = column("param_field_descriptions",
				JDBCType.LONGVARCHAR);

		public final SqlColumn<String> responseFieldDescriptions = column("response_field_descriptions",
				JDBCType.LONGVARCHAR);

		public SysResource() {
			super("sys_resource", SysResource::new);
		}

	}

}