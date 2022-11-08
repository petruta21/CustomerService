DROP TABLE customer IF EXISTS;
CREATE TABLE customer
(id INT auto_increment,
 name TEXT NOT NULL,
 age  INT  NOT NULL);

ALTER TABLE customer ADD PRIMARY KEY (id);