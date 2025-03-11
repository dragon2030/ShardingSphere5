package com.atguigu.shargingjdbcdemo;

import com.atguigu.shargingjdbcdemo.entity.Order;
import com.atguigu.shargingjdbcdemo.entity.Student;
import com.atguigu.shargingjdbcdemo.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

//水平分库+水平分表
@SpringBootTest
public class HorizontalShardingTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;
    @Resource
    private StudentMapper studentMapper;


    /**
     * 水平分库+水平分表
     * 水平分片：分表插入数据测试
     */
    @Test
    public void testInsertOrderTableStrategy(){

        for (long i = 1; i < 5; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }



        for (long i = 5; i < 9; i++) {

            Order order = new Order();
            order.setOrderNo("ATGUIGU" + i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }
    //拿到notepad看很明显 前4条数据UserId=1都按照取模运算插入了server-order1 后四条数据UserId=2都按照取模运算插入了server-order0 数据都按照哈希运算 均匀分布在两个分表中
    //2025-03-04 19:07:35.215  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id, ?, ?,? )
    //2025-03-04 19:07:35.215  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.215  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order1  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880191449792514, ATGUIGU1, 1, 100]
    //2025-03-04 19:07:35.243  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.243  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.243  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194012512257, ATGUIGU2, 1, 100]
    //2025-03-04 19:07:35.248  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.248  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.248  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order1  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194012512258, ATGUIGU3, 1, 100]
    //2025-03-04 19:07:35.252  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.252  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.253  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194012512259, ATGUIGU4, 1, 100]
    //2025-03-04 19:07:35.257  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.257  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.257  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order1  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194079621121, ATGUIGU5, 2, 100]
    //2025-03-04 19:07:35.266  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.266  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.266  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194079621122, ATGUIGU6, 2, 100]
    //2025-03-04 19:07:35.271  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.271  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.272  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order1  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194142535682, ATGUIGU7, 2, 100]
    //2025-03-04 19:07:35.276  INFO 32472 --- [main] ShardingSphere-SQL: Logic SQL: INSERT INTO t_order  ( id,order_no,user_id,amount )  VALUES  ( ?,?,?,? )
    //2025-03-04 19:07:35.276  INFO 32472 --- [main] ShardingSphere-SQL: SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
    //2025-03-04 19:07:35.276  INFO 32472 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: INSERT INTO t_order0  ( id,order_no,user_id,amount )  VALUES  (?, ?, ?, ?) ::: [1896880194142535683, ATGUIGU8, 2, 100]
    /**
     * 测试哈希取模
     */
    @Test
    public void testHash(){

        //注意hash取模的结果是整个字符串hash后再取模，和数值后缀是什么无关
        System.out.println("ATGUIGU001".hashCode() % 2);
        System.out.println("ATGUIGU0011".hashCode() % 2);
    }
//-1
//0

    /**
     * 水平分库+水平分表：查询所有记录
     * 查询了两个数据源，每个数据源中使用UNION ALL连接两个表
     */
    @Test
    public void testShardingSelectAll(){

        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }
//2025-03-04 19:33:28.058  INFO 26084 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,order_no,user_id,amount  FROM t_order
//2025-03-04 19:33:28.058  INFO 26084 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 19:33:28.058  INFO 26084 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT  id,order_no,user_id,amount  FROM t_order0 UNION ALL SELECT  id,order_no,user_id,amount  FROM t_order1
//2025-03-04 19:33:28.058  INFO 26084 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT  id,order_no,user_id,amount  FROM t_order0 UNION ALL SELECT  id,order_no,user_id,amount  FROM t_order1
//Order(id=1896556077791002625, orderNo=ATGUIGU001, userId=2, amount=100.00)
//Order(id=1896556077791002627, orderNo=ATGUIGU001, userId=4, amount=100.00)
//Order(id=1896880194079621122, orderNo=ATGUIGU6, userId=2, amount=100.00)
//Order(id=1896880194142535683, orderNo=ATGUIGU8, userId=2, amount=100.00)
//Order(id=1896880194079621121, orderNo=ATGUIGU5, userId=2, amount=100.00)
//Order(id=1896880194142535682, orderNo=ATGUIGU7, userId=2, amount=100.00)
//Order(id=1896556075098259457, orderNo=ATGUIGU001, userId=1, amount=100.00)
//Order(id=1896556077791002626, orderNo=ATGUIGU001, userId=3, amount=100.00)
//Order(id=1896880194012512257, orderNo=ATGUIGU2, userId=1, amount=100.00)
//Order(id=1896880194012512259, orderNo=ATGUIGU4, userId=1, amount=100.00)
//Order(id=1896880191449792514, orderNo=ATGUIGU1, userId=1, amount=100.00)
//Order(id=1896880194012512258, orderNo=ATGUIGU3, userId=1, amount=100.00)

    /**
     * 水平分库+水平分表：根据user_id查询记录
     * 查询了一个数据源，每个数据源中使用UNION ALL连接两个表
     * 当根据id进行查询时 从结果可见，没有访问order2库，因为是根据user_id做的水平分库，shardingSphere自动处理了。
     * 这就是用user_id做水平分库的原因，因为会经常用user_id查询数据，这样就可以吧同一个用户的数据都存到一个库中
     */
    @Test
    public void testShardingSelectByUserId(){

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", 1L);
        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        orders.forEach(System.out::println);
    }
//2025-03-04 19:35:18.235  INFO 38476 --- [main] ShardingSphere-SQL: Logic SQL: SELECT  id,order_no,user_id,amount  FROM t_order WHERE (user_id = ?)
//2025-03-04 19:35:18.235  INFO 38476 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-04 19:35:18.235  INFO 38476 --- [main] ShardingSphere-SQL: Actual SQL: server-order1 ::: SELECT  id,order_no,user_id,amount  FROM t_order0 WHERE (user_id = ?) UNION ALL SELECT id,order_no,user_id,amount FROM t_order1 WHERE (user_id = ?) ::: [1, 1]
//Order(id=1896556075098259457, orderNo=ATGUIGU001, userId=1, amount=100.00)
//Order(id=1896880194012512257, orderNo=ATGUIGU2, userId=1, amount=100.00)
//Order(id=1896880194012512259, orderNo=ATGUIGU4, userId=1, amount=100.00)
//Order(id=1896880191449792514, orderNo=ATGUIGU1, userId=1, amount=100.00)
//Order(id=1896880194012512258, orderNo=ATGUIGU3, userId=1, amount=100.00)


    //测试没有设置分片规则的表
    @Test
    public void testNoShardingTable(){
//        List<Student> list = studentMapper.getList();
//        System.out.println(list);

        for(int i=0;i<10;i++){
            List<Student> list = studentMapper.getList();
            System.out.println(list);
        }
    }
//2025-03-11 16:21:43.984  INFO 48840 --- [main] ShardingSphere-SQL: Logic SQL: SELECT id,name,age,create_time as createTime,update_time as updateTime FROM student
//2025-03-11 16:21:43.984  INFO 48840 --- [main] ShardingSphere-SQL: SQLStatement: MySQLSelectStatement(table=Optional.empty, limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
//2025-03-11 16:21:43.984  INFO 48840 --- [main] ShardingSphere-SQL: Actual SQL: server-order0 ::: SELECT id,name,age,create_time as createTime,update_time as updateTime FROM student
//[Student(id=1, name=Bob, age=22, createTime=Wed Mar 12 00:15:11 CST 2025, updateTime=null), Student(id=2, name=Sam, age=23, createTime=Wed Mar 12 00:15:21 CST 2025, updateTime=null)]

}
