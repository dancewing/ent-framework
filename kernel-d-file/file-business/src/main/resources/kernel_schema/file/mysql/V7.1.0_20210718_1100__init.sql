SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


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
  `create_user_name` VARCHAR(50) COMMENT '创建人账号',
  `update_user_name` VARCHAR(50) COMMENT '修改人账号',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '文件信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
