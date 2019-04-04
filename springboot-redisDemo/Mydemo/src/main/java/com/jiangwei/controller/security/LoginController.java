package com.jiangwei.controller.security;

import com.jiangwei.dao.redis.RedisUtil;
import com.jiangwei.dao.security.UserMapper;
import com.jiangwei.entity.security.User;
import com.jiangwei.util.CookiesUtil;
import com.jiangwei.util.JWTTokenUtils;
import com.jiangwei.entity.security.LoginDTO;
import com.jiangwei.config.securityConfig.MyAuthenticationProvider;
import com.jiangwei.config.securityConfig.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/02
 */
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public String login(@Valid LoginDTO loginDTO,  HttpServletResponse httpResponse) {
        //通过用户名和密码创建一个 Authentication 认证对象，实现类为 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        User user  = null ;
         //如果认证对象不为空
        if (Objects.nonNull(authenticationToken)) {
            user = userMapper.selectByName(authenticationToken.getPrincipal().toString());
            if (user == null) {
                return "该用户不存在";
            }
        }

        //通过 AuthenticationManager（默认实现为ProviderManager）的authenticate方法验证 Authentication 对象
        Authentication authentication = myAuthenticationProvider.authenticate(authenticationToken);
        //将 Authentication 绑定到 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成Token
        String token = jwtTokenUtils.createToken(authentication, false);
        //将Token写入到Http头部
        httpResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER, "Bearer " + token);
        //测试 添加token到cookie中,cookie中不能有空格，逗号之类的
        CookiesUtil.setCookie(httpResponse,WebSecurityConfig.AUTHORIZATION_HEADER, URLEncoder.encode("Bearer " + token),120000);
        try {
            redisUtil.hset(WebSecurityConfig.AUTHORIZATION_HEADER,user.getId().toString(),"Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Bearer " + token;

    }
}
