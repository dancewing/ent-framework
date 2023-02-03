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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '职位信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
CREATE TABLE `sys_app`  (
  `app_id` bigint NOT NULL COMMENT '主键id',
  `app_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `app_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `entry_path` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '入口路径',
  `app_icon` varchar(100) NOT NULL COMMENT '应用图标',
  `active_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否默认激活：Y-是，N-否，激活的应用下的菜单会在首页默认展开',
  `status_flag` tinyint NOT NULL COMMENT '状态：1-启用，2-禁用',
  `app_sort` int NULL COMMENT '排序',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否删除：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`app_id`) USING BTREE,
  UNIQUE INDEX `APP_CODE_UNIQUE`(`app_code`) USING BTREE COMMENT 'app编码唯一'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统应用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_app
-- ----------------------------
INSERT INTO `sys_app` VALUES (1265476890672672821, '系统应用', 'system', '/index.html','ant-design:appstore-filled', 'Y', 1, 1, 'N', '2020-03-25 19:07:00', 1265476890672672808, '2021-01-08 20:51:51', 1339550467939639299, NULL, NULL);


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
  `link_open_type` tinyint NULL DEFAULT 0 COMMENT '外部链接打开方式：1-内置打开外链，2-新页面外链',
  `link_url` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '外部链接地址',
  `active_url` varchar(255) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用于非菜单显示页面的重定向url设置',
  `visible` char(1) COLLATE utf8mb4_unicode_ci NULL DEFAULT 'Y' COMMENT '是否可见(分离版用)：Y-是，N-否',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`menu_resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='菜单资源绑定';

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '通知管理' ROW_FORMAT = Dynamic;

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1339550467939639303, '超级管理员', 'superAdmin', 1.00, 50, 1, '备注', 'N', '2020-12-17 20:41:25', -1, '2020-12-17 20:41:30', -1, 'Y', 'role_system', NULL, NULL);
INSERT INTO `sys_role` VALUES (1339550467939639304, '普通人员', 'normal', 2.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system', NULL, NULL);
INSERT INTO `sys_role` VALUES (1339550467939639305, 'C端人员', 'c', 3.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system', NULL, NULL);
INSERT INTO `sys_role` VALUES (1339550467939639306, 'B端人员', 'b', 4.00, 10, 1, NULL, 'N', NULL, NULL, NULL, NULL, 'Y', 'role_system', NULL, NULL);

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`role_resource_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '角色资源关联' ROW_FORMAT = Dynamic;

ALTER TABLE `sys_role_resource` ADD INDEX `ROLE_RESOURCE_ID_RESOURCE_CODE`(`role_id`, `resource_code`) COMMENT '资源role_id和resource_code的联合索引';


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
  `org_id` bigint DEFAULT NULL COMMENT '所属机构id',
  `position_id` bigint DEFAULT NULL COMMENT '职位id',
  `status_flag` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-冻结',
  `login_count` int NULL DEFAULT 1 COMMENT '登录次数',
  `last_login_ip` varchar(100) COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1339550467939639299, '管理员', '超管', 'admin', '$2a$10$N/mL91CVAlcuZmW8/m4Fb..BSsimGqhfwpHtIGH3h8NYI41rXhhIq', 10000, '2020-12-01', 'M', 'sn93@qq.com', '18200000000', '123456', 'Y', null, null, 1, 1, '127.0.0.1', '2021-05-31 23:12:59', 'N', '2020-12-17 20:40:31', -1, '2021-05-31 23:12:59', -1, NULL, NULL);

/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`user_data_scope_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '用户数据范围' ROW_FORMAT = Dynamic;

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '用户角色关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1339554696976781379, 1339550467939639299, 1339550467939639303, '2020-12-17 20:57:31', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
