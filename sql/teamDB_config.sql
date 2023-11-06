-- 최초 작성일시 : 2023-11-03
-- 최초 작성자 : 이창규
-- 수정일시 : 2023-11-04
-- 수정일시 : 2023-11-06
-- 작성자 : 이창규
-- 

use team;

DROP TABLE  if exists user;
DROP TABLE  if exists nonuser;
DROP TABLE  if exists bill;
DROP TABLE  if exists company;
DROP TABLE  if exists flwOpt;
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

create table `nonuser` (
	`non_cp` VARCHAR(20) not null comment '비회원 전화번호',
	`non_name` VARCHAR(10) NOT NULL COMMENT '비회원 성함',
	`non_addr` VARCHAR(100) not null COMMENT '비회원 주소',
	`non_Daddr` varchar(30) not null comment '비회원 상세주소',
	`reg_date` DATETIME NOT NULL DEFAULT current_timestamp() comment '등록날짜',
	PRIMARY KEY (`non_cp`)
);

create table `bill` (
	`bill_no` varchar(10) not null comment '주문 번호',
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
	 primary key (`bill_no`)
);

create table company(
	`company_cd` varchar(2) NOT NULL comment '택배사 코드',
	`company_name` varchar(30) comment '택배사명',
	primary key (`company_cd`)
);

DROP table IF EXISTS flwOpt;
create table flwOpt(
	`flwOpt_no` int(10) NOT NULL AUTO_INCREMENT  comment '옵션 번호',
	`flwOpt_name` varchar(10) NOT NULL comment '상품명',
	`flwOpt_weight` int(10) comment '상품 무게',
	`flwOpt_size` varchar(15) comment '상품 크기',
	`flwOpt_fee` int(10) comment '요금',
	`bill_no` VARCHAR(10) not null comment '주문 번호',
	 primary key (`flwOpt_no`)
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

-- 최초 작성자 : 이양진
-- 작성 일시 : 2023.11.05
-- 수정 : ( 2023.11.06 이창규 ) 
DROP table IF EXISTS purchase_history;
CREATE TABLE purchase_history(
	`user_id` varchar(15) comment '회원 ID',
    `non_name` VARCHAR(10) NOT NULL COMMENT '비회원 성함',
    `bill_no` varchar(10) not null comment '주문 번호',
    `flwOpt_name` varchar(10) NOT NULL comment '상품명',
    `flwOpt_size` varchar(15) comment '상품 크기',
    `flwOpt_fee` int(10) comment '요금',
	PRIMARY KEY(`user_id`),
    FOREIGN KEY(`bill_no`) REFERENCES bill(`bill_no`) on DELETE CASCADE
);

-- company DB 추가 === (2023.11.05 차소영 수정)

insert into company values('01', 'CJ대한통운');
insert into company values('02', '롯데택배');
insert into company values('03', '우체국택배');
insert into company values('04', '로젠택배');
insert into company values('05', '한진택배');
insert into company values('06', 'CU 편의점택배');
insert into company values('07', 'EMS 택배');
insert into company values('08', '경동택배');
insert into company values('09', '대신택배');
insert into company values('10', 'DHL 택배');
insert into company values('11', '하이택배');
insert into company values('12', 'CVSnet 편의점택배');
insert into company values('13', '합동택배');
insert into company values('14', '천일택배');
insert into company values('15', 'APEX 택배');
insert into company values('16', '세방 택배');
insert into company values('17', 'KGB택배');
insert into company values('18', 'SLX 택배');
insert into company values('19', '일양로지스');
insert into company values('20', '홈픽택배');

-- data3.txt 각자 경로로 설정
load data local infile 'D:\\project_data\\team3\\sql\\data3.txt' into table sigugun fields terminated BY '\t' lines terminated by '\n';


-- 관계 설정
alter table bill add foreign key(user_id) REFERENCES user( user_id);
alter table bill add foreign key(company_cd) REFERENCES company( company_cd);
alter table user_address add foreign key(user_id) REFERENCES user( user_id);
alter table flwOpt add foreign key(bill_no) REFERENCES bill(bill_no) ON DELETE CASCADE;
alter table bill add foreign key (non_cp) REFERENCES nonuser( non_cp);
