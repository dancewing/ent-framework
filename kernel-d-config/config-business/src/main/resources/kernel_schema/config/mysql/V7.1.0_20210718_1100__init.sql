SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`config_id`) USING BTREE,
  UNIQUE INDEX `code_unique`(`config_code`) USING BTREE COMMENT '配置编码唯一索引'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '参数配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统配置是否已经初始化的标识', 'SYS_CONFIG_INIT_FLAG', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (6, '不需要过滤的url', 'SYS_NONE_SECURITY_URLS', '/assets/**,/login,/swagger-ui.html,/favicon.ico,/swagger-ui/**,', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (9, '账号单端登录限制', 'SYS_SINGLE_ACCOUNT_LOGIN_FLAG', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (12, '系统默认密码', 'SYS_DEFAULT_PASSWORD', '123456', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (15, 'beetl自动检查资源', 'RESOURCE_AUTO_CHECK', 'true', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (21, '系统发布版本', 'SYS_RELEASE_VERSION', '20210101', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (22, '多租户开关', 'SYS_TENANT_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (23, '验证码开关', 'SYS_CAPTCHA_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (24, '系统名称', 'SYS_SYSTEM_NAME', 'Guns快速开发平台', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (25, 'Beetl默认边界符开始', 'DELIMITER_STATEMENT_START', '@', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (26, 'Beetl边界符的结束', 'DELIMITER_STATEMENT_END', 'null', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (27, '自定义标签文件Root目录', 'RESOURCE_TAG_ROOT', 'common/tags', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (28, '自定义标签文件后缀', 'RESOURCE_TAG_SUFFIX', 'tag', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (31, '系统默认头像Base64', 'SYS_DEFAULT_AVATAR_BASE64', 'iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAIBUlEQVR4Xu2dW2wUVRjH/7Ntt3dqse2WS0VNtwaM8aEFVEqEhAQflAehErxAvAZJ0Ghotw8++sC2jRpMhBg1AU2IVNAIGogkYChRgT4YRANTEFIlu9tCLbRdej1mtm4t3bZnZrtnZg7nmxdoOvNd/v/ffnN2OrujgTalFdCU7p6aBwGgOAQEAAGguAKKt08TgABQXAHF26cJQAAoroDi7dMEIAAUV0Dx9mkCEACKK6B4+zQBCADFFVC8fZoABIDiCijePk0AAkBxBRRvnyYAAaC4Aoq3TxOAAFBcAcXbpwlAACiugOLt0wQgAMwrECj1N4JhGzQ0BUN6rfkjaU/RCgR8/q0Adlj1xvQEqCvxP61p2B9vhDGsbYjoB0Q3RvH5CtSX+DczDTuT8cY0AAGf/xiAFePKOR4M6yv55dEeohWYiTcEgGh3bIhvFwCnACwe60djrcFQW5UN/VEKjgKB0vIzYFplMt6YmgD1peU1jGn7EupgqA1G9CZyyDkFAiV+Y1HemKw3XADqSspf0DRtz5QtjmjLgx0XWpyTQN3MgeKKanjYiZl4wwUgUOo/AYbqwtJBrNzQhSVPduPsj3k4+FExbnSmQwM7uT3cVq2uDc51Xu8rb2HQls0qGsJTWzrw0OM9lr3hA+DzRwAUV66+gZq68Fi3u9+Ziz9+yjV+7gyG9WLnZFA3c8Dn7wBQtPDRXmx692pS3pgBgBmRV228hlWbro8l+aqxBGcOF4z+zEaWqWuDg51rnpNG9qonurGu1nidjm5Hd8/G0T13x/4fDOvTepw0AM0NJWg98h8ADmpAqYHK1d2oqSMAlGXBcQBqSmkSOEFfc6g7ltZxADbMLUBZltcJDZTN2X5rAHuvEgAEgBsmwEtvdeE+/6CyZjjR+J96Bj57v9Adp4DX3vsL9z8cdUIHZXNe+jUbH7893x0A0ASwn0NXTQBaBNoPAC0C7dfcVRkJAFfZYX8xBID9mrsqIwHgKjvsL4YAsF9zV2UkAFxlh/3FEAD2a+6qjASAq+ywvxhpAUgr60P+m7/DUzBgXjXjniQN6P+5GL2f+hOOy6yOIHfjRUCL3bxkabt1eB769i9IOMZbdQ25z16Clm/xbxzDGvpPF01a5/gkOeuuwFvZiZ5PKjB0Md9SzcbO0gKQ8UA38reds9ywccDA2UL07FiYcGzui23IfOz/O2KsBJ8qpveRDuS9rFsJNbbvVDHHBzM0MLSIHixD9Nsyy3mkBcDoNK2sF56cIdNN56y/HDsm+v18RL++J+G47DXtyH6qHcPtuej78l5TcXkxtcxhpM2JwvjX7MaLSQCYVXLCfrxXSxyAwfMFuNn0oKksvJimgliskwBIRlUgdsqYblwSAA7fFCr6r4EEQOIrR+o1gNVBQAAQAHQKmMAATYBxgtAaQLI1gHdxJ9igB4immTobZK+/jHQTbwNT+S7AkzeE9EX/gA2krk56FwAg57lLyFoRMmX8xJ0Gz92Fmx8smvI6QCoByHv1ArxLOlNaJwEAID6uLSurAdFDZYh+k3jVTMQpwLgKaFwNtLwNa4h+Nz92hW+6jbew5eWVdg2QjFk8MZKJyTMgmZi8OmkCjJsAVsY1T9hkzCIARlVNycfDrVwISsYsAuAOug5AAIyayZtAPOhpDSD4OoAIUGkNQGuAMQZoAlj40y1vHCbzauUZkExMXp00AeITYE07WG86htpj3zJmeus/XoqBM6NffjR+i5vF+szHNK4sajlDGGgtQs+uioSYea/o8C7tgJWYRpCRzkwM/lY4aZ0EgLH4qT2HjIrRb7awuk11+9RMYg5eKMDNxsSbSGYS08xtXrwJxNNG2kWg0VjelvOxa+wjkSxen7f9fuj8LBjXDybbjJgY9GA4bC5m2rw+II2h/4c5k8bMWnUVGQu7wYY8GP47x3SdI13eWF9T1RkPZFxm1rzD6G/xmY49fkepAUiqYzroNgUIAMWBIAAIAPqWMJUZoAmgsvsyfzJIcd9S1j5NgJRJKWcgAkBO31JWtasAeH5pOhbM9qSsOQrEV+DK9RF88cvo5ysd/7Jo+qZQvmGp3sNV3xRKAKTaXn48VwFApwC+Yanew1WnACv3BKZaCFXjuWoRSADYj6GrAKBHxtgPgJHRNY+McaZ9yhpXwPG3gWSFswo4AgA9ONJZ02PZbXpwJD061gVeT1ZCYIrH+jY3+NB6ZJZxSEcwrJdMVz7/o2H08GiX2g9M9mDvU4cKcGxvIbpCGcYH/1qCIX35jACgx8e71n/wvGGMbWyItH0+IwCMg+tLy2sY0/YlBGKoDUb0JvdKdOdXNpU3msae2R5qa+YpwD0FxAMEfP5TABaPBdRYazDUVsVLQL8Xr0CCN8DpYFhfYiazFQCOAVgxLujxYFhfaSYJ7SNWgYDPn7Q3BIBYb2yJbgsA9SX+zUzDznhHGsPr2yP6Lls6pCTTKjDRG8awtiGiHzAjm+kJYAQL+PxbAewA8EYwrH9oJgHtY48CY95oaAqG9FqzWS0BYDYo7SePAgSAPF4JqZQAECKrPEEJAHm8ElIpASBEVnmCEgDyeCWkUgJAiKzyBCUA5PFKSKUEgBBZ5QlKAMjjlZBKCQAhssoTlACQxyshlRIAQmSVJygBII9XQiolAITIKk9QAkAer4RUSgAIkVWeoASAPF4JqZQAECKrPEEJAHm8ElIpASBEVnmCEgDyeCWkUgJAiKzyBCUA5PFKSKUEgBBZ5QlKAMjjlZBK/wWoX5T5KOBLqgAAAABJRU5ErkJggg==', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (32, '用于auth模块权限校验的jwt失效时间', 'SYS_AUTH_JWT_TIMEOUT_SECONDS', '604800', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1350666483050553346, 'beetl自定义支持HTML标签', 'HTML_TAG_FLAG', 'tag:', 'Y', '', 1, 'sys_config', 'N', '2021-01-17 12:49:18', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1356246056131649538, 'websocket的ws-url', 'WEB_SOCKET_WS_URL', 'ws://localhost:8080/webSocket/{token}', 'Y', '', 1, 'sys_config', 'N', '2021-02-01 22:20:32', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367118984192843778, '邮件是否启用账号密码验证', 'SYS_EMAIL_ENABLE_AUTH', 'true', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:25:40', 1339550467939639299, '2021-03-03 22:25:43', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119064924807169, '邮箱的账号', 'SYS_EMAIL_ACCOUNT', 'xxx@126.com', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:00', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119226749444098, '邮箱的密码或者授权码', 'SYS_EMAIL_PASSWORD', 'xxx', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:38', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119286195314689, '邮箱的发送方邮箱', 'SYS_EMAIL_SEND_FROM', 'xxx@126.com', 'Y', '', 1, 'java_mail_config', 'N', '2021-03-03 22:26:52', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119399810621441, '是否开启tls', 'SYS_EMAIL_START_TLS_ENABLE', 'true', 'N', '使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS或SSL）， 而不是使用一个单独的加密通信端口。', 1, 'java_mail_config', 'N', '2021-03-03 22:27:19', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119457260003329, 'SSL安全连接', 'SYS_EMAIL_TLS_ENABLE', 'true', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:27:33', 1339550467939639299, '2021-03-03 22:28:33', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119505888763905, '指定的端口连接到在使用指定的套接字工厂', 'SYS_EMAIL_SOCKET_FACTORY_PORT', '465', 'Y', '', 1, 'java_mail_config', 'N', '2021-03-03 22:27:45', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119568455196674, 'SMTP超时时长，单位毫秒', 'SYS_EMAIL_SMTP_TIMEOUT', '10000', 'N', '', 1, 'java_mail_config', 'N', '2021-03-03 22:28:00', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1367119662306942977, 'Socket连接超时值，单位毫秒，缺省值不超时', 'SYS_EMAIL_CONNECTION_TIMEOUT', '10000', 'N', 'Socket连接超时值，单位毫秒，缺省值不超时', 1, 'java_mail_config', 'N', '2021-03-03 22:28:22', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610114, 'smtp服务器地址', 'SYS_EMAIL_SMTP_HOST', 'smtp.126.com', 'N', NULL, 1, 'java_mail_config', 'N', '2021-06-09 16:55:01', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610125, '拖拽验证码开关', 'SYS_DRAG_CAPTCHA_OPEN', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610115, 'smtp服务端口', 'SYS_EMAIL_SMTP_PORT', '465', 'Y', NULL, 1, 'java_mail_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (47, '短信发送验证码失效时间', 'SYS_SMS_VALIDATE_EXPIRED_SECONDS', '300', 'Y', NULL, 1, 'sms_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610200, 'auth认证用的jwt秘钥，用于校验登录token', 'SYS_AUTH_JWT_SECRET', 'hxim2q05g6wg6llsp24z', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610205, '解析sso传过来的token', 'SYS_AUTH_SSO_JWT_SECRET', 'aabbccdd', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610210, '解析sso加密的数据的秘钥，解密sso单点中jwt中payload的秘钥', 'SYS_AUTH_SSO_DECRYPT_DATA_SECRET', 'EDPpR/BQfEFJiXKgxN8Uno4OnNMGcIJW1F777yySCPA=', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610215, '获取是否开启sso远程会话校验', 'SYS_AUTH_SSO_SESSION_VALIDATE_SWITCH', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610220, 'sso会话校验，redis的host', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_HOST', 'localhost', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610225, 'sso会话校验，redis的port', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PORT', '6379', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610230, 'sso会话校验，redis的密码', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_PASSWORD', '', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610235, 'sso会话校验，redis的数据库序号', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_DB_INDEX', '2', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610240, 'sso会话校验，redis的缓存前缀', 'SYS_AUTH_SSO_SESSION_VALIDATE_REDIS_CACHE_PREFIX', 'CA:USER:TOKEN:', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610245, 'sso服务器地址', 'SYS_AUTH_SSO_HOST', 'http://localhost:8888', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610300, 'C端用户，注册邮件标题', 'CUSTOMER_REG_EMAIL_TITLE', '用户注册', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610305, '获取注册邮件的内容模板', 'CUSTOMER_REG_EMAIL_CONTENT', '感谢您注册Guns官方论坛，请点击此激活链接激活您的账户：<a href=\"http://localhost:8080/customer/active?verifyCode={}\">http://localhost:8080/customer/active?verifyCode={} </a>', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610310, '获取重置密码的邮件标题', 'CUSTOMER_RESET_PWD_EMAIL_TITLE', '用户校验', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610315, '获取重置密码的邮件内容', 'CUSTOMER_RESET_PWD_EMAIL_CONTENT', '您的验证码是【{}】，此验证码用于修改登录密码，请不要泄露给他人，如果不是您本人操作，请忽略此邮件。', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610320, '存放用户头像的bucket的名称', 'CUSTOMER_FILE_BUCKET', 'customer-bucket', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610325, '存放用户头像的bucket的名称的过期时间', 'CUSTOMER_FILE_BUCKET_EXPIRED_SECONDS', '600', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610330, '获取c端用户缓存的过期时间，用在加快获取速度', 'CUSTOMER_CACHE_EXPIRED_SECONDS', '3600', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610335, '是否开启旧版密码校验', 'CUSTOMER_OPEN_OLD_PASSWORD_VALIDATE', 'false', 'Y', NULL, 1, 'customer_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610400, '是否开启demo演示', 'SYS_DEMO_ENV_FLAG', 'false', 'Y', NULL, 1, 'sys_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610415, '日志记录的文件存储的位置（windows服务器）', 'SYS_LOG_FILE_SAVE_PATH_WINDOWS', 'd:/logfiles', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610420, '日志记录的文件存储的位置（linux和mac服务器）', 'SYS_LOG_FILE_SAVE_PATH_LINUX', '/tmp/logfiles', 'Y', NULL, 1, 'file_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1402549781675610500, 'AES秘钥，用在数据库数据加密', 'SYS_ENCRYPT_SECRET_KEY', 'Ux1dqQ22KxVjSYootgzMe776em8vWEGE', 'Y', NULL, 1, 'security_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config` VALUES (1481244035229200999, '帐号密码错误次数校验开关', 'ACCOUNT_ERROR_DETECTION', 'false', 'Y', NULL, 1, 'auth_config', 'N', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_code`, `config_value`, `sys_flag`, `remark`, `status_flag`, `group_code`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES (1402549781675610505, '开发模式开关', 'DEVOPS_DEV_SWITCH_STATUS', 'true', 'Y', '在开发模式下，允许devops平台访问某些系统接口', 1, 'sys_config', 'N', NULL, NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS = 1;
