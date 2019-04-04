package com.jiangwei.sg.service.impl;

import com.jiangwei.sg.entity.UserToRole;
import com.jiangwei.sg.mapper.UserToRoleMapper;
import com.jiangwei.sg.service.UserToRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {
    @Autowired
    UserToRoleMapper userToRoleMapper ;

    @Override
    public UserToRole selectByUserNo(int uid) {
        return userToRoleMapper.selectRoleByUserNo(uid);
    }
}
