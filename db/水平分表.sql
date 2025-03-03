USE sharding_sphere;
CREATE TABLE if not exists t_order0 (
      id BIGINT,
      order_no VARCHAR(30),
      user_id BIGINT,
      amount DECIMAL(10,2),
      PRIMARY KEY(id)
);
CREATE TABLE if not exists t_order1 (
    id BIGINT,
    order_no VARCHAR(30),
    user_id BIGINT,
    amount DECIMAL(10,2),
    PRIMARY KEY(id)
);

USE sharding_sphere1;
CREATE TABLE if not exists t_order0 (
    id BIGINT,
    order_no VARCHAR(30),
    user_id BIGINT,
    amount DECIMAL(10,2),
    PRIMARY KEY(id)
);
CREATE TABLE if not exists t_order1 (
      id BIGINT,
      order_no VARCHAR(30),
      user_id BIGINT,
      amount DECIMAL(10,2),
      PRIMARY KEY(id)
);
