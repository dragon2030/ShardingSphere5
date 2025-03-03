package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.User;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//读写分离测试类
//@SpringBootTest
@SpringBootTest
public class ReadWriteTest {

    @Autowired
    private UserMapper userMapper;


    /**
     * 读写分离：写入数据的测试
     * Logic SQL 逻辑sql会向自定义的myds操作
     * sharding jdbc 会自动判断获得写数据源并插入数据
     */
    @Test
    public void testInsert(){

        User user = new User();
        user.setUname("张三丰");
        userMapper.insert(user);
    }
    //2025-03-03 20:14:58.807  INFO 27624 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_user  ( uname )  VALUES  ( ? )
    //2025-03-03 20:14:58.807  INFO 27624 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 20:14:58.807  INFO 27624 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: INSERT INTO t_user  ( uname )  VALUES  ( ? ) ::: [张三丰]

    /**
     * 读写分离：事务测试
     * 不添加@Transactional：insert对主库操作，select对从库操作
     * 添加@Transactional：则insert和select均对主库操作
     * **注意：**在JUnit环境下的@Transactional注解，默认情况下就会对事务进行回滚（**即使在没加注解@Rollback，也会对事务回滚**）
     */
    @Test
    public void testTrans(){

        User user = new User();
        user.setUname("铁锤");
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
    }
    //2025-03-03 20:27:43.533  INFO 22128 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_user  ( uname )  VALUES  ( ? )
    //2025-03-03 20:27:43.533  INFO 22128 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 20:27:43.533  INFO 22128 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: INSERT INTO t_user  ( uname )  VALUES  ( ? ) ::: [铁锤]
    //2025-03-03 20:27:43.633  INFO 22128 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  id,uname  FROM t_user
    //2025-03-03 20:27:43.633  INFO 22128 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:27:43.633  INFO 22128 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT  id,uname  FROM t_user
    @Transactional//开启事务
    @Test
    public void testTrans2(){

        User user = new User();
        user.setUname("铁锤");
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
    }
//2025-03-03 20:28:36.695  INFO 23576 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_user  ( uname )  VALUES  ( ? )
//2025-03-03 20:28:36.695  INFO 23576 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-03 20:28:36.695  INFO 23576 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: INSERT INTO t_user  ( uname )  VALUES  ( ? ) ::: [铁锤]
//2025-03-03 20:28:36.764  INFO 23576 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  id,uname  FROM t_user
//2025-03-03 20:28:36.764  INFO 23576 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-03 20:28:36.764  INFO 23576 --- [           main] ShardingSphere-SQL                       : Actual SQL: master ::: SELECT  id,uname  FROM t_user
//2025-03-03 20:28:37.013  INFO 23576 --- [           main] o.s.t.c.transaction.TransactionContext   : Rolled back transaction for test: [DefaultTestContext@54d18072 testClass = ReadWriteTest, testInstance = com.atguigu.shargingjdbcdemo.ReadWriteTest@470866d1, testMethod = testTrans2@ReadWriteTest, testException = [null], mergedContextConfiguration = [WebMergedContextConfiguration@1506f20f testClass = ReadWriteTest, locations = '{}', classes = '{class com.atguigu.shargingjdbcdemo.ShargingJdbcDemoApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@747f281, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4808bc9b, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@15f47664, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@6dd7b5a3, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@55b7a4e0], resourceBasePath = 'src/main/webapp', contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.web.ServletTestExecutionListener.activateListener' -> true, 'org.springframework.test.context.web.ServletTestExecutionListener.populatedRequestContextHolder' -> true, 'org.springframework.test.context.web.ServletTestExecutionListener.resetRequestContextHolder' -> true]]

    /**
     * 读写分离：负载均衡测试
     * 需要在一个进程中多次调用才能测试负载 每次调用一次测试方法只能请求第一个从库
     */
    @Test
    public void testSelectAll(){

        List<User> users1 = userMapper.selectList(null);
        List<User> users2 = userMapper.selectList(null);
        List<User> users3 = userMapper.selectList(null);
        List<User> users4 = userMapper.selectList(null);
//        users.forEach(System.out::println);
    }
    //2025-03-03 20:32:32.332  INFO 3076 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.378  INFO 3076 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.379  INFO 3076 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:32:32.379  INFO 3076 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave2 ::: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.381  INFO 3076 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.381  INFO 3076 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:32:32.381  INFO 3076 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave1 ::: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.382  INFO 3076 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  id,uname  FROM t_user
    //2025-03-03 20:32:32.382  INFO 3076 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:32:32.382  INFO 3076 --- [           main] ShardingSphere-SQL                       : Actual SQL: slave2 ::: SELECT  id,uname  FROM t_user

}
