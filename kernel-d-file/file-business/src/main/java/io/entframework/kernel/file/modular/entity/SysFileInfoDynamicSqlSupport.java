package io.entframework.kernel.file.modular.entity;

import io.entframework.kernel.file.api.enums.FileStatusEnum;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysFileInfoDynamicSqlSupport {

	public static final SysFileInfo sysFileInfo = new SysFileInfo();

	public static final SqlColumn<Long> fileId = sysFileInfo.fileId;

	public static final SqlColumn<Long> fileCode = sysFileInfo.fileCode;

	public static final SqlColumn<Integer> fileVersion = sysFileInfo.fileVersion;

	public static final SqlColumn<FileStatusEnum> fileStatus = sysFileInfo.fileStatus;

	public static final SqlColumn<FileStorageEnum> fileLocation = sysFileInfo.fileLocation;

	public static final SqlColumn<String> fileBucket = sysFileInfo.fileBucket;

	public static final SqlColumn<String> fileOriginName = sysFileInfo.fileOriginName;

	public static final SqlColumn<String> fileSuffix = sysFileInfo.fileSuffix;

	public static final SqlColumn<Long> fileSizeKb = sysFileInfo.fileSizeKb;

	public static final SqlColumn<String> fileSizeInfo = sysFileInfo.fileSizeInfo;

	public static final SqlColumn<String> fileObjectName = sysFileInfo.fileObjectName;

	public static final SqlColumn<String> filePath = sysFileInfo.filePath;

	public static final SqlColumn<YesOrNotEnum> secretFlag = sysFileInfo.secretFlag;

	public static final SqlColumn<YesOrNotEnum> delFlag = sysFileInfo.delFlag;

	public static final SqlColumn<LocalDateTime> createTime = sysFileInfo.createTime;

	public static final SqlColumn<Long> createUser = sysFileInfo.createUser;

	public static final SqlColumn<LocalDateTime> updateTime = sysFileInfo.updateTime;

	public static final SqlColumn<Long> updateUser = sysFileInfo.updateUser;

	public static final SqlColumn<String> createUserName = sysFileInfo.createUserName;

	public static final SqlColumn<String> updateUserName = sysFileInfo.updateUserName;

	public static final BasicColumn[] selectList = BasicColumn.columnList(fileId, fileCode, fileVersion, fileStatus,
			fileLocation, fileBucket, fileOriginName, fileSuffix, fileSizeKb, fileSizeInfo, fileObjectName, filePath,
			secretFlag, delFlag, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

	public static final class SysFileInfo extends AliasableSqlTable<SysFileInfo> {

		public final SqlColumn<Long> fileId = column("file_id", JDBCType.BIGINT);

		public final SqlColumn<Long> fileCode = column("file_code", JDBCType.BIGINT);

		public final SqlColumn<Integer> fileVersion = column("file_version", JDBCType.INTEGER);

		public final SqlColumn<FileStatusEnum> fileStatus = column("file_status", JDBCType.CHAR);

		public final SqlColumn<FileStorageEnum> fileLocation = column("file_location", JDBCType.TINYINT);

		public final SqlColumn<String> fileBucket = column("file_bucket", JDBCType.VARCHAR);

		public final SqlColumn<String> fileOriginName = column("file_origin_name", JDBCType.VARCHAR);

		public final SqlColumn<String> fileSuffix = column("file_suffix", JDBCType.VARCHAR);

		public final SqlColumn<Long> fileSizeKb = column("file_size_kb", JDBCType.BIGINT);

		public final SqlColumn<String> fileSizeInfo = column("file_size_info", JDBCType.VARCHAR);

		public final SqlColumn<String> fileObjectName = column("file_object_name", JDBCType.VARCHAR);

		public final SqlColumn<String> filePath = column("file_path", JDBCType.VARCHAR);

		public final SqlColumn<YesOrNotEnum> secretFlag = column("secret_flag", JDBCType.CHAR);

		public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

		public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

		public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

		public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

		public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

		public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

		public SysFileInfo() {
			super("sys_file_info", SysFileInfo::new);
		}

	}

}