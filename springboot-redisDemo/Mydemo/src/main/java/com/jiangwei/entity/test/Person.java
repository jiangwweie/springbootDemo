package com.jiangwei.entity.test;

import org.springframework.data.annotation.Id;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/19
 */

public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
    private String sex;


    public Person(Long id,String name,int age,String sex){
        this.id = id;
        this.name = name ;
        this.age  = age  ;
        this.sex  = sex  ;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
