package io.entframework.kernel.resource.starter;

import cn.hutool.core.util.ArrayUtil;
import io.entframework.kernel.rule.plugin.ModularPlugin;
import io.entframework.kernel.rule.plugin.ModuleContext;
import io.entframework.kernel.rule.plugin.ModuleMeta;
import io.entframework.kernel.rule.plugin.PluginAction;

/**
 * @author jeff_qian
 */
public class KernelResourceModuleRegister implements ModularPlugin {

    private static final PluginAction[] SUPPORTED_ACTIONS = { PluginAction.INFO, PluginAction.INIT,
            PluginAction.MIGRATE };

    @Override
    public ModuleMeta getModuleMeta() {
        return new ResourceModuleMeta();
    }

    @Override
    public boolean supports(ModuleContext delimiter) {
        return ArrayUtil.contains(SUPPORTED_ACTIONS, delimiter.getAction());
    }

}
