package com.jiangwei.config.securityConfig;

import com.jiangwei.dao.security.UserMapper;
import com.jiangwei.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * describe: 自定义userdetailsService
 * 灵活验证登录信息
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/28
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;


    /**
     * /重写loadUserByUsername 方法获得 userdetails 类型用户
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByName(s);
        if (user != null) {
            return new MyUserDetails(user);

        } else {
            throw new UsernameNotFoundException("用户名不存在");
        }
    }
}
