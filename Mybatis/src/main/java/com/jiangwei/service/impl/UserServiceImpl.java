package com.jiangwei.service.impl;

import com.jiangwei.dao.UserMapper;
import com.jiangwei.entity.User;
import com.jiangwei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017\11\7 0007.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByName(String username) {
        return userMapper.selectByName(username);
    }


}
