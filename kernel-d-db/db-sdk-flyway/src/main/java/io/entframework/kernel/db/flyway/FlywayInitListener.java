/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.flyway;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.FlywayExceptionEnum;
import io.entframework.kernel.rule.enums.DbTypeEnum;
import io.entframework.kernel.rule.listener.ContextInitializedListener;
import io.entframework.kernel.rule.util.DatabaseTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;

/**
 * flyway 数据库脚本初始化 当spring装配好配置后开始初始化 通常在business 模块，需要做DB操作时继承该类
 *
 * @author jeff_qian
 * @date 2021/1/17 21:14
 */
@Slf4j
public abstract class FlywayInitListener extends ContextInitializedListener {

	public void flywayMigrate(Environment environment, String locations, String tableSuffix) {

		// 获取数据库连接配置
		String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
		String dataSourceUrl = environment.getProperty("spring.datasource.url");
		String dataSourceUsername = environment.getProperty("spring.datasource.username");
		String dataSourcePassword = environment.getProperty("spring.datasource.password");

		// 判断是否开启 sharding jdbc
		Boolean enableSharding = environment.getProperty("spring.shardingsphere.enabled", Boolean.class);

		// 如果开启了sharding jdbc，则读取 sharding jdbc 主库配置
		if (enableSharding != null && enableSharding) {
			driverClassName = environment.getProperty("spring.shardingsphere.datasource.master.driver-class-name");
			dataSourceUrl = environment.getProperty("spring.shardingsphere.datasource.master.url");
			dataSourceUsername = environment.getProperty("spring.shardingsphere.datasource.master.username");
			dataSourcePassword = environment.getProperty("spring.shardingsphere.datasource.master.password");
		}

		// flyway的配置
		String enabledStr = environment.getProperty("spring.flyway.enabled");
		String baselineOnMigrateStr = environment.getProperty("spring.flyway.baseline-on-migrate");
		String placeholder = environment.getProperty("spring.flyway.placeholder-replacement");

		// 是否开启flyway，默认false.
		boolean enabled = false;
		if (CharSequenceUtil.isNotBlank(enabledStr)) {
			enabled = Boolean.parseBoolean(enabledStr);
		}

		// 如果未开启flyway 直接return
		if (!enabled) {
			return;
		}

		// 如果有为空的配置，终止执行
		if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword, driverClassName)) {
			throw new DaoException(FlywayExceptionEnum.DB_CONFIG_ERROR);
		}

		// 当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移，默认false.
		boolean baselineOnMigrate = false;
		if (CharSequenceUtil.isNotBlank(baselineOnMigrateStr)) {
			baselineOnMigrate = Boolean.parseBoolean(baselineOnMigrateStr);
		}

		// 如果未设置flyway路径，则设置为默认flyway路径
		if (CharSequenceUtil.isBlank(locations)) {
			throw new DaoException(FlywayExceptionEnum.DB_CONFIG_ERROR);
		}

		// 是否开启占位符
		boolean enablePlaceholder = true;
		if (CharSequenceUtil.isNotBlank(placeholder)) {
			enablePlaceholder = Boolean.parseBoolean(placeholder);
		}

		String flywayTable = "flyway_schema_history";
		if (StringUtils.isNotEmpty(tableSuffix)) {
			flywayTable = flywayTable + tableSuffix;
		}
		DriverManagerDataSource dmDataSource = null;
		try {
			assert dataSourceUrl != null;
			// 手动创建数据源
			dmDataSource = new DriverManagerDataSource();
			dmDataSource.setDriverClassName(driverClassName);
			dmDataSource.setUrl(dataSourceUrl);
			dmDataSource.setUsername(dataSourceUsername);
			dmDataSource.setPassword(dataSourcePassword);

			DbTypeEnum dbType = DatabaseTypeUtil.getDbType(dataSourceUrl);
			String schemaLocation = locations;
			if (schemaLocation.endsWith(File.separator)) {
				schemaLocation = schemaLocation + dbType.getXmlDatabaseId();
			}
			else {
				schemaLocation = schemaLocation + File.separator + dbType.getXmlDatabaseId();
			}
			// flyway配置
			Flyway flyway = Flyway.configure().dataSource(dmDataSource)

					// 迁移脚本的位置
					.locations(schemaLocation)

					// 当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移
					.baselineOnMigrate(baselineOnMigrate)

					// 是否允许无序的迁移 开发环境最好开启 , 生产环境关闭
					.outOfOrder(false)

					// 是否开启占位符
					.placeholderReplacement(enablePlaceholder)

					// 定制化table，每个插件独立的flyway基准表
					.table(flywayTable)

					.load();

			// 执行迁移
			flyway.migrate();

		}
		catch (Exception e) {
			log.error("flyway初始化失败, locations: {}, table: {}", locations, flywayTable);
			throw new DaoException(FlywayExceptionEnum.FLYWAY_MIGRATE_ERROR, e.getMessage());
		}
	}

}
