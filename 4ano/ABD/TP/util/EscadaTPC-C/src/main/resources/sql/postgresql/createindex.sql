ALTER TABLE customer  ADD 
	CONSTRAINT PK_customer PRIMARY KEY   
	(
		c_w_id,
		c_d_id,
		c_id
	);
CREATE  INDEX IX_customer ON customer(c_w_id,c_d_id,c_last);
 
ALTER TABLE district  ADD 
	CONSTRAINT PK_district PRIMARY KEY   
	(
		d_w_id,
		d_id
	); 

ALTER TABLE item  ADD 
	CONSTRAINT PK_item PRIMARY KEY   
	(
		i_id
	); 

ALTER TABLE order_line  ADD 
	CONSTRAINT PK_order_line PRIMARY KEY   
	(
		ol_w_id,
		ol_d_id,
		ol_o_id,
		ol_number
	); 
CREATE  INDEX IX_order_line ON order_line(ol_i_id);

CREATE  INDEX PK_orders on orders (o_w_id,o_d_id,o_id);
CREATE  INDEX IX_orders ON orders(o_w_id,o_d_id,o_c_id);

CREATE  INDEX IX_new_order ON new_order(no_w_id, no_d_id, no_o_id);

ALTER TABLE stock ADD 
CONSTRAINT PK_stock PRIMARY KEY   
	(
		s_w_id,
		s_i_id
	); 
CREATE INDEX IX_stock ON stock(s_i_id);

ALTER TABLE warehouse  ADD 
	CONSTRAINT PK_warehouse PRIMARY KEY   
	(
		w_id
	);
