USE sharding_sphere;
CREATE TABLE t_dict(
                       id BIGINT,
                       dict_type VARCHAR(200),
                       PRIMARY KEY(id)
);
USE sharding_sphere1;
CREATE TABLE t_dict(
                       id BIGINT,
                       dict_type VARCHAR(200),
                       PRIMARY KEY(id)
);
USE sharding_sphere2;
CREATE TABLE t_dict(
                       id BIGINT,
                       dict_type VARCHAR(200),
                       PRIMARY KEY(id)
);
