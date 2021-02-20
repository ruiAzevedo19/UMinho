USE tpcc

--
-- TOC entry 16 (OID 17148)
-- Name: customer; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE customer (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    c_id integer NOT NULL,
    c_d_id integer NOT NULL,
    c_w_id integer NOT NULL,
    c_first character(16),
    c_middle character(2),
    c_last character(16),
    c_street_1 character(20),
    c_street_2 character(20),
    c_city character(20),
    c_state character(2),
    c_zip character(9),
    c_phone character(16),
    c_since date,
    c_credit character(2),
    c_credit_lim numeric(12,2),
    c_discount numeric(4,4),
    c_balance numeric(12,2),
    c_ytd_payment numeric(12,2),
    c_payment_cnt integer,
    c_delivery_cnt integer,
    c_data text
) ENGINE=InnoDB;


--
-- TOC entry 17 (OID 17158)
-- Name: district; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE district (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    d_id integer NOT NULL,
    d_w_id integer NOT NULL,
    d_name character(10),
    d_street_1 character(20),
    d_street_2 character(20),
    d_city character(20),
    d_state character(2),
    d_zip character(9),
    d_tax numeric(4,4),
    d_ytd numeric(12,2),
    d_next_o_id integer
) ENGINE=InnoDB;;

--
-- TOC entry 18 (OID 17165)
-- Name: history; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE history (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    h_c_id integer,
    h_c_d_id integer,
    h_c_w_id integer,
    h_d_id integer,
    h_w_id integer,
    h_date date,
    h_amount numeric(6,2),
    h_data character(24)
) ENGINE=InnoDB;;


--
-- TOC entry 19 (OID 17170)
-- Name: item; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE item (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    i_id integer NOT NULL,
    i_im_id integer,
    i_name character(24),
    i_price numeric(5,2),
    i_data character(50)
) ENGINE=InnoDB;;


--
-- TOC entry 20 (OID 17177)
-- Name: new_order; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE new_order (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    no_o_id integer,
    no_d_id integer,
    no_w_id integer
) ENGINE=InnoDB;;

--
-- TOC entry 21 (OID 17182)
-- Name: order_line; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE order_line (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    ol_o_id integer NOT NULL,
    ol_d_id integer NOT NULL,
    ol_w_id integer NOT NULL,
    ol_number integer NOT NULL,
    ol_i_id integer,
    ol_supply_w_id integer,
    ol_delivery_d date,
    ol_quantity integer,
    ol_amount numeric(6,2),
    ol_dist_info character(24)
) ENGINE=InnoDB;;


--
-- TOC entry 22 (OID 17187)
-- Name: orders; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE orders (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    o_id integer,
    o_d_id integer,
    o_w_id integer,
    o_c_id integer,
    o_entry_d date,
    o_carrier_id integer,
    o_ol_cnt integer,
    o_all_local integer
) ENGINE=InnoDB;;

--
-- TOC entry 23 (OID 17192)
-- Name: stock; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE stock (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    s_i_id integer NOT NULL,
    s_w_id integer NOT NULL,
    s_quantity integer,
    s_dist_01 character(24),
    s_dist_02 character(24),
    s_dist_03 character(24),
    s_dist_04 character(24),
    s_dist_05 character(24),
    s_dist_06 character(24),
    s_dist_07 character(24),
    s_dist_08 character(24),
    s_dist_09 character(24),
    s_dist_10 character(24),
    s_ytd integer,
    s_order_cnt integer,
    s_remote_cnt integer,
    s_data character(50)
) ENGINE=InnoDB;;


--
-- TOC entry 24 (OID 17199)
-- Name: warehouse; Type: TABLE; Schema: public; Owner: tpcc
--

CREATE TABLE warehouse (
    oid integer NOT NULL AUTO_INCREMENT UNIQUE,
    w_id integer NOT NULL,
    w_name character(10),
    w_street_1 character(20),
    w_street_2 character(20),
    w_city character(20),
    w_state character(2),
    w_zip character(9),
    w_tax numeric(4,4),
    w_ytd numeric(12,2)
) ENGINE=InnoDB;;


--
-- TOC entry 25 (OID 1115797)
-- Name: ix_customer; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_customer ON customer (c_w_id, c_d_id, c_last);


--
-- TOC entry 33 (OID 1115804)
-- Name: ix_order_line; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_order_line ON order_line (ol_i_id);


--
-- TOC entry 36 (OID 1115805)
-- Name: pk_orders; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX pk_orders ON orders (o_w_id, o_d_id, o_id);


--
-- TOC entry 35 (OID 1115806)
-- Name: ix_orders; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_orders ON orders (o_w_id, o_d_id, o_c_id);


--
-- TOC entry 32 (OID 1115807)
-- Name: ix_new_order; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_new_order ON new_order (no_w_id, no_d_id, no_o_id);


--
-- TOC entry 37 (OID 1115810)
-- Name: ix_stock; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_stock ON stock (s_i_id);

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

