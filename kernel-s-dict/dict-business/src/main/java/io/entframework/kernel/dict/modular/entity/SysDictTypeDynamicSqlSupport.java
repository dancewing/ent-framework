package io.entframework.kernel.dict.modular.entity;

import io.entframework.kernel.dict.api.enums.DictTypeClassEnum;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SysDictTypeDynamicSqlSupport {

    public static final SysDictType sysDictType = new SysDictType();

    public static final SqlColumn<Long> dictTypeId = sysDictType.dictTypeId;

    public static final SqlColumn<DictTypeClassEnum> dictTypeClass = sysDictType.dictTypeClass;

    public static final SqlColumn<String> dictTypeBusCode = sysDictType.dictTypeBusCode;

    public static final SqlColumn<String> dictTypeCode = sysDictType.dictTypeCode;

    public static final SqlColumn<String> dictTypeName = sysDictType.dictTypeName;

    public static final SqlColumn<String> dictTypeNamePinyin = sysDictType.dictTypeNamePinyin;

    public static final SqlColumn<String> dictTypeDesc = sysDictType.dictTypeDesc;

    public static final SqlColumn<StatusEnum> statusFlag = sysDictType.statusFlag;

    public static final SqlColumn<BigDecimal> dictTypeSort = sysDictType.dictTypeSort;

    public static final SqlColumn<YesOrNotEnum> delFlag = sysDictType.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = sysDictType.createTime;

    public static final SqlColumn<Long> createUser = sysDictType.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = sysDictType.updateTime;

    public static final SqlColumn<Long> updateUser = sysDictType.updateUser;

    public static final SqlColumn<String> createUserName = sysDictType.createUserName;

    public static final SqlColumn<String> updateUserName = sysDictType.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(dictTypeId, dictTypeClass, dictTypeBusCode,
            dictTypeCode, dictTypeName, dictTypeNamePinyin, dictTypeDesc, statusFlag, dictTypeSort, delFlag, createTime,
            createUser, updateTime, updateUser, createUserName, updateUserName);

    public static final class SysDictType extends AliasableSqlTable<SysDictType> {

        public final SqlColumn<Long> dictTypeId = column("dict_type_id", JDBCType.BIGINT);

        public final SqlColumn<DictTypeClassEnum> dictTypeClass = column("dict_type_class", JDBCType.INTEGER);

        public final SqlColumn<String> dictTypeBusCode = column("dict_type_bus_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dictTypeCode = column("dict_type_code", JDBCType.VARCHAR);

        public final SqlColumn<String> dictTypeName = column("dict_type_name", JDBCType.VARCHAR);

        public final SqlColumn<String> dictTypeNamePinyin = column("dict_type_name_pinyin", JDBCType.VARCHAR);

        public final SqlColumn<String> dictTypeDesc = column("dict_type_desc", JDBCType.VARCHAR);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<BigDecimal> dictTypeSort = column("dict_type_sort", JDBCType.DECIMAL);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public SysDictType() {
            super("sys_dict_type", SysDictType::new);
        }

    }

}