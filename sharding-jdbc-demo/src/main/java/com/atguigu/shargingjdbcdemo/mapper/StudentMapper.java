package com.atguigu.shargingjdbcdemo.mapper;

import com.atguigu.shargingjdbcdemo.entity.Order;
import com.atguigu.shargingjdbcdemo.entity.OrderVo;
import com.atguigu.shargingjdbcdemo.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select({"SELECT id,name,age,create_time as createTime,update_time as updateTime FROM student"})
    List<Student> getList();
}
