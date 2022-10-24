CREATE TABLE CUSTOMER
(ID bigint auto_increment,
 NAME TEXT NOT NULL,
 AGE  INT  NOT NULL)

 alter table CUSTOMER
     add primary key (ID);

INSERT INTO CUSTOMER  VALUES ( 5, 'Denis', 38 );
INSERT INTO CUSTOMER  VALUES ( 2, 'Tatsiana', 37 ) ;
INSERT INTO CUSTOMER  VALUES ( 3, 'Sofiya', 12 );
INSERT INTO CUSTOMER  VALUES ( 4, 'Matsvej', 10 );