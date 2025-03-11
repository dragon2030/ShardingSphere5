package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.Order;
import com.atguigu.shargingjdbcdemo.entity.OrderItem;
import com.atguigu.shargingjdbcdemo.entity.OrderVo;
import com.atguigu.shargingjdbcdemo.mapper.DictMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderItemMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderMapper;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

//多表关联
@SpringBootTest
public class MultipleTableAssociationTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;
    /**
     * 测试关联表的插入
     * 从结果可以明显的看出：因为关联的两张表配置的 分库键 分表键 都一样，所以同类型订单的数据都被插入了同一个库的同类型的订单详情表，例如->t_order1 ->t_order_item1 ->t_order_item1
     */
    @Test
    public void testInsertOrderAndOrderItem(){

        for (long i = 1; i < 3; i++) {

            Order order = new Order();
            String orderNo = "ATGUIGU" + i;
            Long userId = 1L;
            order.setOrderNo(orderNo);
            order.setUserId(userId);
            orderMapper.insert(order);
            //为每一个订单插入多个订单详情
            for (int j = 1; j < 3; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo(orderNo);
                orderItem.setUserId(userId);
                orderItem.setPrice(new BigDecimal(10));
                orderItem.setCount(2);
                orderItemMapper.insert(orderItem);
            }
        }

        for (long i = 3; i < 5; i++) {

            Order order = new Order();
            String orderNo = "ATGUIGU" + i;
            Long userId = 2L;
            order.setOrderNo(orderNo);
            order.setUserId(userId);
            orderMapper.insert(order);

            for (int j = 3; j < 5; j++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo(orderNo);
                orderItem.setUserId(userId);
                orderItem.setPrice(new BigDecimal(3));
                orderItem.setCount(3);
                orderItemMapper.insert(orderItem);
            }
        }
    }
//2025-03-04 20:13:44.101  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id )  VALUES  ( ?,?,? )
//2025-03-04 20:13:44.101  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.101  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order1  ( id,order_no,user_id )  VALUES  (?, ?, ?) ::: [1896896838122266625, ATGUIGU1, 1]
//2025-03-04 20:13:44.133  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.133  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.133  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order_item1  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU1, 1, 10, 2, 1103776497295949824]
//2025-03-04 20:13:44.142  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.142  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.142  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order_item1  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU1, 1, 10, 2, 1103776497342087169]
//2025-03-04 20:13:44.146  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id )  VALUES  ( ?,?,? )
//2025-03-04 20:13:44.146  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.146  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order0  ( id,order_no,user_id )  VALUES  (?, ?, ?) ::: [1896896840794038273, ATGUIGU2, 1]
//2025-03-04 20:13:44.150  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.150  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.150  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order_item0  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU2, 1, 10, 2, 1103776497371447296]
//2025-03-04 20:13:44.154  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.154  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.154  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order_item0  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU2, 1, 10, 2, 1103776497388224513]
//2025-03-04 20:13:44.158  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id )  VALUES  ( ?,?,? )
//2025-03-04 20:13:44.158  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.158  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order1  ( id,order_no,user_id )  VALUES  (?, ?, ?) ::: [1896896840861147137, ATGUIGU3, 2]
//2025-03-04 20:13:44.163  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.163  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.163  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order_item1  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU3, 2, 3, 3, 1103776497430167552]
//2025-03-04 20:13:44.168  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.168  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.168  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order_item1  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU3, 2, 3, 3, 1103776497451139073]
//2025-03-04 20:13:44.173  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id )  VALUES  ( ?,?,? )
//2025-03-04 20:13:44.173  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.173  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order0  ( id,order_no,user_id )  VALUES  (?, ?, ?) ::: [1896896840861147138, ATGUIGU4, 2]
//2025-03-04 20:13:44.177  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.177  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.177  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order_item0  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU4, 2, 3, 3, 1103776497484693504]
//2025-03-04 20:13:44.181  INFO 33680 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order_item  ( order_no,user_id,price,count )  VALUES  ( ?,?,?,? )
//2025-03-04 20:13:44.181  INFO 33680 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
//2025-03-04 20:13:44.181  INFO 33680 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order_item0  ( order_no,user_id,price,count , id)  VALUES  (?, ?, ?, ?, ?) ::: [ATGUIGU4, 2, 3, 3, 1103776497501470721]


    /**
     * 测试关联表查询(绑定表前)
     * 如果不配置绑定表：测试的结果为8个SQL，多表关联查询会出现笛卡尔积关联
     */
    @Test
    public void testGetOrderAmount(){
        List<OrderVo> orderAmountList = orderMapper.getOrderAmount();
        orderAmountList.forEach(System.out::println);
    }
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Logic SQL: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order o JOIN t_order_item i ON o.order_no = i.order_no GROUP BY o.order_no
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:23:49.236  INFO 38824 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//OrderVo(orderNo=ATGUIGU1, amount=80.00)
//OrderVo(orderNo=ATGUIGU2, amount=80.00)
//OrderVo(orderNo=ATGUIGU3, amount=18.00)
//OrderVo(orderNo=ATGUIGU4, amount=18.00)
}
