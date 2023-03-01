package io.entframework.kernel.system.modular.entity;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysMenuResourceDynamicSqlSupport {

	public static final SysMenuResource sysMenuResource = new SysMenuResource();

	public static final SqlColumn<Long> menuResourceId = sysMenuResource.menuResourceId;

	public static final SqlColumn<Long> menuId = sysMenuResource.menuId;

	public static final SqlColumn<String> resourceCode = sysMenuResource.resourceCode;

	public static final SqlColumn<LocalDateTime> createTime = sysMenuResource.createTime;

	public static final SqlColumn<Long> createUser = sysMenuResource.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysMenuResource.updateTime;

	public static final SqlColumn<Long> updateUser = sysMenuResource.updateUser;

	public static final SqlColumn<String> createUserName = sysMenuResource.createUserName;

	public static final SqlColumn<String> updateUserName = sysMenuResource.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(menuResourceId, menuId, resourceCode,
			createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

	public static final class SysMenuResource extends AliasableSqlTable<SysMenuResource> {

		public final SqlColumn<Long> menuResourceId = column("menu_resource_id", JDBCType.BIGINT);

		public final SqlColumn<Long> menuId = column("menu_id", JDBCType.BIGINT);

		public final SqlColumn<String> resourceCode = column("resource_code", JDBCType.VARCHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysMenuResource() {
			super("sys_menu_resource", SysMenuResource::new);
		}

	}

}