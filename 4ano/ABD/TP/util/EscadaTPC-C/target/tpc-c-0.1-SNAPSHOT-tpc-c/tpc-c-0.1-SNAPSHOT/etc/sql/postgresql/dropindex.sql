ALTER TABLE customer DROP CONSTRAINT PK_customer;  
DROP INDEX IX_customer; 

ALTER TABLE district DROP CONSTRAINT PK_district; 

ALTER TABLE item DROP CONSTRAINT PK_item;   

ALTER TABLE order_line DROP CONSTRAINT PK_order_line;   
DROP INDEX IX_order_line;

DROP INDEX IX_new_order;
DROP INDEX PK_orders;
DROP INDEX IX_orders;

ALTER TABLE stock DROP CONSTRAINT PK_stock; 
DROP INDEX IX_stock; 

ALTER TABLE warehouse DROP CONSTRAINT PK_warehouse;
