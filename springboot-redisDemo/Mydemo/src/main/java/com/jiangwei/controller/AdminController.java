package com.jiangwei.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "admin")
public class AdminController {


    @GetMapping("/user")
    public String httpApi() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "admin下面的userpage";
    }

    @GetMapping("/admin")
    public String httpSuite() {

        return "admin下面的adminpage";
    }


}

