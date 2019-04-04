package com.jiangwei.service.impl;

import com.jiangwei.dao.RoleMapper;
import com.jiangwei.entity.Role;
import com.jiangwei.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper roleMapper ;





    @Cacheable(value = "List<Role>",key = "'id'+#uid")
    @Override
    public List<Role> getRole(int uid) {
        List list = new ArrayList();
        Role role1 = new Role("r1");
        Role role2 = new Role("r2");
        Role role3 = new Role("r3");
        list.add(role1);
        list.add(role2);
        list.add(role3);
        System.out.println("select in db-_ ---- -- -  - - - --  - --  - -");
        return list;
    }
}
