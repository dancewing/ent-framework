package io.entframework.kernel.rule.plugin;

import org.springframework.plugin.core.Plugin;


/**
 * 目前主要用来搜集一些元数据信息
 * @author jeff_qian
 */
public interface ModularPlugin extends Plugin<ModuleContext> {

    /**
     * 模块的元数据信息
     *
     * @return 元数据信息
     */
    ModuleMeta getModuleMeta();

    /**
     * 执行初始化动作
     * 比如关键数据检查，并默认初始化
     */
    default void init(ModuleContext context){}
}
