package io.entframework.kernel.dict.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysDictDynamicSqlSupport {

    public static final SysDict sysDict = new SysDict();

    public static final SqlColumn<Long> dictId = sysDict.dictId;

    public static final SqlColumn<String> dictCode = sysDict.dictCode;

    public static final SqlColumn<String> dictName = sysDict.dictName;

    public static final SqlColumn<String> dictNamePinyin = sysDict.dictNamePinyin;

    public static final SqlColumn<String> dictEncode = sysDict.dictEncode;

    public static final SqlColumn<String> dictTypeCode = sysDict.dictTypeCode;

    public static final SqlColumn<String> dictShortName = sysDict.dictShortName;

    public static final SqlColumn<String> dictShortCode = sysDict.dictShortCode;

    public static final SqlColumn<Long> dictParentId = sysDict.dictParentId;

    public static final SqlColumn<StatusEnum> statusFlag = sysDict.statusFlag;

    public static final SqlColumn<BigDecimal> dictSort = sysDict.dictSort;

    public static final SqlColumn<String> dictPids = sysDict.dictPids;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysDict.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysDict.createTime;

    public static final SqlColumn<Long> createUser = sysDict.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysDict.updateTime;

    public static final SqlColumn<Long> updateUser = sysDict.updateUser;

    public static final SqlColumn<String> createUserName = sysDict.createUserName;

    public static final SqlColumn<String> updateUserName = sysDict.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(dictId, dictCode, dictName, dictNamePinyin,
            dictEncode, dictTypeCode, dictShortName, dictShortCode, dictParentId, statusFlag, dictSort, dictPids,
            delFlag, createTime, createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysDict extends AliasableSqlTable<SysDict> {

        public final SqlColumn<Long> dictId = column("dict_id", JDBCType.BIGINT);

        public final SqlColumn<String> dictCode = column("dict_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dictName = column("dict_name", JDBCType.VARCHAR);

        public final SqlColumn<String> dictNamePinyin = column("dict_name_pinyin", JDBCType.VARCHAR);

        public final SqlColumn<String> dictEncode = column("dict_encode", JDBCType.VARCHAR);

        public final SqlColumn<String> dictTypeCode = column("dict_type_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dictShortName = column("dict_short_name", JDBCType.VARCHAR);

        public final SqlColumn<String> dictShortCode = column("dict_short_code", JDBCType.VARCHAR);

        public final SqlColumn<Long> dictParentId = column("dict_parent_id", JDBCType.BIGINT);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<BigDecimal> dictSort = column("dict_sort", JDBCType.DECIMAL);

        public final SqlColumn<String> dictPids = column("dict_pids", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysDict() {
            super("sys_dict", SysDict::new);
        }

    }

}