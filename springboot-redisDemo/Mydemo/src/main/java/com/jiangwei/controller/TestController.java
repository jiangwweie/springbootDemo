package com.jiangwei.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangwei.dao.security.UserMapper;
import com.jiangwei.entity.MyPage;
import com.jiangwei.entity.Result;
import com.jiangwei.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/16
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    UserMapper userMapper ;


    /**
     * PathVariable  表示路径部分的参数  如  item/{itmeId}
     * RequestParam  表示？后面的参数    如  items?itemId=1
     */
    @RequestMapping("{pageNo}/{pageSize}")
    public Result test1(@PathVariable("pageNo") int pageno, @PathVariable("pageSize") int pageSize){
        Page page = PageHelper.startPage(2, 3);
        List<User> users = userMapper.findByPwd("123456");
        PageInfo info = new PageInfo<>(page.getResult());
        MyPage myPage = new MyPage(info);
        Result result = new Result(myPage);
        return  new Result(myPage) ;
    }

}
