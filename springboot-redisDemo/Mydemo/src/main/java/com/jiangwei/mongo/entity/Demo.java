package com.jiangwei.mongo.entity;

import org.springframework.data.annotation.Id;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/02
 */

public class Demo {
    @Id
    private String id;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}