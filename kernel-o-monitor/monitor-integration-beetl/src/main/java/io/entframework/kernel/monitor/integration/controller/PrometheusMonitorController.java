/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.monitor.integration.controller;

import cn.hutool.core.text.CharSequenceUtil;
import io.entframework.kernel.monitor.api.PrometheusApi;
import io.entframework.kernel.monitor.api.constants.MonitorConstants;
import io.entframework.kernel.monitor.api.pojo.prometheus.PromResultInfo;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 来自prometheus的监控
 *
 * @date 2020/12/30 16:40
 */
@Controller
@ApiResource(name = "来自prometheus的监控")
public class PrometheusMonitorController {

	@Value("${spring.application.name}")
	private String name;

	@Value("${prometheus.url}")
	private String prometheusUrl;

	@Value("${prometheus.instance}")
	private String prometheusInstance;

	@Resource
	private PrometheusApi service;

	/**
	 * tomcat监控页面
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "tomcat监控首页", path = "/view/monitor/tomcat-info")
	public String tomcatIndex() {
		return "/modular/system/monitor/tomcatInfo.html";
	}

	/**
	 * tomcat监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "tomcat监控数据", path = "/view/monitor/get-tomcat-info")
	@ResponseBody
	public String tomcatInfo() {
		Map<String, Object> metricMap = getMetricInfos(getPromQl(), "tomcat_", "", "");
		return JSON.toJSONString(metricMap);
	}

	/**
	 * jvm监控页面
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "jvm监控页面", path = "/view/monitor/jvmInfo")
	public String jvmIndex() {
		return "/modular/system/monitor/jvmInfo.html";
	}

	/**
	 * jvm监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "jvm监控数据", path = "/view/monitor/get-jvm-info")
	@ResponseBody
	public String jvmInfo(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "area", required = false) String area) {
		Map<String, Object> metricMap = getMetricInfos(getPromQl(id, area), "jvm_", "", "");
		return JSON.toJSONString(metricMap);
	}

	/**
	 * 性能监控页面
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "性能监控页面", path = "/view/monitor/performance-info")
	public String performanceIndex() {
		return "/modular/system/monitor/performanceInfo.html";
	}

	/**
	 * 性能监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "CPU监控数据", path = "/view/monitor/getCpuInfo")
	@ResponseBody
	public String cpuInfo() {
		Map<String, Object> metricMap = getMetricInfos(getPromQl(), "cpu_", "", "");
		return JSON.toJSONString(metricMap);
	}

	/**
	 * 服务器负载监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "服务器负载监控数据", path = "/view/monitor/getLoadInfo")
	@ResponseBody
	public String loadInfo() {
		Map<String, Object> metricMap = getMetricInfos(getPromQl(), "system_", "", "");
		return JSON.toJSONString(metricMap);
	}

	/**
	 * 进程监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "进程监控数据", path = "/view/monitor/getProcessInfo")
	@ResponseBody
	public String processInfo() {
		Map<String, Object> metricMap = getMetricInfos(getPromQl(), "process_", "", "");
		return JSON.toJSONString(metricMap);
	}

	/**
	 * 日志监控页面
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "日志监控页面", path = "/view/monitor/logbackInfo")
	public String logbackIndex() {
		return "/modular/system/monitor/logbackInfo.html";
	}

	/**
	 * 日志监控数据
	 *
	 * @date 2021/1/4 16:32
	 */
	@GetResource(name = "日志监控数据", path = "/view/monitor/getLogbackInfo")
	@ResponseBody
	public String logbackInfo(@RequestParam("level") String level, @RequestParam("timeInterval") String timeInterval,
			@RequestParam("isRate") String isRate, @RequestParam("rateMetric") String rateMetric) {
		if (CharSequenceUtil.isEmpty(timeInterval)) {
			timeInterval = "[5m]";
		}
		Map<String, Object> metricMap = getMetricInfos(getIratePromQl(level, timeInterval), "logback_", isRate,
				rateMetric);
		return JSON.toJSONString(metricMap);
	}

	/**
	 * 组装prometheus查询sql
	 * @param id 同一指标不同分类的id号
	 * @param area 查询区域jvm常用
	 * @date 2021/1/4 16:32
	 */
	private String getPromQl(String id, String area) {
		StringBuilder promql = new StringBuilder("{application=\"");
		promql.append(name);
		if (!CharSequenceUtil.isEmpty(id)) {
			promql.append("\",id=\"");
			promql.append(id);
		}
		if (!CharSequenceUtil.isEmpty(area)) {
			promql.append("\",area=\"");
			promql.append(area);
		}
		if (!CharSequenceUtil.isEmpty(prometheusInstance)) {
			promql.append("\",instance=\"");
			promql.append(prometheusInstance);
		}
		promql.append("\"}");
		return promql.toString();
	}

	/**
	 * 组装prometheus查询sql方法重写不带参数
	 *
	 * @date 2021/1/4 16:32
	 */
	private String getPromQl() {
		StringBuilder promql = new StringBuilder("{application=\"");
		promql.append(name);
		if (!CharSequenceUtil.isEmpty(prometheusInstance)) {
			promql.append("\",instance=\"");
			promql.append(prometheusInstance);
		}
		promql.append("\"}");
		return promql.toString();
	}

	/**
	 * 组装prometheus平均值查询sql方法重写不带参数
	 * @param level 日志统计查询参数，info、warn、error、trace、debug
	 * @param timeInterval 统计时间区间单位通常为分钟(m)
	 * @date 2021/1/4 16:32
	 */
	private String getIratePromQl(String level, String timeInterval) {
		StringBuilder promql = new StringBuilder("{application=\"");
		promql.append(name);
		if (!CharSequenceUtil.isEmpty(prometheusInstance)) {
			promql.append("\",instance=\"");
			promql.append(prometheusInstance);
		}
		if (!CharSequenceUtil.isEmpty(level)) {
			promql.append("\",level=\"");
			promql.append(level);
		}
		promql.append("\"}");
		promql.append(timeInterval);
		return promql.toString();
	}

	/**
	 * 分别输出监控名称以及对应的值
	 * @param promQL prometheus查询sql
	 * @param metric prometheus指标前缀，比如："jvm_"
	 * @param isRate prometheus计算函数，不需要计算周期内值就直接为"",需要计算则写计算函数以及对应的指标
	 * @date 2021/1/4 16:32
	 */
	private Map<String, Object> getMetricInfos(String promQL, String metric, String isRate, String rateMetric) {
		Map<String, Object> metricMap = new HashMap<>();
		if (!CharSequenceUtil.isEmpty(prometheusUrl)) {
			List<PromResultInfo> promResultInfos = service.getMetricInfo(
					prometheusUrl.concat(MonitorConstants.PROMETHEUS_QUERY_RANGE), promQL, isRate, rateMetric);
			if (Objects.isNull(promResultInfos)) {
				return metricMap;
			}
			for (PromResultInfo promResultInfo : promResultInfos) {
				String metricName = promResultInfo.getMetric().get__name__();
				JSONArray valueArray = JSON.parseArray(
						JSON.toJSONString(promResultInfo.getValues()).replace("\\\\", "").replace("\"", ""));
				if (!CharSequenceUtil.isEmpty(metricName)) {
					if (metricName.contains(metric)) {
						// 得到的数据为数组，需要转为json字符串去除双引号再转化为JSONArray，JSONArray是echarts时间序列图需要的数据格式
						metricMap.put(metricName, valueArray);
					}
				}
				else {
					// 查询指定的指标
					metricMap.put(rateMetric, valueArray);
				}
			}
		}
		return metricMap;
	}

}
