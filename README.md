# ShardingSphere5
学习读写分离 和 数据分片（分库分表）操作

官网文档 https://shardingsphere.apache.org/

## sharding-jdbc
### mysql主从同步
mysql主从（主备）同步由mysql配置实现
？？这边先不测试主从了 后面可以本地下载的docker，然后用docker容器加compose跨平台的快速部署测试环境？？

注意以下所有配置文件
读写分离
读写分离.sql
application-读写分离.properties
ReadWriteTest.java

垂直分库
垂直分库.sql
application-垂直分片.properties
VerticalShardingTest.java

水平分库
水平分表.sql
application-水平分库.properties
HorizontalPartitioningTest.java

水平分库+水平分表+分片算法配置
application-水平分库+水平分表.properties
HorizontalShardingTest.java

多表关联
MultipleTableAssociation.java
application-多表关联.properties
水平分库+水平分表.sql

绑定表
BindingTableTest.java
application-绑定表.properties

广播表
绑定表.sql
ShardingTest.java
application-广播表.properties




## sharding-proxy
