package com.jiangwei.sg.service.impl;

import com.jiangwei.sg.entity.SysUser;
import com.jiangwei.sg.mapper.SysUserMapper;
import com.jiangwei.sg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
