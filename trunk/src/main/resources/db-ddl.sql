set character set utf8;
create database tianshi DEFAULT CHARACTER SET utf8;
use tianshi;

DROP TABLE IF EXISTS t_bouns_conf;
CREATE TABLE t_bouns_conf (
  res_id int(11) primary key NOT NULL,
  rank_id int(11) ,
  direct_p varchar(40) ,
  indirect_p varchar(40) ,
  honor_p varchar(40) ,
  special_p varchar(40) ,
  achieve_p varchar(40) ,
  remark varchar(40) ,
  w_1 varchar(40) ,
  w_2 varchar(40) ,
  w_3 varchar(40) ,
  w_4 varchar(40) ,
  w_5 varchar(40) ,
  w_6 varchar(40) ,
  w_7 varchar(40) ,
  w_8 varchar(40) 
 );


DROP TABLE IF EXISTS t_dictionary;
CREATE TABLE t_dictionary (
  dict_type_id int(11) ,
  busi_id int(11) primary key NOT NULL,
  busi_name varchar(64) ,
  busi_status varchar(10) 
);



DROP TABLE IF EXISTS t_dictionary_type;
CREATE TABLE t_dictionary_type (
  dict_type_id int(11) primary key NOT NULL,
  dict_type_name varchar(255) 
);



DROP TABLE IF EXISTS t_distributor;
CREATE TABLE t_distributor (
  res_id int(11) primary key NOT NULL,
  shop_id int(11) ,
  rank_id int(11) ,
  distributor_code varchar(8) NOT NULL,
  distributor_name varchar(255) ,
  sponsor_id int(11) NOT NULL,
  create_time datetime ,
  address varchar(255) ,
  telephone varchar(40) ,
  remark varchar(255) ,
  sponsor_code varchar(8) ,
  floors int(11)  
 );


DROP TABLE IF EXISTS t_distributor_bouns;
CREATE TABLE t_distributor_bouns (
  res_id int(11) primary key NOT NULL,
  distributor_id int(11) ,
  bouns_date datetime ,
  direct_bouns decimal(10,0) ,
  indirect_bouns decimal(10,0) ,
  leader_bouns decimal(10,0) ,
  honor_bouns decimal(10,0) ,
  special_bouns decimal(10,0) ,
  internatial_bouns decimal(10,0) ,
  adjust_bouns decimal(10,0) ,
  tax decimal(10,0) ,
  computer_fee decimal(10,0) ,
  check_flag char(1) ,
  check_man varchar(255) ,
  check_time datetime ,
  remark varchar(255) ,
  rank_id int(11) 
);


DROP TABLE IF EXISTS t_distributor_bouns_his;
CREATE TABLE t_distributor_bouns_his (
  res_id int(11) primary key NOT NULL,
  distributor_id int(11) ,
  bouns_date datetime ,
  direct_bouns decimal(10,0) ,
  indirect_bouns decimal(10,0) ,
  leader_bouns decimal(10,0) ,
  honor_bouns decimal(10,0) ,
  special_bouns decimal(10,0) ,
  internatial_bouns decimal(10,0) ,
  adjust_bouns decimal(10,0) ,
  tax decimal(10,0) ,
  computer_fee decimal(10,0) ,
  check_flag char(1) ,
  check_man varchar(255) ,
  check_time datetime ,
  remark varchar(255) ,
  rank_id int(11) 
);


DROP TABLE IF EXISTS t_distributor_grade;
CREATE TABLE t_distributor_grade (
  res_id int(11) primary key NOT NULL,
  distributor_id int(11) NOT NULL,
  achieve_date datetime ,
  person_achieve int(11) ,
  direct_achieve int(11) ,
  indirect_achieve int(11) ,
  accu_achieve int(11) ,
  net_achieve int(11) ,
  cell_achieve int(11) ,
  accu_p_achieve int(11) ,
  check_flag char(1) ,
  check_man varchar(64) ,
  remark varchar(255) ,
  distributor_code varchar(8) NOT NULL,
  floors int(11) NOT NULL
 );


DROP TABLE IF EXISTS t_distributor_grade_his;
CREATE TABLE t_distributor_grade_his (
  res_id int(11) primary key NOT NULL default 0,
  distributor_id int(11) ,
  achieve_date datetime ,
  person_achieve int(11) ,
  direct_achieve int(11) ,
  indirect_achieve int(11) ,
  accu_achieve int(11) ,
  net_achieve int(11) ,
  cell_achieve int(11) ,
  accu_p_achieve int(11) ,
  check_flag char(1) ,
  check_man varchar(64) ,
  remark varchar(255) ,
  distributor_code varchar(8) ,
  floors int(11) 
);


DROP TABLE IF EXISTS t_distributor_rank;
CREATE TABLE t_distributor_rank (
  res_id int(11) primary key NOT NULL,
  rank_status varchar(10) ,
  remark varchar(64) ,
  rank_name varchar(64) 
);

DROP TABLE IF EXISTS t_product_info;
CREATE TABLE t_product_info (
  res_id int(11) primary key NOT NULL,
  product_code varchar(15) NOT NULL,
  product_name varchar(64) NOT NULL,
  product_price double NOT NULL,
  product_pv double NOT NULL,
  product_bv double NOT NULL,
  remark varchar(255) ,
  status varchar(1) 
);


DROP TABLE IF EXISTS t_product_list;
CREATE TABLE t_product_list (
  res_id int(11) primary key NOT NULL,
  distributor_id int(11) NOT NULL,
  distributor_code varchar(8) NOT NULL,
  product_code varchar(20) NOT NULL,
  shop_code varchar(20) NOT NULL,
  sale_time datetime ,
  sale_number int(11) ,
  sale_price double ,
  sum_price double ,
  pv double ,
  bv double ,
  create_time datetime  ,
  creator varchar(12) ,
  check_flag char(1)  ,
  check_man varchar(12),
  floors int(11) ,
  remark varchar(255) 
  );


DROP TABLE IF EXISTS t_shop_info;
CREATE TABLE t_shop_info (
  res_id int(11) primary key NOT NULL,
  shop_code varchar(8) NOT NULL,
  shop_name varchar(64) NOT NULL,
  shop_owner varchar(8) NOT NULL,
  shop_country varchar(64) ,
  shop_city varchar(64) ,
  create_time datetime ,
  creator varchar(255) ,
  remark varchar(255) ,
  shop_addr varchar(255)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  USER_ID bigint(20) primary key unique NOT NULL auto_increment ,
  USER_NAME varchar(20) unique  default 'admin',
  PASSWORD varchar(42)  default '000000',
  CREATE_TIME datetime  ,
  GENDER varchar(255) 
);