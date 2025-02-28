package com.atguigu.shargingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_user")//逻辑表
@Data
/**
 * @TableName
 * 表名自动映射
 * 如果你不使用 @TableName，MyBatis-Plus 默认会将实体类名（类名的小写形式）作为表名。这是基于 Java 类名到数据库表名的一种约定。比如，如果你有一个 User 类，默认情况下，MyBatis-Plus 会将其映射到 user 表。
 */
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String uname;
}
