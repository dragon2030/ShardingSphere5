package com.atguigu.shargingjdbcdemo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Student {
    private int id;
    private String name;
    private int age;
    private Date createTime;
    private Date updateTime;

}
