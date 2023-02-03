SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hr_organization
-- ----------------------------
CREATE TABLE `hr_organization`  (
  `org_id` bigint NOT NULL COMMENT '主键',
  `org_parent_id` bigint NOT NULL COMMENT '父id，一级节点父id是0',
  `org_pids` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父ids',
  `org_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织名称',
  `org_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织编码',
  `org_sort` decimal(10, 2) NOT NULL COMMENT '排序',
  `status_flag` tinyint NOT NULL COMMENT '状态：1-启用，2-禁用',
  `org_remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '删除标记：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '组织机构信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for hr_position
-- ----------------------------
CREATE TABLE `hr_position`  (
  `position_id` bigint NOT NULL COMMENT '主键',
  `position_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '职位名称',
  `position_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '职位编码',
  `position_sort` decimal(10, 2) NOT NULL COMMENT '排序',
  `status_flag` tinyint NOT NULL DEFAULT 0 COMMENT '状态：1-启用，2-禁用',
  `position_remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '删除标记：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '职位信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
CREATE TABLE `sys_app`  (
  `app_id` bigint NOT NULL COMMENT '主键id',
  `app_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `app_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `app_icon` varchar(100) NOT NULL COMMENT '应用图标',
  `active_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开',
  `status_flag` tinyint NOT NULL COMMENT '状态：1-启用，2-禁用',
  `app_sort` int NULL COMMENT '排序',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否删除：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`app_id`) USING BTREE,
  UNIQUE INDEX `APP_CODE_UNIQUE`(`app_code`) USING BTREE COMMENT 'app编码唯一'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统应用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_app
-- ----------------------------
INSERT INTO `sys_app` VALUES (1265476890672672821, '系统应用', 'system', 'ant-design:appstore-filled', 'Y', 1, 1, 'N', '2020-03-25 19:07:00', 1265476890672672808, '2021-01-08 20:51:51', 1339550467939639299);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
CREATE TABLE `sys_config`  (
  `config_id` bigint NOT NULL COMMENT '主键',
  `config_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `config_code` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性编码',
  `config_value` varchar(3500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '属性值',
  `sys_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT 'Y' COMMENT '是否是系统参数：Y-是，N-否',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `status_flag` tinyint NULL DEFAULT 1 COMMENT '状态：1-正常，2-停用',
  `group_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '常量所属分类的编码，来自于“常量的分类”字典',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`config_id`) USING BTREE,
  UNIQUE INDEX `code_unique`(`config_code`) USING BTREE COMMENT '配置编码唯一索引'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '参数配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统配置是否已经初始化的标识', 'SYS_CONFIG_INIT_FLAG', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (2, 'JWT安全码', 'SYS_JWT_SECRET', '1928374650abcdef', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (3, 'JWT过期时间', 'SYS_JWT_TIMEOUT_SECONDS', '259200', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (4, 'Linux本地文件保存路径', 'SYS_LOCAL_FILE_SAVE_PATH_LINUX', '/tmp/tempFilePath', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (5, 'Windows本地文件保存路径', 'SYS_LOCAL_FILE_SAVE_PATH_WINDOWS', 'D:\\tempFilePath', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (6, '不需要过滤的url', 'SYS_NONE_SECURITY_URLS', '/assets/**,/login,/swagger-ui.html,/favicon.ico,/swagger-ui/**,', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (8, 'session过期时间', 'SYS_SESSION_EXPIRED_SECONDS', '3600', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (9, '账号单端登录限制', 'SYS_SINGLE_ACCOUNT_LOGIN_FLAG', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (10, '携带token的header头的名称', 'SYS_AUTH_HEADER_NAME', 'Authorization', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (11, '携带token的param传参的名称', 'SYS_AUTH_PARAM_NAME', 'token', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (12, '系统默认密码', 'SYS_DEFAULT_PASSWORD', '123456', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (14, '会话保存在cookie中时，cooke的name', 'SYS_SESSION_COOKIE_NAME', 'Authorization', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (15, 'beetl自动检查资源', 'RESOURCE_AUTO_CHECK', 'true', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (21, '系统发布版本', 'SYS_RELEASE_VERSION', '20210101', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (22, '多租户开关', 'SYS_TENANT_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (23, '验证码开关', 'SYS_CAPTCHA_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (24, '系统名称', 'SYS_SYSTEM_NAME', 'Guns快速开发平台', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (25, 'Beetl默认边界符开始', 'DELIMITER_STATEMENT_START', '@', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (26, 'Beetl边界符的结束', 'DELIMITER_STATEMENT_END', 'null', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (27, '自定义标签文件Root目录', 'RESOURCE_TAG_ROOT', 'common/tags', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (28, '自定义标签文件后缀', 'RESOURCE_TAG_SUFFIX', 'tag', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (29, '获取文件生成auth url的失效时间', 'SYS_DEFAULT_FILE_TIMEOUT_SECONDS', '3600', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (30, '服务默认部署的环境地址', 'SYS_SERVER_DEPLOY_HOST', 'http://localhost:8080', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (31, '系统默认头像Base64', 'SYS_DEFAULT_AVATAR_BASE64', 'iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAIBUlEQVR4Xu2dW2wUVRjH/7Ntt3dqse2WS0VNtwaM8aEFVEqEhAQflAehErxAvAZJ0Ghotw8++sC2jRpMhBg1AU2IVNAIGogkYChRgT4YRANTEFIlu9tCLbRdej1mtm4t3bZnZrtnZg7nmxdoOvNd/v/ffnN2OrujgTalFdCU7p6aBwGgOAQEAAGguAKKt08TgABQXAHF26cJQAAoroDi7dMEIAAUV0Dx9mkCEACKK6B4+zQBCADFFVC8fZoABIDiCijePk0AAkBxBRRvnyYAAaC4Aoq3TxOAAFBcAcXbpwlAACiugOLt0wQgAMwrECj1N4JhGzQ0BUN6rfkjaU/RCgR8/q0Adlj1xvQEqCvxP61p2B9vhDGsbYjoB0Q3RvH5CtSX+DczDTuT8cY0AAGf/xiAFePKOR4M6yv55dEeohWYiTcEgGh3bIhvFwCnACwe60djrcFQW5UN/VEKjgKB0vIzYFplMt6YmgD1peU1jGn7EupgqA1G9CZyyDkFAiV+Y1HemKw3XADqSspf0DRtz5QtjmjLgx0XWpyTQN3MgeKKanjYiZl4wwUgUOo/AYbqwtJBrNzQhSVPduPsj3k4+FExbnSmQwM7uT3cVq2uDc51Xu8rb2HQls0qGsJTWzrw0OM9lr3hA+DzRwAUV66+gZq68Fi3u9+Ziz9+yjV+7gyG9WLnZFA3c8Dn7wBQtPDRXmx692pS3pgBgBmRV228hlWbro8l+aqxBGcOF4z+zEaWqWuDg51rnpNG9qonurGu1nidjm5Hd8/G0T13x/4fDOvTepw0AM0NJWg98h8ADmpAqYHK1d2oqSMAlGXBcQBqSmkSOEFfc6g7ltZxADbMLUBZltcJDZTN2X5rAHuvEgAEgBsmwEtvdeE+/6CyZjjR+J96Bj57v9Adp4DX3vsL9z8cdUIHZXNe+jUbH7893x0A0ASwn0NXTQBaBNoPAC0C7dfcVRkJAFfZYX8xBID9mrsqIwHgKjvsL4YAsF9zV2UkAFxlh/3FEAD2a+6qjASAq+ywvxhpAUgr60P+m7/DUzBgXjXjniQN6P+5GL2f+hOOy6yOIHfjRUCL3bxkabt1eB769i9IOMZbdQ25z16Clm/xbxzDGvpPF01a5/gkOeuuwFvZiZ5PKjB0Md9SzcbO0gKQ8UA38reds9ywccDA2UL07FiYcGzui23IfOz/O2KsBJ8qpveRDuS9rFsJNbbvVDHHBzM0MLSIHixD9Nsyy3mkBcDoNK2sF56cIdNN56y/HDsm+v18RL++J+G47DXtyH6qHcPtuej78l5TcXkxtcxhpM2JwvjX7MaLSQCYVXLCfrxXSxyAwfMFuNn0oKksvJimgliskwBIRlUgdsqYblwSAA7fFCr6r4EEQOIrR+o1gNVBQAAQAHQKmMAATYBxgtAaQLI1gHdxJ9igB4immTobZK+/jHQTbwNT+S7AkzeE9EX/gA2krk56FwAg57lLyFoRMmX8xJ0Gz92Fmx8smvI6QCoByHv1ArxLOlNaJwEAID6uLSurAdFDZYh+k3jVTMQpwLgKaFwNtLwNa4h+Nz92hW+6jbew5eWVdg2QjFk8MZKJyTMgmZi8OmkCjJsAVsY1T9hkzCIARlVNycfDrVwISsYsAuAOug5AAIyayZtAPOhpDSD4OoAIUGkNQGuAMQZoAlj40y1vHCbzauUZkExMXp00AeITYE07WG86htpj3zJmeus/XoqBM6NffjR+i5vF+szHNK4sajlDGGgtQs+uioSYea/o8C7tgJWYRpCRzkwM/lY4aZ0EgLH4qT2HjIrRb7awuk11+9RMYg5eKMDNxsSbSGYS08xtXrwJxNNG2kWg0VjelvOxa+wjkSxen7f9fuj8LBjXDybbjJgY9GA4bC5m2rw+II2h/4c5k8bMWnUVGQu7wYY8GP47x3SdI13eWF9T1RkPZFxm1rzD6G/xmY49fkepAUiqYzroNgUIAMWBIAAIAPqWMJUZoAmgsvsyfzJIcd9S1j5NgJRJKWcgAkBO31JWtasAeH5pOhbM9qSsOQrEV+DK9RF88cvo5ysd/7Jo+qZQvmGp3sNV3xRKAKTaXn48VwFApwC+Yanew1WnACv3BKZaCFXjuWoRSADYj6GrAKBHxtgPgJHRNY+McaZ9yhpXwPG3gWSFswo4AgA9ONJZ02PZbXpwJD061gVeT1ZCYIrH+jY3+NB6ZJZxSEcwrJdMVz7/o2H08GiX2g9M9mDvU4cKcGxvIbpCGcYH/1qCIX35jACgx8e71n/wvGGMbWyItH0+IwCMg+tLy2sY0/YlBGKoDUb0JvdKdOdXNpU3msae2R5qa+YpwD0FxAMEfP5TABaPBdRYazDUVsVLQL8Xr0CCN8DpYFhfYiazFQCOAVgxLujxYFhfaSYJ7SNWgYDPn7Q3BIBYb2yJbgsA9SX+zUzDznhHGsPr2yP6Lls6pCTTKjDRG8awtiGiHzAjm+kJYAQL+PxbAewA8EYwrH9oJgHtY48CY95oaAqG9FqzWS0BYDYo7SePAgSAPF4JqZQAECKrPEEJAHm8ElIpASBEVnmCEgDyeCWkUgJAiKzyBCUA5PFKSKUEgBBZ5QlKAMjjlZBKCQAhssoTlACQxyshlRIAQmSVJygBII9XQiolAITIKk9QAkAer4RUSgAIkVWeoASAPF4JqZQAECKrPEEJAHm8ElIpASBEVnmCEgDyeCWkUgJAiKzyBCUA5PFKSKUEgBBZ5QlKAMjjlZBK/wWoX5T5KOBLqgAAAABJRU5ErkJggg==', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (32, '用于auth模块权限校验的jwt失效时间', 'SYS_AUTH_JWT_TIMEOUT_SECONDS', '604800', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (33, 'Druid监控界面的url映射', 'SYS_DRUID_URL_MAPPINGS', '/druid/*', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (34, 'Druid控制台账号', 'SYS_DRUID_ACCOUNT', 'admin', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (35, 'Druid控制台账号密码', 'SYS_DRUID_PASSWORD', '123456', 'Y', '默认是空串，为空会让程序自动创建一个随机密码', 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (36, 'Druid控制台的监控数据是否可以重置清零', 'SYS_DRUID_RESET_ENABLE', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (37, 'druid web url统计的拦截范围', 'SYS_DRUID_WEB_STAT_FILTER_URL_PATTERN', '/*', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (38, 'druid web url统计的排除拦截表达式', 'SYS_DRUID_WEB_STAT_FILTER_EXCLUSIONS', '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (39, 'druid web url统计的session统计开关', 'SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_ENABLE', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (40, 'druid web url统计的session名称', 'SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_SESSION_NAME', 'Authorization', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (41, 'druid web url统计的session最大监控数', 'SYS_DRUID_WEB_STAT_FILTER_SESSION_STAT_MAX_COUNT', '1000', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (42, 'druid web url统计的cookie名称', 'SYS_DRUID_WEB_STAT_FILTER_PRINCIPAL_COOKIE_NAME', 'Authorization', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (43, 'druid web url统计的是否开启监控单个url调用的sql列表', 'SYS_DRUID_WEB_STAT_FILTER_PROFILE_ENABLE', 'true', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (44, '阿里云短信的accessKeyId', 'SYS_ALIYUN_SMS_ACCESS_KEY_ID', '你的accessKeyId', 'Y', NULL, 1, 'sms_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (45, '阿里云短信的accessKeySecret', 'SYS_ALIYUN_SMS_ACCESS_KEY_SECRET', '你的secret', 'Y', NULL, 1, 'sms_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (46, '阿里云短信的签名', 'SYS_ALIYUN_SMS_SIGN_NAME', '签名名称', 'Y', NULL, 1, 'sms_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1350666094452482049, '获取XSS排除过滤的url范围', 'SYS_XSS_URL_EXCLUSIONS', '/sysNotice/add,/sysNotice/edit,/databaseInfo/add,/apiResource/record', 'Y', '', 1, 'sys_config', 'N', '2021-01-17 12:47:46', 1339550467939639299, '2021-03-04 22:14:14', 1339550467939639299);
INSERT INTO `sys_config` VALUES (1350666483050553346, 'beetl自定义支持HTML标签', 'HTML_TAG_FLAG', 'tag:', 'Y', '', 1, 'sys_config', 'N', '2021-01-17 12:49:18', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1356246056131649538, 'websocket的ws-url', 'WEB_SOCKET_WS_URL', 'ws://localhost:8080/webSocket/{token}', 'Y', '', 1, 'sys_config', 'N', '2021-02-01 22:20:32', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367118984192843778, '邮件是否启用账号密码验证', 'SYS_EMAIL_ENABLE_AUTH', 'true', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:25:40', 1339550467939639299, '2021-03-03 22:25:43', 1339550467939639299);
INSERT INTO `sys_config` VALUES (1367119064924807169, '邮箱的账号', 'SYS_EMAIL_ACCOUNT', 'xxx@126.com', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:00', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119226749444098, '邮箱的密码或者授权码', 'SYS_EMAIL_PASSWORD', 'xxx', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:38', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119286195314689, '邮箱的发送方邮箱', 'SYS_EMAIL_SEND_FROM', 'xxx@126.com', 'Y', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:52', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119399810621441, '是否开启tls', 'SYS_EMAIL_START_TLS_ENABLE', 'true', 'N', '使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS或SSL）， 而不是使用一个单独的加密通信端口。', 1, 'java_mail_config', 'N', '2021-03-03 22:27:19', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119457260003329, 'SSL安全连接', 'SYS_EMAIL_TLS_ENABLE', 'true', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:27:33', 1339550467939639299, '2021-03-03 22:28:33', 1339550467939639299);
INSERT INTO `sys_config` VALUES (1367119505888763905, '指定的端口连接到在使用指定的套接字工厂', 'SYS_EMAIL_SOCKET_FACTORY_PORT', '465', 'Y', '', 1, 'java_mail_config', 'N', '2021-03-03 22:27:45', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119568455196674, 'SMTP超时时长，单位毫秒', 'SYS_EMAIL_SMTP_TIMEOUT', '10000', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:28:00', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119662306942977, 'Socket连接超时值，单位毫秒，缺省值不超时', 'SYS_EMAIL_CONNECTION_TIMEOUT', '10000', 'N', 'Socket连接超时值，单位毫秒，缺省值不超时', 1, 'java_mail_config', 'N', '2021-03-03 22:28:22', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610114, 'smtp服务器地址', 'SYS_EMAIL_SMTP_HOST', 'smtp.126.com', 'N', NULL, 1, 'java_mail_config', 'N', '2021-06-09 16:55:01', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610125, '拖拽验证码开关', 'SYS_DRAG_CAPTCHA_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610115, 'smtp服务端口', 'SYS_EMAIL_SMTP_PORT', '465', 'Y', NULL, 1, 'java_mail_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1350666094452482050, '获取XSS过滤的url范围', 'SYS_XSS_URL_INCLUDES', '/*', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (47, '短信发送验证码失效时间', 'SYS_SMS_VALIDATE_EXPIRED_SECONDS', '300', 'Y', NULL, 1, 'sms_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610200, 'auth认证用的jwt秘钥，用于校验登录token', 'SYS_AUTH_JWT_SECRET', 'hxim2q05g6wg6llsp24z', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610205, '解析sso传过来的token', 'SYS_AUTH_SSO_JWT_SECRET', 'aabbccdd', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610210, '解析sso加密的数据的秘钥，解密sso单点中jwt中payload的秘钥', 'SYS_AUTH_SSO_DECRYPT_DATA_SECRET', 'EDPpR/BQfEFJiXKgxN8Uno4OnNMGcIJW1F777yySCPA=', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610215, '获取是否开启sso远程会话校验', 'SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610220, 'sso会话校验，redis的host', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST', 'localhost', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610225, 'sso会话校验，redis的port', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT', '6379', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610230, 'sso会话校验，redis的密码', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PASSWORD', '', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610235, 'sso会话校验，redis的数据库序号', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX', '2', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610240, 'sso会话校验，redis的缓存前缀', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX', 'CA:USER:TOKEN:', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610245, 'sso服务器地址', 'SYS_AUTH_SSO_HOST', 'http://localhost:8888', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610300, 'C端用户，注册邮件标题', 'CUSTOMER_REG_EMAIL_TITLE', '用户注册', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610305, '获取注册邮件的内容模板', 'CUSTOMER_REG_EMAIL_CONTENT', '感谢您注册Guns官方论坛，请点击此激活链接激活您的账户：<a href=\"http://localhost:8080/customer/active?verifyCode={}\">http://localhost:8080/customer/active?verifyCode={} </a>', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610310, '获取重置密码的邮件标题', 'CUSTOMER_RESET_PWD_EMAIL_TITLE', '用户校验', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610315, '获取重置密码的邮件内容', 'CUSTOMER_RESET_PWD_EMAIL_CONTENT', '您的验证码是【{}】，此验证码用于修改登录密码，请不要泄露给他人，如果不是您本人操作，请忽略此邮件。', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610320, '存放用户头像的bucket的名称', 'CUSTOMER_FILE_BUCKET', 'customer-bucket', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610325, '存放用户头像的bucket的名称的过期时间', 'CUSTOMER_FILE_BUCKET_EXPIRED_SECONDS', '600', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610330, '获取c端用户缓存的过期时间，用在加快获取速度', 'CUSTOMER_CACHE_EXPIRED_SECONDS', '3600', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610335, '是否开启旧版密码校验', 'CUSTOMER_OPEN_OLD_PASSWORD_VALIDATE', 'false', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610400, '是否开启demo演示', 'SYS_DEMO_ENV_FLAG', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610405, '默认存储文件的bucket名称', 'SYS_FILE_DEFAULT_BUCKET', 'defaultBucket', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610410, '用于专门给文件鉴权用的jwt的密钥', 'SYS_DEFAULT_FILE_AUTH_JWT_SECRET', 'hxim2q05g6wg6llsp245', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610415, '日志记录的文件存储的位置（windows服务器）', 'SYS_LOG_FILE_SAVE_PATH_WINDOWS', 'd:/logfiles', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610420, '日志记录的文件存储的位置（linux和mac服务器）', 'SYS_LOG_FILE_SAVE_PATH_LINUX', '/tmp/logfiles', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610500, 'AES秘钥，用在数据库数据加密', 'SYS_ENCRYPT_SECRET_KEY', 'Ux1dqQ22KxVjSYootgzMe776em8vWEGE', 'Y', NULL, 1, 'security_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1481244035229200999, '帐号密码错误次数校验开关', 'ACCOUNT_ERROR_DETECTION', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_code`, `config_value`, `sys_flag`, `remark`, `status_flag`, `group_code`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (1402549781675610505, '开发模式开关', 'DEVOPS_DEV_SWITCH_STATUS', 'true', 'Y', '在开发模式下，允许devops平台访问某些系统接口', 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);
-- ----------------------------
-- Table structure for sys_database_info
-- ----------------------------
CREATE TABLE `sys_database_info`  (
  `db_id` bigint NOT NULL COMMENT '主键',
  `db_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库名称（英文名称）',
  `jdbc_driver` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'jdbc的驱动类型',
  `jdbc_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'jdbc的url',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库连接的账号',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库连接密码',
  `schema_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库的schema名称，每种数据库的schema意义都不同',
  `status_flag` tinyint NULL DEFAULT NULL COMMENT '数据源状态：1-正常，2-无法连接',
  `error_description` varchar(500) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '连接失败原因',
  `remarks` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注，摘要',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除，Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`db_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '多数据源信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_database_info
-- ----------------------------
INSERT INTO `sys_database_info` VALUES (1399383361507803138, 'master', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/guns?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&nullCatalogMeansCurrent=true', 'root', '123456', NULL, 1, NULL, '主数据源，项目启动数据源！', 'N', '2021-05-31 23:12:47', NULL, '2021-05-31 23:13:00', -1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
CREATE TABLE `sys_dict`  (
  `dict_id` bigint NOT NULL COMMENT '字典id',
  `dict_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典编码',
  `dict_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
  `dict_name_pinyin` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典名称首字母',
  `dict_encode` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典编码',
  `dict_type_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型的编码',
  `dict_short_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典简称',
  `dict_short_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典简称的编码',
  `dict_parent_id` bigint NOT NULL COMMENT '上级字典的id(如果没有上级字典id，则为-1)',
  `status_flag` tinyint NOT NULL COMMENT '状态：(1-启用,2-禁用),参考 StatusEnum',
  `dict_sort` decimal(10, 2) NULL DEFAULT NULL COMMENT '排序，带小数点',
  `dict_pids` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父id集合',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除，Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1348235720908619802, 'M', '男', 'n', 'male', 'sex', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619803, 'F', '女', 'n', 'female', 'sex', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619804, '1', '启用', 'n', 'male', 'user_status', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619805, '2', '禁用', 'n', 'female', 'user_status', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619806, '3', '冻结', 'n', 'female', 'user_status', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457799368257537, 'low', '低', 'd', NULL, 'priority_level', '低', '', -1, 1, 1.00, '-1', 'N', '2021-01-16 23:00:04', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457870780477442, 'middle', '中', 'z', NULL, 'priority_level', '中', '', -1, 1, 2.00, '-1', 'N', '2021-01-16 23:00:21', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457950417727489, 'high', '高', 'g', NULL, 'priority_level', '高', '', -1, 1, 3.00, '-1', 'N', '2021-01-16 23:00:40', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547360691851266, 'sys_config', '系统配置', 'xtpz', NULL, 'config_group', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-25 11:36:53', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547405457657857, 'file_config', '文件配置', 'wjpz', NULL, 'config_group', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-25 11:37:04', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547460558229506, 'auth_config', '鉴权配置', 'jqpz', NULL, 'config_group', '', '', -1, 1, 3.00, '-1', 'N', '2021-01-25 11:37:17', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547539293704194, 'sms_config', '短信配置', 'dxpz', NULL, 'config_group', '', '', -1, 1, 4.00, '-1', 'N', '2021-01-25 11:37:36', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353998066804658177, 'chinese', '中文', 'zw', NULL, 'languages', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-26 17:27:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353998106784763906, 'english', 'english', 'yw', NULL, 'languages', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-26 17:27:59', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1354040749627662337, 'role_system', '系统角色', 'xtjs', NULL, 'role_type', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-26 20:17:26', 1339550467939639299, '2021-01-26 20:19:56', 1339550467939639299);
INSERT INTO `sys_dict` VALUES (1354040819219554305, 'role_c', 'C端角色', 'Cdjs', NULL, 'role_type', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-26 20:17:43', 1339550467939639299, '2021-01-26 20:19:43', 1339550467939639299);
INSERT INTO `sys_dict` VALUES (1354041049981771778, 'role_b', 'B端角色', 'Bdjs', NULL, 'role_type', '', '', -1, 1, 3.00, '-1', 'N', '2021-01-26 20:18:38', 1339550467939639299, '2021-01-26 20:19:50', 1339550467939639299);
INSERT INTO `sys_dict` VALUES (1365251792270045186, 'Y', '是', 's', NULL, 'yn', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-02-26 18:46:07', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1365251827812577282, 'N', '否', 'f', NULL, 'yn', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-02-26 18:46:16', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1365252384094728193, 'com.mysql.cj.jdbc.Driver', 'com.mysql.cj.jdbc.Driver', 'com.mysql.cj.jdbc.Driver', NULL, 'jdbc_type', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-02-26 18:48:28', 1339550467939639299, '2021-02-26 18:53:48', 1339550467939639299);
INSERT INTO `sys_dict` VALUES (1402549554864427010, 'java_mail_config', 'java邮件配置', 'javayjpz', NULL, 'config_group', NULL, NULL, -1, 1, 50.00, '[-1],', 'N', '2021-06-09 16:54:07', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1402549554864427020, 'customer_config', 'C端用户配置', 'cdyhpz', NULL, 'config_group', NULL, NULL, -1, 1, 60.00, '[-1],', 'N', '2021-07-07 16:54:07', 1339550467939639299, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
CREATE TABLE `sys_dict_type`  (
  `dict_type_id` bigint NOT NULL COMMENT '字典类型id',
  `dict_type_class` int NULL DEFAULT NULL COMMENT '字典类型： 1-业务类型，2-系统类型，参考 DictTypeClassEnum',
  `dict_type_bus_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型业务编码',
  `dict_type_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型编码',
  `dict_type_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型名称',
  `dict_type_name_pinyin` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型名称首字母拼音',
  `dict_type_desc` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典类型描述',
  `status_flag` tinyint NULL DEFAULT NULL COMMENT '字典类型的状态：1-启用，2-禁用，参考 StatusEnum',
  `dict_type_sort` decimal(10, 2) NULL DEFAULT NULL COMMENT '排序，带小数点',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`dict_type_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1348235720908619811, 1, 'base', 'sex', '性别', 'xb', NULL, 1, 1.00, 'N', '2021-01-14 14:47:32', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1348235720908619812, 2, 'system', 'user_status', '用户状态', 'yhzt', NULL, 1, 2.00, 'N', '2021-01-14 14:47:32', NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1350457656690618370, 1, 'notice', 'priority_level', '优先级', 'yxj', '', 1, 5.00, 'N', '2021-01-16 22:59:30', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1353547215422132226, 2, '', 'config_group', '系统配置分组', 'xtpzfz', '系统配置分组', 1, 6.00, 'N', '2021-01-25 11:36:19', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1353997993299480577, 2, '', 'languages', '语种', 'yz', 'i18n 多语言', 1, 7.00, 'N', '2021-01-26 17:27:32', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1354040335406587906, 2, '', 'role_type', '角色类型', 'jslx', '', 1, 8.00, 'N', '2021-01-26 20:15:47', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1365251549365317633, 1, NULL, 'yn', 'yn', 'yn', NULL, 1, 7.00, 'N', '2021-02-26 18:45:09', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1365252142779641858, 1, NULL, 'jdbc_type', 'jdbc_type', 'jdbc_type', NULL, 1, 8.00, 'N', '2021-02-26 18:47:31', 1339550467939639299, NULL, NULL);

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
CREATE TABLE `sys_file_info`  (
  `file_id` bigint NOT NULL COMMENT '文件主键id',
  `file_code` bigint NOT NULL COMMENT '文件编码，本号升级的依据，解决一个文件多个版本问题，多次上传文件编码不变',
  `file_version` int NOT NULL DEFAULT 1 COMMENT '文件版本，从1开始',
  `file_status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '当前状态：0-历史版,1-最新版',
  `file_location` tinyint NOT NULL COMMENT '文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地',
  `file_bucket` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件仓库（文件夹）',
  `file_origin_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称（上传时候的文件全名）',
  `file_suffix` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件后缀，例如.txt',
  `file_size_kb` bigint NULL DEFAULT NULL COMMENT '文件大小kb为单位',
  `file_size_info` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件大小信息，计算后的',
  `file_object_name` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '存储到bucket中的名称，主键id+.后缀',
  `file_path` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '存储路径',
  `secret_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否为机密文件，Y-是机密，N-不是机密',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '文件信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------
INSERT INTO `sys_file_info` VALUES (10000, -1, 1, '1', 4, 'defaultBucket', 'defaultAvatar.png', '.png', 12, '11.56 kB', '10000.png', NULL, 'Y', 'N', '2020-12-29 20:07:14', NULL, NULL, NULL);

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
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`llg_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL COMMENT '主键',
  `menu_parent_id` bigint NOT NULL COMMENT '父id，顶级节点的父id是-1',
  `menu_pids` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父id集合，中括号包住，逗号分隔',
  `menu_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单的名称',
  `menu_type` bigint COMMENT '菜单类型',
  `menu_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单的编码',
  `app_id` bigint COMMENT '所属应用',
  `menu_sort` decimal(10, 2) NOT NULL DEFAULT 100.00 COMMENT '排序',
  `status_flag` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `router` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由地址，浏览器显示的URL，例如/menu',
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci NULL COMMENT '图标',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '前端组件名',
  `link_open_type` tinyint NULL DEFAULT 0 COMMENT '外部链接打开方式：1-内置打开外链，2-新页面外链',
  `link_url` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '外部链接地址',
  `active_url` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用于非菜单显示页面的重定向url设置',
  `visible` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT 'Y' COMMENT '是否可见(分离版用)：Y-是，N-否',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

CREATE TABLE `sys_menu_resource` (
  `menu_resource_id` bigint NOT NULL COMMENT '主键',
  `menu_id` bigint NOT NULL COMMENT '菜单或按钮id',
  `resource_code` varchar(300) NOT NULL DEFAULT 'N' COMMENT '资源的编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`menu_resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='菜单资源绑定';

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
CREATE TABLE `sys_message`  (
  `message_id` bigint NOT NULL COMMENT '主键',
  `receive_user_id` bigint NULL DEFAULT NULL COMMENT '接收用户id',
  `send_user_id` bigint NULL DEFAULT NULL COMMENT '发送用户id',
  `message_title` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '消息标题',
  `message_content` varchar(512) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '消息内容',
  `message_type` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '消息类型',
  `priority_level` varchar(50) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优先级',
  `message_send_time` datetime NULL DEFAULT NULL COMMENT '消息发送时间',
  `business_id` bigint NULL DEFAULT NULL COMMENT '业务id',
  `business_type` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业务类型(根据业务id和业务类型可以确定业务数据)',
  `read_flag` tinyint NULL DEFAULT 0 COMMENT '阅读状态：0-未读，1-已读',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
CREATE TABLE `sys_notice`  (
  `notice_id` bigint NOT NULL COMMENT '主键',
  `notice_title` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知标题',
  `notice_summary` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知摘要',
  `notice_content` varchar(512) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知内容',
  `priority_level` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '优先级',
  `notice_begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `notice_end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `notice_scope` varchar(3000) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知范围（用户id字符串）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '通知管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
CREATE TABLE `sys_resource`  (
  `resource_id` bigint NOT NULL COMMENT '资源id',
  `app_code` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '应用编码',
  `resource_code` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源编码',
  `resource_name` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源名称',
  `project_code` varchar(64) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目编码，一般为spring.application.name',
  `class_name` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '方法名称',
  `modular_code` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源模块编码，一般为控制器类名排除Controller',
  `modular_name` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源模块名称，一般为控制器名称',
  `ip_address` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源初始化的服务器ip地址',
  `view_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否是视图类型：Y-是，N-否\r\n如果是视图类型，url需要以 \'/view\' 开头，\r\n视图类型的接口会渲染出html界面，而不是json数据，\r\n视图层一般会在前后端不分离项目出现',
  `url` varchar(300) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源url',
  `http_method` varchar(10) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'http请求方法',
  `required_login_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否需要登录：Y-是，N-否',
  `required_permission_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否需要鉴权：Y-是，N-否',
  `validate_groups` text COLLATE utf8mb4_unicode_ci NULL COMMENT '需要进行参数校验的分组',
  `param_field_descriptions` text COLLATE utf8mb4_unicode_ci NULL COMMENT '接口参数的字段描述',
  `response_field_descriptions` text COLLATE utf8mb4_unicode_ci NULL COMMENT '接口返回结果的字段描述',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '资源' ROW_FORMAT = Dynamic;

ALTER TABLE `sys_resource` ADD INDEX `RESOURCE_CODE_URL`(`resource_code`, `url`) COMMENT '资源code和url的联合索引';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL COMMENT '主键id',
  `role_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色编码',
  `role_sort` decimal(10, 2) NOT NULL COMMENT '序号',
  `data_scope_type` tinyint NOT NULL DEFAULT 1 COMMENT '数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据',
  `status_flag` tinyint NOT NULL DEFAULT 0 COMMENT '状态：1-启用，2-禁用',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `role_system_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否是系统角色:Y-是,N-否',
  `role_type_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典:角色类型',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1339550467939639303, '超级管理员', 'superAdmin', 1.00, 50, 1, '备注', 'N', '2020-12-17 20:41:25', -1, '2020-12-17 20:41:30', -1, 'Y', 'role_system');
INSERT INTO `sys_role` VALUES (1339550467939639304, '普通人员', 'normal', 2.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system');
INSERT INTO `sys_role` VALUES (1339550467939639305, 'C端人员', 'c', 3.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system');
INSERT INTO `sys_role` VALUES (1339550467939639306, 'B端人员', 'b', 4.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system');

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
CREATE TABLE `sys_role_data_scope`  (
  `role_data_scope_id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `organization_id` bigint NOT NULL COMMENT '机构id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`role_data_scope_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '角色数据范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '角色菜单关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
CREATE TABLE `sys_role_resource`  (
  `role_resource_id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `resource_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源编码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`role_resource_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '角色资源关联' ROW_FORMAT = Dynamic;

ALTER TABLE `sys_role_resource` ADD INDEX `ROLE_RESOURCE_ID_RESOURCE_CODE`(`role_id`, `resource_code`) COMMENT '资源role_id和resource_code的联合索引';

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
CREATE TABLE `sys_sms`  (
  `sms_id` bigint NOT NULL COMMENT '主键',
  `phone_number` varchar(11) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `validate_code` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信验证码',
  `template_code` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信模板编号',
  `biz_id` varchar(20) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业务id',
  `status_flag` tinyint NULL DEFAULT NULL COMMENT '发送状态：1-未发送，2-发送成功，3-发送失败，4-失效',
  `source` int NULL DEFAULT NULL COMMENT '来源：1-app，2-pc，3-其他',
  `invalid_time` datetime NULL DEFAULT NULL COMMENT '短信失效截止时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`sms_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '短信发送记录' ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`timer_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_translation
-- ----------------------------
CREATE TABLE `sys_translation`  (
  `tran_id` bigint NOT NULL COMMENT '主键id',
  `tran_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `tran_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '多语言条例名称',
  `tran_language_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '语种字典',
  `tran_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '翻译的值',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`tran_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '多语言' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_translation
-- ----------------------------
INSERT INTO `sys_translation` VALUES (1355348835513200642, 'MENU_BLACKBOARD', '菜单_主控面板', 'chinese', '主控面板', '2021-01-30 10:55:18', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355348835513200643, 'MENU_BLACKBOARD', '菜单_主控面板', 'english', 'dashboard', '2021-01-30 10:55:18', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355348956036526081, 'MENU_BOARD_PLATFORM', '菜单_工作台', 'chinese', '工作台', '2021-01-30 10:55:47', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355348956036526082, 'MENU_BOARD_PLATFORM', '菜单_工作台', 'english', 'platform', '2021-01-30 10:55:47', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355352667639050242, 'MENU_BOARD_ANALYSE', '菜单_分析页', 'chinese', '分析页', '2021-01-30 11:10:32', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355352667639050243, 'MENU_BOARD_ANALYSE', '菜单_分析页', 'english', 'analyse', '2021-01-30 11:10:32', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355352795519184897, 'MENU_ORG', '菜单_组织机构', 'chinese', '组织机构', '2021-01-30 11:11:02', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355352795519184898, 'MENU_ORG', '菜单_组织机构', 'english', 'organiztion', '2021-01-30 11:11:02', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354480979275777, 'MENU_ORG_USER', '菜单_用户管理', 'chinese', '用户管理', '2021-01-30 11:17:44', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354480979275778, 'MENU_ORG_USER', '菜单_用户管理', 'english', 'users', '2021-01-30 11:17:44', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354599871016962, 'MENU_ORG_MAIN', '菜单_机构管理', 'chinese', '机构管理', '2021-01-30 11:18:13', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354599871016963, 'MENU_ORG_MAIN', '菜单_机构管理', 'english', 'organization', '2021-01-30 11:18:13', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354696272900097, 'MENU_ORG_POSITION', '菜单_职位管理', 'chinese', '职位管理', '2021-01-30 11:18:36', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354696272900098, 'MENU_ORG_POSITION', '菜单_职位管理', 'english', 'position', '2021-01-30 11:18:36', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354785196339201, 'MENU_AUTH', '菜单_权限管理', 'chinese', '权限管理', '2021-01-30 11:18:57', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354785196339202, 'MENU_AUTH', '菜单_权限管理', 'english', 'authority', '2021-01-30 11:18:57', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354906436890626, 'MENU_AUTH_APP', '菜单_应用管理', 'chinese', '应用管理', '2021-01-30 11:19:26', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355354906436890627, 'MENU_AUTH_APP', '菜单_应用管理', 'english', 'apps', '2021-01-30 11:19:26', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355090189348866, 'MENU_AUTH_MENU', '菜单_菜单管理', 'chinese', '菜单管理', '2021-01-30 11:20:09', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355090189348867, 'MENU_AUTH_MENU', '菜单_菜单管理', 'english', 'menus', '2021-01-30 11:20:09', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355153632391170, 'MENU_AUTH_ROLE', '菜单_角色管理', 'chinese', '角色管理', '2021-01-30 11:20:25', 1339550467939639299, '2021-01-30 11:21:33', 1339550467939639299);
INSERT INTO `sys_translation` VALUES (1355355153632391171, 'MENU_AUTH_ROLE', '菜单_角色管理', 'english', 'roles', '2021-01-30 11:20:25', 1339550467939639299, '2021-01-30 11:21:33', 1339550467939639299);
INSERT INTO `sys_translation` VALUES (1355355326739705858, 'MENU_AUTH_RESOURCE', '菜单_资源管理', 'chinese', '资源管理', '2021-01-30 11:21:06', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355326739705859, 'MENU_AUTH_RESOURCE', '菜单_资源管理', 'english', 'resource', '2021-01-30 11:21:06', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355406259515393, 'MENU_BASE', '菜单_基础数据', 'chinese', '基础数据', '2021-01-30 11:21:25', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355406259515394, 'MENU_BASE', '菜单_基础数据', 'english', 'base data', '2021-01-30 11:21:25', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355520512356354, 'MENU_BASE_SYSCONFIG', '菜单_系统配置', 'chinese', '系统配置', '2021-01-30 11:21:52', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355520512356355, 'MENU_BASE_SYSCONFIG', '菜单_系统配置', 'english', 'config', '2021-01-30 11:21:52', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355620575866882, 'MENU_BASE_DICT', '菜单_字典管理', 'chinese', '字典管理', '2021-01-30 11:22:16', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355620575866883, 'MENU_BASE_DICT', '菜单_字典管理', 'english', 'dict', '2021-01-30 11:22:16', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355711239942145, 'MENU_BASE_APIS', '菜单_接口文档', 'chinese', '接口文档', '2021-01-30 11:22:38', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355711239942146, 'MENU_BASE_APIS', '菜单_接口文档', 'english', 'apis', '2021-01-30 11:22:38', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355878345207809, 'MENU_SYS', '菜单_系统功能', 'chinese', '系统功能', '2021-01-30 11:23:17', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355355878345207810, 'MENU_SYS', '菜单_系统功能', 'english', 'system', '2021-01-30 11:23:17', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356005545865218, 'MENU_SYS_FILE', '菜单_文件管理', 'chinese', '文件管理', '2021-01-30 11:23:48', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356005545865219, 'MENU_SYS_FILE', '菜单_文件管理', 'english', 'file', '2021-01-30 11:23:48', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356091415851010, 'MENU_OPERATE_LOG', '菜单_操作日志', 'chinese', '操作日志', '2021-01-30 11:24:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356091415851011, 'MENU_OPERATE_LOG', '菜单_操作日志', 'english', 'operate log', '2021-01-30 11:24:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356171220873218, 'MENU_LOGIN_LOG', '菜单_登录日志', 'chinese', '登录日志', '2021-01-30 11:24:27', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356171220873219, 'MENU_LOGIN_LOG', '菜单_登录日志', 'english', 'login log', '2021-01-30 11:24:27', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356268293844993, 'MENU_SYS_ONLINE', '菜单_在线用户', 'chinese', '在线用户', '2021-01-30 11:24:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356268293844994, 'MENU_SYS_ONLINE', '菜单_在线用户', 'english', 'login user', '2021-01-30 11:24:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356371410808833, 'MENU_SYS_TIMER', '菜单_定时任务', 'chinese', '定时任务', '2021-01-30 11:25:15', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356371410808834, 'MENU_SYS_TIMER', '菜单_定时任务', 'english', 'timers', '2021-01-30 11:25:15', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356481783918593, 'MENU_DATASOURCES', '菜单_多数据源', 'chinese', '多数据源', '2021-01-30 11:25:41', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356481783918594, 'MENU_DATASOURCES', '菜单_多数据源', 'english', 'datasources', '2021-01-30 11:25:41', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356554374737921, 'MENU_LANGUAGES', '菜单_多语言配置', 'chinese', '多语言配置', '2021-01-30 11:25:59', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356554374737922, 'MENU_LANGUAGES', '菜单_多语言配置', 'english', 'languages', '2021-01-30 11:25:59', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356772600180738, 'MENU_NOTICE', '菜单_通知管理', 'chinese', '通知管理', '2021-01-30 11:26:51', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356772600180739, 'MENU_NOTICE', '菜单_通知管理', 'english', 'notice', '2021-01-30 11:26:51', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356847015522305, 'MENU_NOTICE_UPDATE', '菜单_通知发布', 'chinese', '通知发布', '2021-01-30 11:27:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356847015522306, 'MENU_NOTICE_UPDATE', '菜单_通知发布', 'english', 'notice deploy', '2021-01-30 11:27:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356922198421505, 'MENU_NOTICE_FIND', '菜单_我的消息', 'chinese', '我的消息', '2021-01-30 11:27:26', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356922198421506, 'MENU_NOTICE_FIND', '菜单_我的消息', 'english', 'my notice', '2021-01-30 11:27:26', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356997855277058, 'MENU_MONITOR', '菜单_监控管理', 'chinese', '监控管理', '2021-01-30 11:27:44', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355356997855277059, 'MENU_MONITOR', '菜单_监控管理', 'english', 'monitor', '2021-01-30 11:27:44', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355357305746550786, 'MENU_MONITOR_DRUID', '菜单_SQL监控', 'chinese', 'SQL监控', '2021-01-30 11:28:58', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355357305746550787, 'MENU_MONITOR_DRUID', '菜单_SQL监控', 'english', 'druid monitor', '2021-01-30 11:28:58', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355357401196326913, 'MENU_MONITOR_SERVER', '菜单_服务器信息', 'chinese', '服务器信息', '2021-01-30 11:29:20', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355357401196326914, 'MENU_MONITOR_SERVER', '菜单_服务器信息', 'english', 'server info', '2021-01-30 11:29:20', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363229903204353, 'MENU_CHANGE_PASSWORD', '菜单_修改密码', 'chinese', '修改密码', '2021-01-30 11:52:30', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363229903204354, 'MENU_CHANGE_PASSWORD', '菜单_修改密码', 'english', 'change password', '2021-01-30 11:52:30', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363314489733122, 'MENU_PERSONAL', '菜单_个人中心', 'chinese', '个人中心', '2021-01-30 11:52:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363314489733123, 'MENU_PERSONAL', '菜单_个人中心', 'english', 'personal', '2021-01-30 11:52:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363436573339649, 'MENU_LOGOUT', '菜单_退出', 'chinese', '退出', '2021-01-30 11:53:19', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355363436573339650, 'MENU_LOGOUT', '菜单_退出', 'english', 'logout', '2021-01-30 11:53:19', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388566909333506, 'FIELD_BASIC_INFO', '个人中心_选项卡_基本信息', 'chinese', '基本信息', '2021-01-30 13:33:11', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388566909333507, 'FIELD_BASIC_INFO', '个人中心_选项卡_基本信息', 'english', 'basic info', '2021-01-30 13:33:11', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388672819703810, 'FIELD_OTHER', '个人中心_选项卡_其他', 'chinese', '其他', '2021-01-30 13:33:36', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388672819703811, 'FIELD_OTHER', '个人中心_选项卡_其他', 'english', 'others', '2021-01-30 13:33:36', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388784606294017, 'FIELD_ACCOUNT', '个人中心_字段_账号', 'chinese', '账号', '2021-01-30 13:34:03', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388784606294018, 'FIELD_ACCOUNT', '个人中心_字段_账号', 'english', 'account', '2021-01-30 13:34:03', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388873777197057, 'FIELD_SEX', '个人中心_字段_性别', 'chinese', '性别', '2021-01-30 13:34:24', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388873777197058, 'FIELD_SEX', '个人中心_字段_性别', 'english', 'sex', '2021-01-30 13:34:24', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388954651766786, 'FIELD_EMAIL', '个人中心_字段_邮箱', 'chinese', '邮箱', '2021-01-30 13:34:43', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355388954651766787, 'FIELD_EMAIL', '个人中心_字段_邮箱', 'english', 'email', '2021-01-30 13:34:43', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389060402753538, 'FIELD_NAME', '个人中心_字段_姓名', 'chinese', '姓名', '2021-01-30 13:35:09', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389060402753539, 'FIELD_NAME', '个人中心_字段_姓名', 'english', 'name', '2021-01-30 13:35:09', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389141315072001, 'FIELD_BIRTHDAY', '个人中心_字段_生日', 'chinese', '生日', '2021-01-30 13:35:28', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389141315072002, 'FIELD_BIRTHDAY', '个人中心_字段_生日', 'english', 'birthday', '2021-01-30 13:35:28', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389290334498817, 'FIELD_PHONE', '个人中心_字段_电话', 'chinese', '电话', '2021-01-30 13:36:03', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389290334498818, 'FIELD_PHONE', '个人中心_字段_电话', 'english', 'phone', '2021-01-30 13:36:03', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389380272959489, 'BTN_UPDATE_INFO', '个人中心_按钮_更新基本信息', 'chinese', '更新基本信息', '2021-01-30 13:36:25', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355389380272959490, 'BTN_UPDATE_INFO', '个人中心_按钮_更新基本信息', 'english', 'update', '2021-01-30 13:36:25', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355391961791590402, 'TITLE_CHANGE_PASSWORD', '修改密码_标题_修改密码', 'chinese', '修改密码', '2021-01-30 13:46:40', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355391961791590403, 'TITLE_CHANGE_PASSWORD', '修改密码_标题_修改密码', 'english', 'change password', '2021-01-30 13:46:40', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392062836568065, 'FIELD_OLD_PASSWORD', '修改密码_字段_旧密码', 'chinese', '旧密码', '2021-01-30 13:47:04', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392062836568066, 'FIELD_OLD_PASSWORD', '修改密码_字段_旧密码', 'english', 'old password', '2021-01-30 13:47:04', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392146118668290, 'FIELD_NEW_PASSWORD', '修改密码_字段_新密码', 'chinese', '新密码', '2021-01-30 13:47:24', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392146118668291, 'FIELD_NEW_PASSWORD', '修改密码_字段_新密码', 'english', 'new password', '2021-01-30 13:47:24', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392231485337601, 'FIELD_REPEAT_PASSWORD', '修改密码_字段_重复密码', 'chinese', '重复密码', '2021-01-30 13:47:45', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392231485337602, 'FIELD_REPEAT_PASSWORD', '修改密码_字段_重复密码', 'english', 'repeat password', '2021-01-30 13:47:45', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392328214376450, 'BTN_SAVE', '按钮_保存', 'chinese', '保存', '2021-01-30 13:48:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392328214376451, 'BTN_SAVE', '按钮_保存', 'english', 'save', '2021-01-30 13:48:08', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392385093332993, 'BTN_CANCEL', '按钮_取消', 'chinese', '取消', '2021-01-30 13:48:21', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_translation` VALUES (1355392385093332994, 'BTN_CANCEL', '按钮_取消', 'english', 'cancel', '2021-01-30 13:48:21', 1339550467939639299, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL COMMENT '主键',
  `real_name` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(50) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码，加密方式为BCrypt',
  `avatar` bigint NULL DEFAULT NULL COMMENT '头像，存的为文件id',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别：M-男，F-女',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `tel` varchar(50) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `super_admin_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否是超级管理员：Y-是，N-否',
  `status_flag` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-冻结',
  `login_count` int NULL DEFAULT 1 COMMENT '登录次数',
  `last_login_ip` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1339550467939639299, '管理员', '超管', 'admin', '$2a$10$N/mL91CVAlcuZmW8/m4Fb..BSsimGqhfwpHtIGH3h8NYI41rXhhIq', 10000, '2020-12-01', 'M', 'sn93@qq.com', '18200000000', '123456', 'Y', 1, 1, '127.0.0.1', '2021-05-31 23:12:59', 'N', '2020-12-17 20:40:31', -1, '2021-05-31 23:12:59', -1);

-- ----------------------------
-- Table structure for sys_user_data_scope
-- ----------------------------
CREATE TABLE `sys_user_data_scope`  (
  `user_data_scope_id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `org_id` bigint NOT NULL COMMENT '机构id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_data_scope_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '用户数据范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_org
-- ----------------------------
CREATE TABLE `sys_user_org`  (
  `user_org_id` bigint NOT NULL COMMENT '企业员工主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `org_id` bigint NOT NULL COMMENT '所属机构id',
  `position_id` bigint NULL DEFAULT NULL COMMENT '职位id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '添加人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`user_org_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '用户组织机构关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_org
-- ----------------------------
INSERT INTO `sys_user_org` VALUES (1339554696976781405, 1339550467939639299, 1339554696976781407, 1339554696976781332, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE `sys_user_role`  (
  `user_role_id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '用户角色关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1339554696976781379, 1339550467939639299, 1339550467939639303, '2020-12-17 20:57:31', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for toc_customer
-- ----------------------------
CREATE TABLE `toc_customer`  (
  `customer_id` bigint NOT NULL COMMENT '主键id',
  `account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码，BCrypt',
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称（显示名称）',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(30) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `verify_code` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱或手机验证码',
  `verified_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT 'N' COMMENT '是否已经邮箱或手机验证通过：Y-通过，N-未通过',
  `avatar` bigint NULL DEFAULT NULL COMMENT '用户头像（文件表id）',
  `avatar_object_name` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像的文件全名',
  `score` int NULL DEFAULT NULL COMMENT '用户积分',
  `status_flag` tinyint NULL DEFAULT NULL COMMENT '用户状态：1-启用，2-禁用',
  `secret_key` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户秘钥，用在调用会员校验等',
  `member_expire_time` datetime NULL DEFAULT NULL COMMENT '会员截止日期，到期时间',
  `last_login_ip` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上次登录ip',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = 'C端用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
