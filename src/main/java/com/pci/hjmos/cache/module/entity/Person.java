package com.pci.hjmos.cache.module.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Person implements Serializable {
    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "姓名：" + name + "  年龄：" + age;
    }

    // 注意这里
    //private transient String name;
    private  String name;
    private int age;
}