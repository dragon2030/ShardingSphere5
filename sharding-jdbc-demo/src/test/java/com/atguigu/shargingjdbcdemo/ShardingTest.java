package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.*;
import com.atguigu.shargingjdbcdemo.mapper.DictMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderItemMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderMapper;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ShardingTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;


    /**
     * 广播表：插入测试
     */
    @Test
    public void testInsertBroadcast(){

        Dict dict = new Dict();
        dict.setDictType("type1");
        dictMapper.insert(dict);
    }
//2025-03-04 20:41:12.821  INFO 11272 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_dict  ( id,dict_type )  VALUES  ( ?,? )
//2025-03-04 20:41:12.822  INFO 11272 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:41:12.822  INFO 11272 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_dict  ( id,dict_type )  VALUES  (?, ?) ::: [1896903753334181890, type1]
//2025-03-04 20:41:12.822  INFO 11272 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_dict  ( id,dict_type )  VALUES  (?, ?) ::: [1896903753334181890, type1]
//2025-03-04 20:41:12.822  INFO 11272 --- [main] ShardingSphere-SQL: Actual SQL: server-user ::: INSERT INTO t_dict  ( id,dict_type )  VALUES  (?, ?) ::: [1896903753334181890, type1]


    /**
     * 广播表：查询测试
     * 会在随机一个数据源中进行查询
     */
    @Test
    public void testSelectBroadcast(){

        List<Dict> dictList = dictMapper.selectList(null);
        dictMapper.selectList(null);
        dictMapper.selectList(null);
        dictMapper.selectList(null);
        dictMapper.selectList(null);
        dictMapper.selectList(null);
        dictList.forEach(System.out::println);
    }
//2025-03-04 20:42:40.222  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.222  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.222  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.279  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.279  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.279  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-user ::: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.281  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.281  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.281  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.282  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.283  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.283  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.284  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.284  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.284  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.285  INFO 34624 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,dict_type  FROM t_dict
//2025-03-04 20:42:40.285  INFO 34624 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:42:40.285  INFO 34624 --- [main] ShardingSphere-SQL: Actual SQL: server-user ::: SELECT  id,dict_type  FROM t_dict
//Dict(id=1896903753334181890, dictType=type1)
}
