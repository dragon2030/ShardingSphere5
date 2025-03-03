USE sharding_sphere;
CREATE TABLE if not exists t_user (
                        id BIGINT AUTO_INCREMENT,
                        uname VARCHAR(30),
                        PRIMARY KEY (id)
);

USE sharding_sphere1;
CREATE TABLE if not exists t_order (
                         id BIGINT AUTO_INCREMENT,
                         order_no VARCHAR(30),
                         user_id BIGINT,
                         amount DECIMAL(10,2),
                         PRIMARY KEY(id)
);
