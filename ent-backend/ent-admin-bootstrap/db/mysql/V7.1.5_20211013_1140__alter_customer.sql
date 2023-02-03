ALTER TABLE `toc_customer` ADD COLUMN `old_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '旧密码' AFTER `password`;
ALTER TABLE `toc_customer` ADD COLUMN `old_password_salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '旧的密码盐' AFTER `old_password`;
