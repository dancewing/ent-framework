/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.rule.context;

import lombok.Getter;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * application.yml或application.properties配置的快速获取
 * <p>
 * 此类的使用必须激活 ConfigInitListener
 *
 * @date 2021/2/26 18:27
 */
@Getter
public class ApplicationPropertiesContext {

    private static final ApplicationPropertiesContext APPLICATION_PROPERTIES_CONTEXT = new ApplicationPropertiesContext();

    private String applicationName = null;

    private String contextPath = null;

    private String profile = null;

    private ApplicationPropertiesContext() {
    }

    public void initConfigs(ConfigurableEnvironment configurableEnvironment) {
        applicationName = configurableEnvironment.getProperty("spring.application.name");
        contextPath = configurableEnvironment.getProperty("server.servlet.context-path");
        profile = configurableEnvironment.getProperty("spring.profiles.active");
    }

    public static ApplicationPropertiesContext getInstance() {
        return APPLICATION_PROPERTIES_CONTEXT;
    }

}
