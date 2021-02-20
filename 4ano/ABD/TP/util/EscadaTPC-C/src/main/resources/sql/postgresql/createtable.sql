CREATE SEQUENCE seqcustomer INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE customer 
(
	key int NOT NULL DEFAULT NEXTVAL('seqcustomer') CONSTRAINT keycustomer UNIQUE,
	c_id int NOT NULL ,
	c_d_id int NOT NULL ,
	c_w_id int NOT NULL ,
	c_first char (16) NULL ,
	c_middle char (2) NULL ,
	c_last char (16) NULL ,
	c_street_1 char (20) NULL ,
	c_street_2 char (20) NULL ,
	c_city char (20) NULL ,
	c_state char (2) NULL ,
	c_zip char (9) NULL ,
	c_phone char (16) NULL ,
	c_since timestamp NULL ,
	c_credit char (2) NULL ,
	c_credit_lim numeric(12, 2) NULL ,
	c_discount numeric(4, 4) NULL ,
	c_balance numeric(12, 2) NULL ,
	c_ytd_payment numeric(12, 2) NULL ,
	c_payment_cnt int NULL ,
	c_delivery_cnt int NULL ,
	c_data text NULL 
);


CREATE SEQUENCE seqdistrict INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE district (
	key int NOT NULL DEFAULT NEXTVAL('seqdistrict') CONSTRAINT keydisctrict UNIQUE,
	d_id int NOT NULL ,
	d_w_id int NOT NULL ,
	d_name char (10) NULL ,
	d_street_1 char (20) NULL ,
	d_street_2 char (20) NULL ,
	d_city char (20) NULL ,
	d_state char (2) NULL ,
	d_zip char (9) NULL ,
	d_tax numeric(4, 4) NULL ,
	d_ytd numeric(12, 2) NULL ,
	d_next_o_id int NULL 
);


CREATE SEQUENCE seqhistory INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE history (
	key int NOT NULL DEFAULT NEXTVAL('seqhistory') CONSTRAINT keyhistory UNIQUE,
	h_c_id int NULL ,
	h_c_d_id int NULL ,
	h_c_w_id int NULL ,
	h_d_id int NULL ,
	h_w_id int NULL ,
	h_date timestamp NULL ,
	h_amount numeric(6, 2) NULL ,
	h_data char (24) NULL 
);


CREATE SEQUENCE seqitem INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE item (
	key int NOT NULL DEFAULT NEXTVAL('seqitem') CONSTRAINT keyitem UNIQUE,
	i_id int NOT NULL ,
	i_im_id int NULL ,
	i_name char (24) NULL ,
	i_price numeric(5, 2) NULL ,
	i_data char (50) NULL 
);


CREATE SEQUENCE seqneworder INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE new_order (
	key int NOT NULL DEFAULT NEXTVAL('seqneworder') CONSTRAINT keyneworder UNIQUE,
	no_o_id int NULL ,
	no_d_id int NULL ,
	no_w_id int NULL 
);


CREATE SEQUENCE seqorderline INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE order_line (
	key int NOT NULL DEFAULT NEXTVAL('seqorderline') CONSTRAINT keyorderline UNIQUE,
	ol_o_id int NOT NULL ,
	ol_d_id int NOT NULL ,
	ol_w_id int NOT NULL ,
	ol_number int NOT NULL ,
	ol_i_id int NULL ,
	ol_supply_w_id int NULL ,
	ol_delivery_d timestamp NULL ,
	ol_quantity int NULL ,
	ol_amount numeric(6, 2) NULL ,
	ol_dist_info char (24) NULL 
);


CREATE SEQUENCE seqorders INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE orders (
	key int NOT NULL DEFAULT NEXTVAL('seqorders') CONSTRAINT keyorders UNIQUE,
	o_id int NULL ,
	o_d_id int NULL ,
	o_w_id int NULL ,
	o_c_id int NULL ,
	o_entry_d timestamp NULL ,
	o_carrier_id int NULL ,
	o_ol_cnt int NULL ,
	o_all_local int NULL 
);


CREATE SEQUENCE seqstock INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE stock (
	key int NOT NULL DEFAULT NEXTVAL('seqstock') CONSTRAINT keystock UNIQUE,
	s_i_id int NOT NULL ,
	s_w_id int NOT NULL ,
	s_quantity int NULL ,
	s_dist_01 char (24) NULL ,
	s_dist_02 char (24) NULL ,
	s_dist_03 char (24) NULL ,
	s_dist_04 char (24) NULL ,
	s_dist_05 char (24) NULL ,
	s_dist_06 char (24) NULL ,
	s_dist_07 char (24) NULL ,
	s_dist_08 char (24) NULL ,
	s_dist_09 char (24) NULL ,
	s_dist_10 char (24) NULL ,
	s_ytd int NULL ,
	s_order_cnt int NULL ,
	s_remote_cnt int NULL ,
	s_data char (50) NULL 
);

CREATE SEQUENCE seqwarehouse INCREMENT BY 1 NO MINVALUE NO MAXVALUE START WITH 1 CACHE 20 NO CYCLE; 
CREATE TABLE warehouse (
	key int NOT NULL DEFAULT NEXTVAL('seqwarehouse') CONSTRAINT keywarehouse UNIQUE,
	w_id int NOT NULL ,
	w_name char (10) NULL ,
	w_street_1 char (20) NULL ,
	w_street_2 char (20) NULL ,
	w_city char (20) NULL ,
	w_state char (2) NULL ,
	w_zip char (9) NULL ,
	w_tax numeric(4, 4) NULL ,
	w_ytd numeric(12, 2) NULL 
);
