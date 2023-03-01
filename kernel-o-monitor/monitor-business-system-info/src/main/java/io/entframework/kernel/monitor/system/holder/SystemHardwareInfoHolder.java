/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.system.holder;

import io.entframework.kernel.monitor.system.SystemHardwareCalculator;
import io.entframework.kernel.timer.api.TimerAction;

/**
 * 定时刷新服务器状态信息
 *
 * @date 2021/1/31 21:52
 */
public class SystemHardwareInfoHolder implements TimerAction {

	private SystemHardwareCalculator systemHardwareCalculator = null;

	@Override
	public void action(String params) {
		SystemHardwareCalculator newInfo = new SystemHardwareCalculator();
		newInfo.calc();
		systemHardwareCalculator = newInfo;
	}

	public SystemHardwareCalculator getSystemHardwareInfo() {
		if (systemHardwareCalculator != null) {
			return systemHardwareCalculator;
		}

		systemHardwareCalculator = new SystemHardwareCalculator();
		systemHardwareCalculator.calc();
		return systemHardwareCalculator;
	}

}
