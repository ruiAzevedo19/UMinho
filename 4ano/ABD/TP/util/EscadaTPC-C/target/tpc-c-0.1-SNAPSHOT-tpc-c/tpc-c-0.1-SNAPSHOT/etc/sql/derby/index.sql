--
-- TOC entry 25 (OID 1115797)
-- Name: ix_customer; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_customer ON customer (c_w_id, c_d_id, c_last);


--
-- TOC entry 33 (OID 1115804)
-- Name: ix_order_line; Type: INDEX; Schema: public; Owner: tpcc
--

-- CREATE INDEX ix_order_line ON order_line (ol_i_id);


--
-- TOC entry 35 (OID 1115806)
-- Name: ix_orders; Type: INDEX; Schema: public; Owner: tpcc
--

CREATE INDEX ix_orders ON orders (o_w_id, o_d_id, o_c_id);


--
-- TOC entry 32 (OID 1115807)
-- Name: ix_new_order; Type: INDEX; Schema: public; Owner: tpcc
--

--CREATE INDEX ix_new_order ON new_order (no_w_id, no_d_id, no_o_id);


--
-- TOC entry 37 (OID 1115810)
-- Name: ix_stock; Type: INDEX; Schema: public; Owner: tpcc
--

--CREATE INDEX ix_stock ON stock (s_i_id);

