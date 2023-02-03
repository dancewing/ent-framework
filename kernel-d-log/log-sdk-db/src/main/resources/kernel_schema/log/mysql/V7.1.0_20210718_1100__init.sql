SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `sys_log`  (
  `log_id` bigint NOT NULL COMMENT '主键',
  `log_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志的名称，一般为业务名称',
  `log_content` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志记录的内容',
  `app_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '服务名称，一般为spring.application.name',
  `request_url` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前用户请求的url',
  `request_params` longtext COLLATE utf8mb4_unicode_ci NULL COMMENT 'http或方法的请求参数体',
  `request_result` longtext COLLATE utf8mb4_unicode_ci NULL COMMENT 'http或方法的请求结果',
  `server_ip` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前服务器的ip',
  `client_ip` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户端的ip',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `http_method` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求http方法',
  `client_browser` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户浏览器标识',
  `client_os` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户操作系统',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
CREATE TABLE `sys_login_log`  (
  `llg_id` bigint NOT NULL COMMENT '主键',
  `llg_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志名称',
  `llg_succeed` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否执行成功',
  `llg_message` text COLLATE utf8mb4_unicode_ci NULL COMMENT '具体消息',
  `llg_ip_address` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录ip',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `login_account` varchar(255) NULL DEFAULT NULL COMMENT '用户账号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`llg_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '登录记录' ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
