package com.jiangwei.controller;

import com.jiangwei.entity.TbUser;
import com.jiangwei.service.impl.TbUserServiceImpl;
import com.jiangwei.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("redis")
public class RedisTestController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TbUserServiceImpl tbUserService;


    @RequestMapping(value = "/select/{id}")
    public @ResponseBody
    String test1(@PathVariable long id) {
        TbUser tbUser = tbUserService.selectByPrimaryKey(id);
        return tbUser.toString();
    }

    @RequestMapping(value = "/delect/{id}")
    public @ResponseBody
    String test5(@PathVariable long id) {
        int rs = tbUserService.deleteByPrimaryKey(id);
        return "success:"+rs;
    }


    @RequestMapping(value = "/insert")
     @ResponseBody int test6(TbUser user){
       return tbUserService.insert(user);
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    Map test2(TbUser tbUser) {

        tbUser.setUsername("testAscpect");
        tbUserService.updateByPrimaryKeySelective(tbUser);
        Map map = new HashMap();
        map.put("success",tbUser);
        return map ;
    }

    @RequestMapping(value = "page/{start}/{end}")
    public @ResponseBody List<TbUser> test3(@PathVariable int start ,@PathVariable int end) {
        TbUser user = new TbUser();
        user.setStatus("1");
        List list = tbUserService.selectLimit(user, start, end);
        return list;
    }
}
