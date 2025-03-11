

-- 测试表 用于测试不配置分片时 shardingSphere执行普通表
CREATE TABLE student(
                        id INT PRIMARY KEY auto_increment,
                        name VARCHAR(20) ,
                        age INT DEFAULT 18,
                        create_time timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time timestamp DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)

insert into student
(`name`,age)
VALUEs
 ('Bob',22);

insert into student
(`name`,age)
VALUEs
('Sam',23);
