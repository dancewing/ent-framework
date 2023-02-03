/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.common.starter.config;

import io.entframework.kernel.rule.plugin.ModularPlugin;
import io.entframework.kernel.rule.plugin.ModuleContext;
import io.entframework.kernel.rule.plugin.PluginAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author jeff_qian
 */
@Service
@Slf4j
public class ModularPluginInitService {

    @Resource
    private PluginRegistry<ModularPlugin, ModuleContext> modularPluginRegistry;


    @PostConstruct
    public void init() {
        ModuleContext moduleContext = new ModuleContext();
        moduleContext.setAction(PluginAction.MIGRATE);
        List<ModularPlugin> plugins = modularPluginRegistry.getPluginsFor(moduleContext);
        for(ModularPlugin plugin: plugins) {
            log.info("Loaded plugin : {}", plugin.getModuleMeta().getName());
        }
    }
}
