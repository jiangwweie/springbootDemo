package com.jiangwei.mongo.dao;

import com.jiangwei.mongo.entity.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DemoDao extends MongoRepository<Demo, String> {

    /**
     * 查询
     * @param name
     * @return
     */
    Demo findByName(String name );

    /**
     * 查询集合
     * @param age
     * @return
     */
    List<Demo> findByAge(int age);



}
