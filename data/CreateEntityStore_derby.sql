
-- "jdbc:derby:" + dbName + ";create=true"
-- CONNECT 'jdbc:derby:/home/tnuss/apps/derbydata/firstdb; create=true';
-- CONNECT 'jdbc:derby:/home/tnuss/apps/derbydata/firstdb';
DROP TABLE TDAYDATA;

-- DROP schema SENTITY restrict;
-- schema must be empty to drop
-- CREATE SCHEMA SENTITY;

--SHOW WARNINGS;

CREATE TABLE TDAYDATA (
  EQUITYKEY CHAR(16) NOT NULL,
  DAYDATE CHAR(10) NOT NULL,
  LAST_PRICE DECIMAL(11,3) NOT NULL,
  HI_PRICE DECIMAL(11,3) NOT NULL,
  MIN_PRICE DECIMAL(11,3) NOT NULL,
  CHANGE DECIMAL(8,3) NOT NULL,
  VOLUME INT NOT NULL,
  DATA_TS TIMESTAMP NOT NULL
  );

ALTER TABLE TDAYDATA
ADD CONSTRAINT PK_DAYDATA PRIMARY KEY (
      EQUITYKEY, DAYDATE) ;

CREATE INDEX INDEX_DAYDATE ON TDAYDATA (
      DAYDATE) ;

