package io.entframework.kernel.file.modular.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysFileStorageDynamicSqlSupport {
    public static final SysFileStorage sysFileStorage = new SysFileStorage();

    public static final SqlColumn<Long> fileId = sysFileStorage.fileId;

    public static final SqlColumn<byte[]> fileBytes = sysFileStorage.fileBytes;

    public static final BasicColumn[] selectList = BasicColumn.columnList(fileId, fileBytes);

    public static final class SysFileStorage extends AliasableSqlTable<SysFileStorage> {
        public final SqlColumn<Long> fileId = column("file_id", JDBCType.BIGINT);

        public final SqlColumn<byte[]> fileBytes = column("file_bytes", JDBCType.LONGVARBINARY);

        public SysFileStorage() {
            super("sys_file_storage", SysFileStorage::new);
        }
    }
}