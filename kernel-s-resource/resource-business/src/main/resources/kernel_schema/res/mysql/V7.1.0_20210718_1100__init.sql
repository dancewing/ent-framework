SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `auto_report` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '自动上报：Y-是，N-否',
  `required_permission_flag` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否需要鉴权：Y-是，N-否',
  `validate_groups` text COLLATE utf8mb4_unicode_ci NULL COMMENT '需要进行参数校验的分组',
  `param_field_descriptions` text COLLATE utf8mb4_unicode_ci NULL COMMENT '接口参数的字段描述',
  `response_field_descriptions` text COLLATE utf8mb4_unicode_ci NULL COMMENT '接口返回结果的字段描述',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '资源' ROW_FORMAT = Dynamic;

ALTER TABLE `sys_resource` ADD INDEX `RESOURCE_CODE_URL`(`resource_code`, `url`) COMMENT '资源code和url的联合索引';

SET FOREIGN_KEY_CHECKS = 1;
