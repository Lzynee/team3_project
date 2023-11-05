-- 작성일시 : 2023-11-03
-- 작성자 : 이창규
-- 수정일시 : 2023-11-04

DROP TABLE  if exists user;
DROP TABLE  if exists nonuser;
DROP TABLE  if exists waybill;
DROP TABLE  if exists company;
DROP TABLE  if exists parcel;
DROP TABLE  if exists user_address;
DROP TABLE  if exists sigugun;



CREATE TABLE `user` (
	user_id VARCHAR(15) NOT NULL PRIMARY KEY COMMENT '회원 ID',
	user_name VARCHAR(10) NOT NULL COMMENT '회원 성함',
	user_pw VARCHAR(20) not null COMMENT '회원 비밀번호',
	user_addr VARCHAR(100) not null COMMENT '회원 주소',
	user_cp VARCHAR(20) not null comment '회원 전화번호',
	user_DAddr VARCHAR(30) NOT NULL COMMENT '회원 상세주소',
	user_grade INT(10) comment '등급'
	
);

use team;

create table `nonuser` (
	`non_cp` VARCHAR(20) not null comment '비회원 전화번호',
	`non_name` VARCHAR(10) NOT NULL COMMENT '비회원 성함',
	`non_addr` VARCHAR(100) not null COMMENT '비회원 주소',
	`non_Daddr` varchar(30) not null comment '비회원 상세주소',
	`reg_date` DATETIME NOT NULL DEFAULT current_timestamp() comment '등록날짜',
	PRIMARY KEY (`non_cp`)
);

create table `waybill` (
	`waybill_no` varchar(10) not null comment '운송장 번호',
	`rcvr_name` VARCHAR(10) not null comment '받는사람 이름',
	`rcvr_addr` varchar(100) not null comment '받는사람 주소',
	`rcvr_Daddr` varchar(30) not null comment '받는사람 상세주소',
	`rcvr_cp` varchar(20) not null comment '받는사람 전화번호',
	`company_cd` varchar(2) comment '택배사 코드',
	`user_id` varchar(15) comment '회원 ID',
	`non_cp` varchar(20) comment '비회원 전화번호',
	`reg_date` DATETIME NOT NULL DEFAULT current_timestamp() comment '등록날짜',
	`total_fee` int(20) comment '최종 요금',
	`up_date` DATETIME comment '받은날짜',
	`msg` varchar(30) comment '요청사항',
	 primary key (`waybill_no`)
);

create table company(
	`company_cd` varchar(2) NOT NULL comment '택배사 코드',
	`company_name` varchar(30) comment '택배사명',
	primary key (`company_cd`)
);

create table parcel(
	`parcel_no` int(10) NOT NULL AUTO_INCREMENT  comment '택배 번호',
	`parcel_name` varchar(10) NOT NULL comment '택배 명',
	`parcel_weight` int(10) comment '택배 무게',
	`parcel_size` varchar(15) comment '택배 크기',
	`parcel_fee` int(10) comment '요금',
	`waybill_no` VARCHAR(10) not null comment '운송장 번호',
	 primary key (`parcel_no`)
);

CREATE TABLE `sigugun` (
	`zipcode` INT(10) NULL DEFAULT NULL,
	`sido` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`sigugun` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`eup` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`dong` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',
	`num` INT(10) NULL DEFAULT NULL,
	`bunum` INT(10) NULL DEFAULT NULL
);

create table user_address(
	user_id VARCHAR(15) NOT NULL  comment '유저ID',
	rcvr_name VARCHAR(10) not null comment '받는사람 이름',
	rcvr_addr varchar(100) not null comment '받는사람 주소',
	rcvr_Daddr varchar(30) not null comment '받는사람 상세주소',
	rcvr_cp varchar(20) not null comment '받는사람 전화번호'
);

--- 주문 내역 조회 리스트 생성을 위한 테이블 추가 === (Nov.5 이양진)
CREATE TABLE purchase_history(
	`user_id` varchar(15) comment '회원 ID',
    `non_name` VARCHAR(10) NOT NULL COMMENT '비회원 성함',
    `waybill_no` varchar(10) not null comment '운송장 번호',
    `parcel_name` varchar(10) NOT NULL comment '택배 명',
    `parcel_size` varchar(15) comment '택배 크기',
    `parcel_fee` int(10) comment '요금',
	PRIMARY KEY(`user_id`),
    FOREIGN KEY(`waybill_no`) REFERENCES waybill(`waybill_no`) on DELETE CASCADE
);
--- 


-- data3.txt 각자 경로로 설정
load data local infile 'D:\\project_data\\team3\\sql\\data3.txt' into table sigugun fields terminated BY '\t' lines terminated by '\n';

use team;
select * from nonuser;

alter table waybill add foreign key(user_id) REFERENCES user( user_id);
alter table waybill add foreign key(company_cd) REFERENCES company( company_cd);
alter table user_address add foreign key(user_id) REFERENCES user( user_id);
alter table parcel add foreign key(waybill_no) REFERENCES waybill( waybill_no) ON DELETE CASCADE;
-- 아래 sql문은 잘못 되었으므로 변경
-- ALTER TABLE nonuser ADD PRIMARY KEY(non_cp);
-- alter table nonuser add foreign key(non_cp) REFERENCES waybill( non_cp);
alter table waybill add foreign key (non_cp) REFERENCES nonuser( non_cp);
-- 변경 완료
