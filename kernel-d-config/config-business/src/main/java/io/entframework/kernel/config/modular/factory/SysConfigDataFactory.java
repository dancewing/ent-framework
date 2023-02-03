/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.config.modular.factory;

import io.entframework.kernel.config.api.SysConfigDataApi;
import io.entframework.kernel.config.modular.sqladapter.MssqlSysConfigData;
import io.entframework.kernel.config.modular.sqladapter.MysqlSysConfigData;
import io.entframework.kernel.config.modular.sqladapter.OracleSysConfigData;
import io.entframework.kernel.config.modular.sqladapter.PgsqlSysConfigData;
import io.entframework.kernel.rule.enums.DbTypeEnum;
import io.entframework.kernel.rule.util.DatabaseTypeUtil;

/**
 * SysConfigDataApi的创建工厂
 *
 * @date 2021/3/27 21:27
 */
public class SysConfigDataFactory {

    /**
     * 通过jdbc url获取api
     *
     * @date 2021/3/27 21:27
     */
    public static SysConfigDataApi getSysConfigDataApi(String jdbcUrl) {
        DbTypeEnum dbType = DatabaseTypeUtil.getDbType(jdbcUrl);
        if (DbTypeEnum.MYSQL.equals(dbType)) {
            return new MysqlSysConfigData();
        } else if (DbTypeEnum.PG_SQL.equals(dbType)) {
            return new PgsqlSysConfigData();
        } else if (DbTypeEnum.MS_SQL.equals(dbType)) {
            return new MssqlSysConfigData();
        } else if (DbTypeEnum.ORACLE.equals(dbType)) {
            return new OracleSysConfigData();
        } else if (DbTypeEnum.DM.equals(dbType)) {
            return new OracleSysConfigData();
        }
        return new MysqlSysConfigData();
    }

}
