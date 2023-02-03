SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

-- ----------------------------
-- Table structure for sys_timers
-- ----------------------------
CREATE TABLE `sys_timers`  (
  `timer_id` bigint NOT NULL COMMENT '定时器id',
  `timer_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `action_class` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '执行任务的class的类名（实现了TimerAction接口的类的全称）',
  `cron` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '定时任务表达式',
  `params` varchar(2000) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '参数',
  `job_status` int NULL DEFAULT NULL COMMENT '状态：1-运行，2-停止',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`timer_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

INSERT INTO `sys_timers`(`timer_id`, `timer_name`, `action_class`, `cron`, `params`, `job_status`, `remark`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (1492358213574615041, '常用功能统计', 'io.entframework.kernel.system.modular.home.timer.InterfaceStatisticsTimer', '0/30 * * * * ? ', NULL, 1, '定时常用功能统计刷新到数据库', 'N', '2022-02-12 12:41:39', 1339550467939639299, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
