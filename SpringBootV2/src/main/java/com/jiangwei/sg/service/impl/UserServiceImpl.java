package com.jiangwei.sg.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jiangwei.sg.entity.SysUser;
import com.jiangwei.sg.mapper.SysUserMapper;
import com.jiangwei.sg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Service(timeout = 10000,interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    SysUserMapper userMapper ;

    @Override
    public SysUser getUserByUname(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public SysUser getById(int uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
