/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.timer.modular.tasks;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.timer.api.TimerAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 这是一个定时任务的示例程序
 *
 * @date 2020/6/30 22:09
 */
@Component
@Slf4j
public class SystemOutTaskRunner implements TimerAction {

    @Override
    public void action(String params) {
        log.info(CharSequenceUtil.format("这是一个定时任务测试的程序，一直输出这行内容！这个是参数: {}", params));
    }

}
