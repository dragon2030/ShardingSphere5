-- 主
CREATE DATABASE if not exists sharding_sphere;
USE sharding_sphere;
CREATE TABLE t_user (
    id BIGINT AUTO_INCREMENT,
    uname VARCHAR(30),
    PRIMARY KEY (id)
);
INSERT INTO t_user(uname) VALUES('zhang3');
INSERT INTO t_user(uname) VALUES(@@hostname);
-- 从1
CREATE DATABASE if not exists sharding_sphere1;
USE sharding_sphere1;
CREATE TABLE t_user (
    id BIGINT AUTO_INCREMENT,
    uname VARCHAR(30),
    PRIMARY KEY (id)
);
INSERT INTO t_user(uname) VALUES('zhang3');
INSERT INTO t_user(uname) VALUES(@@hostname);
-- 从2
CREATE DATABASE if not exists sharding_sphere2;
USE sharding_sphere2;
CREATE TABLE t_user (
    id BIGINT AUTO_INCREMENT,
    uname VARCHAR(30),
    PRIMARY KEY (id)
);
INSERT INTO t_user(uname) VALUES('zhang3');
INSERT INTO t_user(uname) VALUES(@@hostname);
