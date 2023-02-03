SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`sms_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '短信发送记录' ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
