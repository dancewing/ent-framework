SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `read_flag` char(1) NULL DEFAULT 'N' COMMENT '阅读状态：0-未读，1-已读',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否删除：Y-被删除，N-未删除',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '系统消息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
