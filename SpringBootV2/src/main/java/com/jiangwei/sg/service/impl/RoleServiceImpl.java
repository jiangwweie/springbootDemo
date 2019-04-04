package com.jiangwei.sg.service.impl;

import com.jiangwei.sg.entity.SysRole;
import com.jiangwei.sg.mapper.SysRoleMapper;
import com.jiangwei.sg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    SysRoleMapper roleMapper ;

    @Override
    public SysRole selectByPrimaryKey(int rid) {
        return roleMapper.selectByPrimaryKey(rid);
    }
}
