package com.jiangwei.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/02
 */
@RestController
@RequestMapping(value = "user")
public class UserController {


    @GetMapping("/userpage")
    public String httpApi() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "user下面的userpage";
    }


    @GetMapping("/adminpage")
    public String httpSuite() {
        return "user下面的adminpage";
    }


}

