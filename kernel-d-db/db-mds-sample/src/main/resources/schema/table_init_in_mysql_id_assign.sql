/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

drop table if exists `exam_class_grade`;

drop table if exists `exam_teacher`;

drop table if exists `exam_student`;

drop table if exists `exam_history_score`;

create table if not exists `exam_class_grade`
(
    `id` BIGINT not null COMMENT 'ID',
    `name` VARCHAR(100) not null COMMENT '名称',
    `description` VARCHAR(255) null default null COMMENT '描述',
    `grade_type` VARCHAR(20) not null COMMENT '类型[ADVANCE(0):高级,COMMON(1):普通]',
    `start_time` DATETIME COMMENT '开学时间',
    `regulator_id` BIGINT not null COMMENT '班主任',
    `create_time` DATETIME COMMENT '创建时间' ,
    `create_user` bigint COMMENT '创建人' ,
    `update_time` DATETIME COMMENT '更新时间' ,
    `update_user` bigint COMMENT '更新人' ,
    `create_user_name` VARCHAR(50) COMMENT '创建人账号',
    `update_user_name` VARCHAR(50) COMMENT '更新人账号',
    primary key (`id`),
    index `idx_exam_class_grade_name` (`name`)
    ) ENGINE = InnoDB CHARSET = utf8 COMMENT '班级';

create table if not exists `exam_teacher`
(
    `id` BIGINT not null,
    `name` VARCHAR(100) not null COMMENT '姓名',
    `card_num` VARCHAR(50) not null unique COMMENT '卡号',
    `gender` VARCHAR(20) not null COMMENT '性别[MALE(0):男,FEMALE(1):女]',
    `birthday` DATE null default null COMMENT '生日',
    `work_seniority` INT not null COMMENT '级别',
    `status_flag` tinyint not null COMMENT '状态',
    `tech_courses` VARCHAR(255) null default null COMMENT '教授课程',
    `properties` LONGBLOB COMMENT '属性',
    `version_` BIGINT COMMENT '版本',
    `create_time` DATETIME COMMENT '创建时间' ,
    `create_user` bigint COMMENT '创建人' ,
    `update_time` DATETIME COMMENT '更新时间' ,
    `update_user` bigint COMMENT '更新人' ,
    `create_user_name` VARCHAR(50) COMMENT '创建人姓名',
    `update_user_name` VARCHAR(50) COMMENT '更新人姓名',
    primary key (`id`),
    index `idx_exam_teacher_name` (`name`)
    ) ENGINE = InnoDB CHARSET = utf8 COMMENT '教师';

create table if not exists `exam_student`
(
    `id` BIGINT not null,
    `grade_id` BIGINT not null COMMENT '班级ID',
    `name` VARCHAR(100) not null COMMENT '姓名',
    `card_num` VARCHAR(50) not null unique COMMENT '卡号',
    `gender` VARCHAR(20) not null COMMENT '性别[MALE(0):男,FEMALE(1):女]',
    `birthday` DATE null default null COMMENT '生日',
    `take_courses` VARCHAR(255) not null COMMENT '学习课程',
    `from_foreign` BOOLEAN not null COMMENT '外国学生',
    `hometown` VARCHAR(100) null default null COMMENT '籍贯',
    `hobbies` TEXT null default null COMMENT '爱好',
    `del_flag` char(1) COMMENT '删除标记',
    `version_` BIGINT COMMENT '版本',
    `create_time` DATETIME COMMENT '创建时间' ,
    `create_user` bigint COMMENT '创建人' ,
    `update_time` DATETIME COMMENT '更新时间' ,
    `update_user` bigint COMMENT '更新人' ,
    `create_user_name` VARCHAR(50) COMMENT '创建人姓名',
    `update_user_name` VARCHAR(50) COMMENT '更新人姓名',
    primary key (`id`),
    index `idx_exam_student_grade_id` (`grade_id`)
    ) ENGINE = InnoDB CHARSET = utf8 COMMENT '学生';

create table if not exists `exam_history_score`
(
    `id` BIGINT not null,
    `student_id` BIGINT not null COMMENT '学生ID',
    `exam_time` DATETIME not null COMMENT '考试时间',
    `exam_type` VARCHAR(20) not null COMMENT '考试类型[MONTHLY(0):月考,MID_TERM(1):期中,FINAL(2):期末]',
    `total_score` INT not null COMMENT '总分数',
    `score` TEXT not null COMMENT '分数',
    `create_time` DATETIME COMMENT '创建时间' ,
    `create_user` bigint COMMENT '创建人' ,
    `update_time` DATETIME COMMENT '更新时间' ,
    `update_user` bigint COMMENT '更新人' ,
    `create_user_name` VARCHAR(50) COMMENT '创建人姓名',
    `update_user_name` VARCHAR(50) COMMENT '更新人姓名',
    primary key (`id`),
    index `idx_exam_history_score_st_id` (`student_id`)
    ) ENGINE = InnoDB CHARSET = utf8 COMMENT '考试记录';

create table if not exists exam_auto_increment(
     id BIGINT UNSIGNED KEY AUTO_INCREMENT,
     username VARCHAR(20) COMMENT '姓名',
    `create_time` DATETIME COMMENT '创建时间' ,
    `create_user` bigint COMMENT '创建人' ,
    `update_time` DATETIME COMMENT '更新时间' ,
    `update_user` bigint COMMENT '更新人' ,
    `create_user_name` VARCHAR(50) COMMENT '创建人姓名',
    `update_user_name` VARCHAR(50) COMMENT '更新人姓名'
) ENGINE = InnoDB CHARSET = utf8 COMMENT 'AutoTest';