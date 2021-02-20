--CREATE DATABASE tpcc;

--
-- TOC entry 16 (OID 17148)
-- Name: customer; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE customer (
    c_id integer NOT NULL,
    c_d_id integer NOT NULL,
    c_w_id integer NOT NULL,
    c_first char(16),
    c_middle char(2),
    c_last char(16),
    c_street_1 char(20),
    c_street_2 char(20),
    c_city char(20),
    c_state char(2),
    c_zip char(9),
    c_phone char(16),
    c_since date,
    c_credit char(2),
    c_credit_lim decimal(12,2),
    c_discount decimal(4,4),
    c_balance decimal(12,2),
    c_ytd_payment decimal(12,2),
    c_payment_cnt integer,
    c_delivery_cnt integer,
    c_data varchar(500)
);


--
-- TOC entry 17 (OID 17158)
-- Name: district; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE district (
    d_id integer NOT NULL,
    d_w_id integer NOT NULL,
    d_name char(10),
    d_street_1 char(20),
    d_street_2 char(20),
    d_city char(20),
    d_state char(2),
    d_zip char(9),
    d_tax decimal(4,4),
    d_ytd decimal(12,2),
    d_next_o_id integer
);

--
-- TOC entry 18 (OID 17165)
-- Name: history; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE history (
    h_c_id integer NOT NULL,
    h_c_d_id integer,
    h_c_w_id integer,
    h_d_id integer,
    h_w_id integer,
    h_date timestamp NOT NULL,
    h_amount decimal(6,2),
    h_data char(24)
);


--
-- TOC entry 19 (OID 17170)
-- Name: item; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE item (
    i_id integer NOT NULL,
    i_im_id integer,
    i_name char(24),
    i_price decimal(5,2),
    i_data char(50)
);


--
-- TOC entry 20 (OID 17177)
-- Name: new_order; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE new_order (
    no_o_id integer NOT NULL,
    no_d_id integer NOT NULL,
    no_w_id integer NOT NULL
);

--
-- TOC entry 21 (OID 17182)
-- Name: order_line; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE order_line (
    ol_o_id integer NOT NULL,
    ol_d_id integer NOT NULL,
    ol_w_id integer NOT NULL,
    ol_number integer NOT NULL,
    ol_i_id integer,
    ol_supply_w_id integer,
    ol_delivery_d timestamp,
    ol_quantity integer,
    ol_amount decimal(6,2),
    ol_dist_info char(24)
);


--
-- TOC entry 22 (OID 17187)
-- Name: orders; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE orders (
    o_id integer NOT NULL,
    o_d_id integer NOT NULL,
    o_w_id integer NOT NULL,
    o_c_id integer,
    o_entry_d timestamp,
    o_carrier_id integer,
    o_ol_cnt integer,
    o_all_local integer
);

--
-- TOC entry 23 (OID 17192)
-- Name: stock; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE stock (
    s_i_id integer NOT NULL,
    s_w_id integer NOT NULL,
    s_quantity integer,
    s_dist_01 char(24),
    s_dist_02 char(24),
    s_dist_03 char(24),
    s_dist_04 char(24),
    s_dist_05 char(24),
    s_dist_06 char(24),
    s_dist_07 char(24),
    s_dist_08 char(24),
    s_dist_09 char(24),
    s_dist_10 char(24),
    s_ytd integer,
    s_order_cnt integer,
    s_remote_cnt integer,
    s_data char(50)
);


--
-- TOC entry 24 (OID 17199)
-- Name: warehouse; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE warehouse (
    w_id integer NOT NULL,
    w_name char(10),
    w_street_1 char(20),
    w_street_2 char(20),
    w_city char(20),
    w_state char(2),
    w_zip char(9),
    w_tax decimal(4,4),
    w_ytd decimal(12,2)
);

--
-- TOC entry 27 (OID 1115795)
-- Name: pk_customer; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (c_w_id, c_d_id, c_id);


--
-- TOC entry 29 (OID 1115798)
-- Name: pk_district; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE district
    ADD CONSTRAINT pk_district PRIMARY KEY (d_w_id, d_id);


--
-- TOC entry 31 (OID 1115800)
-- Name: pk_item; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE item
    ADD CONSTRAINT pk_item PRIMARY KEY (i_id);


--
-- TOC entry 34 (OID 1115802)
-- Name: pk_order_line; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE order_line
    ADD CONSTRAINT pk_order_line PRIMARY KEY (ol_w_id, ol_d_id, ol_o_id, ol_number);


--
-- TOC entry 39 (OID 1115808)
-- Name: pk_stock; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE stock
    ADD CONSTRAINT pk_stock PRIMARY KEY (s_w_id, s_i_id);


--
-- TOC entry 41 (OID 1115811)
-- Name: pk_warehouse; Type: CONSTRAINT; Schema: public; Owner: tpcc
--

ALTER TABLE warehouse
    ADD CONSTRAINT pk_warehouse PRIMARY KEY (w_id);

--
-- TOC entry 36 (OID 1115805)
-- Name: pk_orders; Type: INDEX; Schema: public; Owner: tpcc
--

ALTER TABLE orders
    ADD CONSTRAINT pk_orders PRIMARY KEY (o_w_id, o_d_id, o_id);


alter table history add constraint pk_history
 primary key (h_date, h_c_id);

alter table new_order add constraint pk_new_order
  primary key (no_w_id, no_d_id, no_o_id);

