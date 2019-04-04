package com.jiangwei.sg.controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @RequestMapping("hello")
    public String sayHi(){
        System.out.println("hello world");
        return "hello";
    }

    @RequestMapping(value = "test1",method = RequestMethod.POST)
    public String test(@RequestParam("ids[]") int[] ids, int is_valid){
        System.out.println(is_valid);

        for (int id : ids){
            System.out.println(id);
        }
        return  "ok";

    }
}
