package com.jiangwei.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiangwei.dao.redis.RedisDao;
import com.jiangwei.entity.security.User;
import com.jiangwei.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class TestServiceImpl implements TestService {
   @Autowired
   RedisDao redisDao ;


    @Override
    public String getUsername() {
        redisDao.set("hi",new User("jiangwei","yingying"));
        String key =  redisDao.get("hi").toString();
        System.out.println(key);
        return key;
    }
}
