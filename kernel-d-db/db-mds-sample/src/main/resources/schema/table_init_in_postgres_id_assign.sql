/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

drop table if exists exam_class_grade;

drop table if exists exam_teacher;

drop table if exists exam_student;

drop table if exists exam_history_score;

create table if not exists exam_class_grade
(
    id BIGINT not null,
    name VARCHAR(100) not null,
    description VARCHAR(255) null default null,
    grade_type VARCHAR(20) not null,
    start_time timestamp,
    regulator_id BIGINT not null,
    create_time timestamp ,
    create_user bigint ,
    update_time timestamp ,
    update_user bigint ,
    create_user_name VARCHAR(50),
    update_user_name VARCHAR(50),
    primary key (id)
    );

create table if not exists exam_teacher
(
    id BIGINT not null,
    name VARCHAR(100) not null,
    card_num VARCHAR(50) not null unique,
    gender VARCHAR(20) not null,
    birthday DATE null default null,
    work_seniority INT not null,
    status_flag smallint not null,
    tech_courses VARCHAR(255) null default null,
    properties text,
    version_ BIGINT,
    create_time timestamp ,
    create_user bigint ,
    update_time timestamp ,
    update_user bigint ,
    create_user_name VARCHAR(50),
    update_user_name VARCHAR(50),
    primary key (id)
    ) ;

create table if not exists exam_student
(
    id BIGINT not null,
    grade_id BIGINT not null,
    name VARCHAR(100) not null,
    card_num VARCHAR(50) not null unique,
    gender VARCHAR(20) not null,
    birthday DATE null default null,
    take_courses VARCHAR(255) not null,
    from_foreign BOOLEAN not null,
    hometown VARCHAR(100) null default null,
    hobbies TEXT null default null,
    del_flag char(1),
    version_ BIGINT,
    create_time timestamp ,
    create_user bigint ,
    update_time timestamp ,
    update_user bigint ,
    create_user_name VARCHAR(50),
    update_user_name VARCHAR(50),
    primary key (id)
    ) ;

create table if not exists exam_history_score
(
    id BIGINT not null,
    student_id BIGINT not null,
    exam_time Date not null,
    exam_type VARCHAR(20) not null,
    total_score INT not null,
    score TEXT not null,
    create_time timestamp ,
    create_user bigint ,
    update_time timestamp ,
    update_user bigint ,
    create_user_name VARCHAR(50),
    update_user_name VARCHAR(50),
    primary key (id)
    ) ;

create table if not exists exam_auto_increment
(
      id SERIAL,
      username VARCHAR(20),
    create_time timestamp ,
    create_user bigint ,
    update_time timestamp ,
    update_user bigint ,
    create_user_name VARCHAR(50),
    update_user_name VARCHAR(50),
    primary key (id)
);