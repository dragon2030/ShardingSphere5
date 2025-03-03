package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.Order;
import com.atguigu.shargingjdbcdemo.entity.User;
import com.atguigu.shargingjdbcdemo.mapper.DictMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderItemMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderMapper;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

//垂直分片
@SpringBootTest
public class VerticalShardingTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    /**
     * 垂直分片：插入数据测试
     * server-user server-order 就对应配置文件中对应的逻辑表
     */
    @Test
    public void testInsertOrderAndUser(){

        User user = new User();
        user.setUname("强哥");
        userMapper.insert(user);

        Order order = new Order();
        order.setOrderNo("ATGUIGU001");
        order.setUserId(user.getId());
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);

    }
    //2025-03-03 20:49:42.550  INFO 26784 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_user  ( uname )  VALUES  ( ? )
    //2025-03-03 20:49:42.551  INFO 26784 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 20:49:42.551  INFO 26784 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-user ::: INSERT INTO t_user  ( uname )  VALUES  (?) ::: [强哥]
    //2025-03-03 20:49:42.583  INFO 26784 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_order  ( order_no,user_id,amount )  VALUES  ( ?,?,? )
    //2025-03-03 20:49:42.583  INFO 26784 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 20:49:42.583  INFO 26784 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order ::: INSERT INTO t_order  ( order_no,user_id,amount )  VALUES  (?, ?, ?) ::: [ATGUIGU001, 7, 100]

    /**
     * 垂直分片：查询数据测试
     */
    @Test
    public void testSelectFromOrderAndUser(){
        User user = userMapper.selectById(1L);
        Order order = orderMapper.selectById(1L);
    }
    //2025-03-03 20:52:06.295  INFO 31296 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT id,uname FROM t_user WHERE id=?
    //2025-03-03 20:52:06.295  INFO 31296 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:52:06.295  INFO 31296 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-user ::: SELECT id,uname FROM t_user WHERE id=?  ::: [1]
    //2025-03-03 20:52:06.363  INFO 31296 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT id,order_no,user_id,amount FROM t_order WHERE id=?
    //2025-03-03 20:52:06.363  INFO 31296 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
    //2025-03-03 20:52:06.363  INFO 31296 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order ::: SELECT id,order_no,user_id,amount FROM t_order WHERE id=?  ::: [1]

}
