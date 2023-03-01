package io.entframework.kernel.log.starter;

import cn.hutool.core.util.ArrayUtil;
import io.entframework.kernel.rule.plugin.ModularPlugin;
import io.entframework.kernel.rule.plugin.ModuleContext;
import io.entframework.kernel.rule.plugin.ModuleMeta;
import io.entframework.kernel.rule.plugin.PluginAction;

/**
 *
 */
public class KernelLogModuleRegister implements ModularPlugin {

	private static final PluginAction[] SUPPORTED_ACTIONS = { PluginAction.INFO, PluginAction.INIT,
			PluginAction.MIGRATE };

	@Override
	public ModuleMeta getModuleMeta() {
		return new LogModuleMeta();
	}

	@Override
	public boolean supports(ModuleContext delimiter) {
		return ArrayUtil.contains(SUPPORTED_ACTIONS, delimiter.getAction());
	}

}
