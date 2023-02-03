package io.entframework.kernel.rule.plugin;

import lombok.Data;

/**
 * 指定插件的context，方便调用对应的Plugin执行对应的业务操作
 * @author jeff_qian
 */
@Data
public class ModuleContext {

    private PluginAction action;

}
