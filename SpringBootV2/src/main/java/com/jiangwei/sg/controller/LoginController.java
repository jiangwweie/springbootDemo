package com.jiangwei.sg.controller;

import com.jiangwei.sg.config.shiro.JWTUtil;
import com.jiangwei.sg.entity.*;
import com.jiangwei.sg.service.impl.RoleServiceImpl;
import com.jiangwei.sg.service.impl.UserServiceImpl;
import com.jiangwei.sg.service.impl.UserToRoleServiceImpl;
import com.jiangwei.sg.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 *  登录接口
 * @author liugh
 * @since 2018-05-03
 */
@RestController
public class LoginController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    UserToRoleServiceImpl userToRoleService ;

    @Autowired
    RoleServiceImpl roleService ;





    @PostMapping("/login")
    public ResponseBean login(@Valid LoginDTO loginDTO , HttpServletResponse httpResponse){
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = userService.getUserByUname(loginDTO.getUsername());
        UserToRole userToRole = userToRoleService.selectByUserNo(sysUser.getUid());
        SysRole role = roleService.selectByPrimaryKey(Integer.valueOf(userToRole.getRid()));
        //将token写入response的头部
        String token = JWTUtil.sign(sysUser.getUsername(), sysUser.getPassword());
        httpResponse.setHeader(Const.AUTHORIZATION_HEADER,token);
        sysUser.setToken(token);
        result.put("user",sysUser);
        result.put("role",role);
        return new ResponseBean().success(result,"success");
    }


}
