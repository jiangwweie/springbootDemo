package com.jiangwei.sg.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;

/**
 * @author jiangwei
 * @Date ：  2019/3/28 20:30
 */
public class JWTToken implements AuthenticationToken , Serializable {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }


}
