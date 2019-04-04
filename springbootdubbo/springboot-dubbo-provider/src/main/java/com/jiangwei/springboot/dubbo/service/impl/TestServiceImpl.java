package com.jiangwei.springboot.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiangwei.entity.User;
import com.jiangwei.service.TestService;

@Service(version = "1.0.0")
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String str) {
        return "hello dubbo"+ System.currentTimeMillis();
    }

    @Override
    public User findUser() {
        return new User(1,"jiangwei","123455");
    }
}
