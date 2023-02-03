package io.entframework.ent.common.starter;

import io.entframework.kernel.rule.plugin.ModularPlugin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;

/**
 * Spring Boot 启动器，生效常用的权限检查等配置
 * @author jeff_qian
 */
@Configuration
@ComponentScan({"io.entframework.ent.common.starter.config"})
@EnablePluginRegistries({ModularPlugin.class})
public class EntCommonSpringAutoConfiguration {

}
