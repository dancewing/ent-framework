SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1348235720908619802, 'M', '男', 'n', 'male', 'sex', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619803, 'F', '女', 'n', 'female', 'sex', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619804, '1', '启用', 'n', 'male', 'user_status', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619805, '2', '禁用', 'n', 'female', 'user_status', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1348235720908619806, '3', '冻结', 'n', 'female', 'user_status', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-01-14 14:46:13', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457799368257537, 'low', '低', 'd', NULL, 'priority_level', '低', '', -1, 1, 1.00, '-1', 'N', '2021-01-16 23:00:04', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457870780477442, 'middle', '中', 'z', NULL, 'priority_level', '中', '', -1, 1, 2.00, '-1', 'N', '2021-01-16 23:00:21', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1350457950417727489, 'high', '高', 'g', NULL, 'priority_level', '高', '', -1, 1, 3.00, '-1', 'N', '2021-01-16 23:00:40', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547360691851266, 'sys_config', '系统配置', 'xtpz', NULL, 'config_group', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-25 11:36:53', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547405457657857, 'file_config', '文件配置', 'wjpz', NULL, 'config_group', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-25 11:37:04', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547460558229506, 'auth_config', '鉴权配置', 'jqpz', NULL, 'config_group', '', '', -1, 1, 3.00, '-1', 'N', '2021-01-25 11:37:17', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353547539293704194, 'sms_config', '短信配置', 'dxpz', NULL, 'config_group', '', '', -1, 1, 4.00, '-1', 'N', '2021-01-25 11:37:36', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353998066804658177, 'chinese', '中文', 'zw', NULL, 'languages', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-26 17:27:50', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1353998106784763906, 'english', 'english', 'yw', NULL, 'languages', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-26 17:27:59', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1354040749627662337, 'role_system', '系统角色', 'xtjs', NULL, 'role_type', '', '', -1, 1, 1.00, '-1', 'N', '2021-01-26 20:17:26', 1339550467939639299, '2021-01-26 20:19:56', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1354040819219554305, 'role_c', 'C端角色', 'Cdjs', NULL, 'role_type', '', '', -1, 1, 2.00, '-1', 'N', '2021-01-26 20:17:43', 1339550467939639299, '2021-01-26 20:19:43', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1354041049981771778, 'role_b', 'B端角色', 'Bdjs', NULL, 'role_type', '', '', -1, 1, 3.00, '-1', 'N', '2021-01-26 20:18:38', 1339550467939639299, '2021-01-26 20:19:50', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1365251792270045186, 'Y', '是', 's', NULL, 'yn', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-02-26 18:46:07', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1365251827812577282, 'N', '否', 'f', NULL, 'yn', NULL, NULL, -1, 1, 2.00, '[-1],', 'N', '2021-02-26 18:46:16', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1365252384094728193, 'com.mysql.cj.jdbc.Driver', 'com.mysql.cj.jdbc.Driver', 'com.mysql.cj.jdbc.Driver', NULL, 'jdbc_type', NULL, NULL, -1, 1, 1.00, '[-1],', 'N', '2021-02-26 18:48:28', 1339550467939639299, '2021-02-26 18:53:48', 1339550467939639299, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1402549554864427010, 'java_mail_config', 'java邮件配置', 'javayjpz', NULL, 'config_group', NULL, NULL, -1, 1, 50.00, '[-1],', 'N', '2021-06-09 16:54:07', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1402549554864427020, 'customer_config', 'C端用户配置', 'cdyhpz', NULL, 'config_group', NULL, NULL, -1, 1, 60.00, '[-1],', 'N', '2021-07-07 16:54:07', 1339550467939639299, NULL, NULL, NULL, NULL);

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`dict_type_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1348235720908619811, 1, 'base', 'sex', '性别', 'xb', NULL, 1, 1.00, 'N', '2021-01-14 14:47:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1348235720908619812, 2, 'system', 'user_status', '用户状态', 'yhzt', NULL, 1, 2.00, 'N', '2021-01-14 14:47:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1350457656690618370, 1, 'notice', 'priority_level', '优先级', 'yxj', '', 1, 5.00, 'N', '2021-01-16 22:59:30', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1353547215422132226, 2, '', 'config_group', '系统配置分组', 'xtpzfz', '系统配置分组', 1, 6.00, 'N', '2021-01-25 11:36:19', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1353997993299480577, 2, '', 'languages', '语种', 'yz', 'i18n 多语言', 1, 7.00, 'N', '2021-01-26 17:27:32', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1354040335406587906, 2, '', 'role_type', '角色类型', 'jslx', '', 1, 8.00, 'N', '2021-01-26 20:15:47', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1365251549365317633, 1, NULL, 'yn', 'yn', 'yn', NULL, 1, 7.00, 'N', '2021-02-26 18:45:09', 1339550467939639299, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1365252142779641858, 1, NULL, 'jdbc_type', 'jdbc_type', 'jdbc_type', NULL, 1, 8.00, 'N', '2021-02-26 18:47:31', 1339550467939639299, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
