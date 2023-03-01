package io.entframework.kernel.db.flyway;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.FlywayExceptionEnum;
import io.entframework.kernel.rule.plugin.ModularPlugin;
import io.entframework.kernel.rule.plugin.ModuleContext;
import io.entframework.kernel.rule.plugin.ModuleMeta;
import io.entframework.kernel.rule.plugin.PluginAction;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeff_qian
 */
@Slf4j
public class FlywayService {

	private static final String FLYWAY_LOCATIONS = "classpath:db/migration/mysql";

	public void migrate(DataSourceProperties dataSourceProperties, FlywayProperties flywayProperties,
			List<ModularPlugin> plugins) {
		if (!flywayProperties.isEnabled()) {
			log.info("Flyway is disabled, abort init database.");
			return;
		}

		// 如果有为空的配置，终止执行
		if (ObjectUtil.hasEmpty(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(),
				dataSourceProperties.getPassword(), dataSourceProperties.getDriverClassName())) {
			throw new DaoException(FlywayExceptionEnum.DB_CONFIG_ERROR);
		}

		List<String> checkList = new ArrayList<>();
		ModuleContext moduleContext = new ModuleContext();
		moduleContext.setAction(PluginAction.MIGRATE);
		for (ModularPlugin plugin : plugins) {
			ModuleMeta moduleMeta = plugin.getModuleMeta();
			if (CharSequenceUtil.isEmpty(moduleMeta.getFlywayLocations())
					|| CharSequenceUtil.isEmpty(moduleMeta.getFlywayTableSuffix())) {
				throw new DaoException(FlywayExceptionEnum.PLUGIN_FLYWAY_CONFIG_ERROR,
						moduleMeta.getName() + " 的 flywayLocations或者flywayTableSuffix未设置");
			}
			if (checkList.contains(moduleMeta.getFlywayTableSuffix())) {
				throw new DaoException(FlywayExceptionEnum.PLUGIN_FLYWAY_CONFIG_ERROR,
						moduleMeta.getName() + " 的flywayTableSuffix重复");
			}
			checkList.add(moduleMeta.getFlywayTableSuffix());
		}

		DriverManagerDataSource dmDataSource = null;

		try {
			// 手动创建数据源
			dmDataSource = new DriverManagerDataSource();
			dmDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
			dmDataSource.setUrl(dataSourceProperties.getUrl());
			dmDataSource.setUsername(dataSourceProperties.getUsername());
			dmDataSource.setPassword(dataSourceProperties.getPassword());

			for (ModularPlugin plugin : plugins) {
				ModuleMeta moduleMeta = plugin.getModuleMeta();
				// flyway配置
				Flyway flyway = Flyway.configure().dataSource(dmDataSource)

						// 迁移脚本的位置
						.locations(moduleMeta.getFlywayLocations())

						// 当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移
						.baselineOnMigrate(flywayProperties.isBaselineOnMigrate())

						// 是否允许无序的迁移 开发环境最好开启 , 生产环境关闭
						.outOfOrder(flywayProperties.isOutOfOrder())

						// 是否开启占位符
						.placeholderReplacement(flywayProperties.isPlaceholderReplacement())

						// 定制化table，每个插件独立的flyway基准表
						.table("flyway_schema_history" + moduleMeta.getFlywayTableSuffix())

						.load();

				// 执行迁移
				flyway.migrate();
			}

		}
		catch (Exception e) {
			log.error("flyway初始化失败", e);
			throw new DaoException(FlywayExceptionEnum.FLYWAY_MIGRATE_ERROR, e.getMessage());
		}
	}

}
