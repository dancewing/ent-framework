package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class HrOrganizationDynamicSqlSupport {

    public static final HrOrganization hrOrganization = new HrOrganization();

    public static final SqlColumn<Long> orgId = hrOrganization.orgId;

    public static final SqlColumn<Long> orgParentId = hrOrganization.orgParentId;

    public static final SqlColumn<String> orgPids = hrOrganization.orgPids;

    public static final SqlColumn<String> orgName = hrOrganization.orgName;

    public static final SqlColumn<String> orgCode = hrOrganization.orgCode;

    public static final SqlColumn<BigDecimal> orgSort = hrOrganization.orgSort;

    public static final SqlColumn<StatusEnum> statusFlag = hrOrganization.statusFlag;

    public static final SqlColumn<String> orgRemark = hrOrganization.orgRemark;

    public static final SqlColumn<YesOrNotEnum> delFlag = hrOrganization.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = hrOrganization.createTime;

    public static final SqlColumn<Long> createUser = hrOrganization.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = hrOrganization.updateTime;

    public static final SqlColumn<Long> updateUser = hrOrganization.updateUser;

    public static final SqlColumn<String> createUserName = hrOrganization.createUserName;

    public static final SqlColumn<String> updateUserName = hrOrganization.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(orgId, orgParentId, orgPids, orgName, orgCode,
            orgSort, statusFlag, orgRemark, delFlag, createTime, createUser, updateTime, updateUser, createUserName,
            updateUserName);

    public static final class HrOrganization extends AliasableSqlTable<HrOrganization> {

        public final SqlColumn<Long> orgId = column("org_id", JDBCType.BIGINT);

        public final SqlColumn<Long> orgParentId = column("org_parent_id", JDBCType.BIGINT);

        public final SqlColumn<String> orgPids = column("org_pids", JDBCType.VARCHAR);

        public final SqlColumn<String> orgName = column("org_name", JDBCType.VARCHAR);

        public final SqlColumn<String> orgCode = column("org_code", JDBCType.VARCHAR);

        public final SqlColumn<BigDecimal> orgSort = column("org_sort", JDBCType.DECIMAL);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<String> orgRemark = column("org_remark", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public HrOrganization() {
            super("hr_organization", HrOrganization::new);
        }

    }

}