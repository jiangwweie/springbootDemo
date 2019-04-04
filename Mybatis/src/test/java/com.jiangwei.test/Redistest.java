package com.jiangwei.test;


import com.jiangwei.entity.Role;
import com.jiangwei.entity.TbUser;
import com.jiangwei.entity.User;
import com.jiangwei.service.impl.RoleServiceImpl;
import com.jiangwei.service.impl.TbUserServiceImpl;
import com.jiangwei.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class Redistest {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TbUserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService ;

    /**
     * 简单string存取，字符串存储
     */
    @Test
    public void test1() {
        boolean set = redisUtil.set("result", "成功");
        System.out.println(set);
        Object myname = redisUtil.get("result");
        System.out.println(myname);
    }


    /**
     * 简单对象存储,hash存储
     */
    @Test
    public void test3() {
        Role myRole = new Role();
        myRole.setUid(1);
        myRole.setName("ROLE_ADMIN");
        myRole.setRid(1);
        boolean rs = redisUtil.hset("role", "role1", myRole);
        System.out.println("存储结果" + rs);

        Object getRole = redisUtil.hget("role", "role1");
        Role role = (Role) getRole;
        System.out.println("查询到的role：" + getRole.toString());
    }

    /**
     * 复杂对象存储，list存取,过期测试
     */
    @Test
    public void test4() {
        User user = new User(1, "jiangwei", "123456");
        Role role1 = new Role(1, 1, "USER_ADMIN");
        Role role2 = new Role(2, 1, "USER_ANON");
        Role role3 = new Role(3, 1, "USER_USER");
        List list = new ArrayList();
        list.add(role1);
        list.add(role2);
        list.add(role3);
        user.setRoles(list);

        boolean result = redisUtil.lSet("currentUser", user, 1200);
        System.out.println(result);
    }

    /**
     * 读取key为currentUser的list，然后取出下标0-10的User集合（List）
     */
    @Test
    public void test5() {
        long l = redisUtil.lGetListSize("currentUser");
        System.out.println(l);
        List<Object> currentUser = redisUtil.lGet("currentUser", 0, 10);
        User o = (User) currentUser.get(0);
        List<Role> roles = o.getRoles();
        for (Role role : roles) {
            System.out.println(role);
        }
    }


    @Test
    public void Test69() throws InterruptedException {
        TbUser tbUser = new TbUser();
        tbUser.setId(1l);
        TbUser user = userService.selectByPrimaryKey(tbUser);
        System.out.println(user);
        TbUser user2 = userService.selectByPrimaryKey(tbUser);
        System.out.println(user2);
    }

    @Test
    public void Test7() throws InterruptedException {
        TbUser user = userService.selectByPrimaryKey(5l);
        System.out.println(user);
        TbUser user2 = userService.selectByPrimaryKey(5l);
        System.out.println(user2);
    }


    @Test
    public void test7() {
        List<Role> role1 = roleService.getRole(5);
        System.out.println(role1.hashCode());
        List<Role> role2 = roleService.getRole(5);
        System.out.println(role2.hashCode());

    }

}
