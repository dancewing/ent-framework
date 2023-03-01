/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.monitor.api.constants;

/**
 * 监控模块常量
 *
 * @date 2021/1/31 22:33
 */
public interface MonitorConstants {

	/**
	 * 监控模块的名称
	 */
	String MONITOR_MODULE_NAME = "kernel-o-monitor";

	/**
	 * 异常枚举的步进值
	 */
	String MONITOR_EXCEPTION_STEP_CODE = "27";

	/**
	 * prometheus查询命令
	 */
	String MONITOR_PROMETHEUS_QUERY = "query";

	/**
	 * prometheus查询开始时间
	 */
	String MONITOR_PROMETHEUS_START = "start";

	/**
	 * prometheus查询结束时间
	 */
	String MONITOR_PROMETHEUS_END = "end";

	/**
	 * prometheus查询步长
	 */
	String MONITOR_PROMETHEUS_STEP = "step";

	/**
	 * prometheus查询区间向量命令
	 */
	String PROMETHEUS_QUERY_RANGE = "query_range";

}
