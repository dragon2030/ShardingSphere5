package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.Order;
import com.atguigu.shargingjdbcdemo.mapper.DictMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderItemMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderMapper;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

//水平分库
@SpringBootTest
public class HorizontalPartitioningTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    /**
     * 水平分片：分库插入数据测试
     */
    @Test
    public void testInsertOrderDatabaseStrategy(){

        for (long i = 0; i < 4; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU001");
            order.setUserId(i + 1);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }
    //2025-03-03 21:39:39.885  INFO 26888 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-03 21:39:39.885  INFO 26888 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 21:39:39.885  INFO 26888 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order1 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896556075098259457, ATGUIGU001, 1, 100]
    //2025-03-03 21:39:39.911  INFO 26888 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-03 21:39:39.911  INFO 26888 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 21:39:39.911  INFO 26888 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order0 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896556077791002625, ATGUIGU001, 2, 100]
    //2025-03-03 21:39:39.915  INFO 26888 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-03 21:39:39.915  INFO 26888 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 21:39:39.915  INFO 26888 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order1 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896556077791002626, ATGUIGU001, 3, 100]
    //2025-03-03 21:39:39.919  INFO 26888 --- [           main] ShardingSphere-SQL                       : Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-03 21:39:39.919  INFO 26888 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-03 21:39:39.919  INFO 26888 --- [           main] ShardingSphere-SQL                       : Actual SQL: server-order0 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896556077791002627, ATGUIGU001, 4, 100]

    //sharding_sphere
//  INSERT INTO `sharding_sphere`.`t_order0`(`id`, `order_no`, `user_id`, `amount`) VALUES (1896556077791002625, 'ATGUIGU001', 2, 100.00);
//INSERT INTO `sharding_sphere`.`t_order0`(`id`, `order_no`, `user_id`, `amount`) VALUES (1896556077791002627, 'ATGUIGU001', 4, 100.00);
    //sharding_sphere1
    //INSERT INTO `sharding_sphere1`.`t_order0`(`id`, `order_no`, `user_id`, `amount`) VALUES (1896556075098259457, 'ATGUIGU001', 1, 100.00);
    //INSERT INTO `sharding_sphere1`.`t_order0`(`id`, `order_no`, `user_id`, `amount`) VALUES (1896556077791002626, 'ATGUIGU001', 3, 100.00);
}
