package com.jiangwei.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiangwei.dao.redis.RedisUtil;
import com.jiangwei.entity.security.User;
import com.jiangwei.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class TestServiceImpl implements TestService {
   @Autowired
   RedisUtil redisUtil;


    @Override
    public String getUsername() {
        redisUtil.set("hi",new User("jiangwei","yingying"));
        String key =  redisUtil.get("hi").toString();
        System.out.println(key);
        return key;
    }
}
