package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;

public final class HrPositionDynamicSqlSupport {

    public static final HrPosition hrPosition = new HrPosition();

    public static final SqlColumn<Long> positionId = hrPosition.positionId;

    public static final SqlColumn<String> positionName = hrPosition.positionName;

    public static final SqlColumn<String> positionCode = hrPosition.positionCode;

    public static final SqlColumn<BigDecimal> positionSort = hrPosition.positionSort;

    public static final SqlColumn<StatusEnum> statusFlag = hrPosition.statusFlag;

    public static final SqlColumn<String> positionRemark = hrPosition.positionRemark;

    public static final SqlColumn<YesOrNotEnum> delFlag = hrPosition.delFlag;

    public static final SqlColumn<LocalDateTime> createTime = hrPosition.createTime;

    public static final SqlColumn<Long> createUser = hrPosition.createUser;

    public static final SqlColumn<LocalDateTime> updateTime = hrPosition.updateTime;

    public static final SqlColumn<Long> updateUser = hrPosition.updateUser;

    public static final SqlColumn<String> createUserName = hrPosition.createUserName;

    public static final SqlColumn<String> updateUserName = hrPosition.updateUserName;

    public static final BasicColumn[] selectList = BasicColumn.columnList(positionId, positionName, positionCode,
            positionSort, statusFlag, positionRemark, delFlag, createTime, createUser, updateTime, updateUser,
            createUserName, updateUserName);

    public static final class HrPosition extends AliasableSqlTable<HrPosition> {

        public final SqlColumn<Long> positionId = column("position_id", JDBCType.BIGINT);

        public final SqlColumn<String> positionName = column("position_name", JDBCType.VARCHAR);

        public final SqlColumn<String> positionCode = column("position_code", JDBCType.VARCHAR);

        public final SqlColumn<BigDecimal> positionSort = column("position_sort", JDBCType.DECIMAL);

        public final SqlColumn<StatusEnum> statusFlag = column("status_flag", JDBCType.TINYINT);

        public final SqlColumn<String> positionRemark = column("position_remark", JDBCType.VARCHAR);

        public final SqlColumn<YesOrNotEnum> delFlag = column("del_flag", JDBCType.CHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> createUser = column("create_user", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> updateUser = column("update_user", JDBCType.BIGINT);

        public final SqlColumn<String> createUserName = column("create_user_name", JDBCType.VARCHAR);

        public final SqlColumn<String> updateUserName = column("update_user_name", JDBCType.VARCHAR);

        public HrPosition() {
            super("hr_position", HrPosition::new);
        }

    }

}