package com.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jiangwei.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/23
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private TestService userService;

    @RequestMapping("name")
    @ResponseBody
    public String showName(){
      return  userService.getUsername();
    }
}
