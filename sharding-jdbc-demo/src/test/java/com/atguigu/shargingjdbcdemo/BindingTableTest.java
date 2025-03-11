package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.OrderVo;
import com.atguigu.shargingjdbcdemo.mapper.DictMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderItemMapper;
import com.atguigu.shargingjdbcdemo.mapper.OrderMapper;
import com.atguigu.shargingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//绑定表
@SpringBootTest
public class BindingTableTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    /**
     * 测试关联表查询(绑定表前)
     * - **如果不配置绑定表：测试的结果为8个SQL。**多表关联查询会出现笛卡尔积关联。
     * - **如果配置绑定表：测试的结果为4个SQL。** 多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升。
     */
    @Test
    public void testGetOrderAmount(){
        List<OrderVo> orderAmountList = orderMapper.getOrderAmount();
        orderAmountList.forEach(System.out::println);
    }
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: Logic SQL: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order o JOIN t_order_item i ON o.order_no = i.order_no GROUP BY o.order_no
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order0 o JOIN t_order_item0 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//2025-03-04 20:33:06.504  INFO 27744 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT o.order_no, SUM(i.price * i.count) AS amount FROM t_order1 o JOIN t_order_item1 i ON o.order_no = i.order_no GROUP BY o.order_no ORDER BY o.order_no ASC
//OrderVo(orderNo=ATGUIGU1, amount=80.00)
//OrderVo(orderNo=ATGUIGU2, amount=80.00)
//OrderVo(orderNo=ATGUIGU3, amount=18.00)
//OrderVo(orderNo=ATGUIGU4, amount=18.00)
}
