package com.jiangwei.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/28
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //header里面的key
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_TOKEN = "access_token";


    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;


    @Bean
    UserDetailsService customUserService() { //注册UserDetailsService 的bean
        return new MyUserDetailsService();
    }

    /**
     * security5.x  需要对密码加密
     * https://blog.csdn.net/SWPU_Lipan/article/details/80586054?utm_source=blogxgwz0
     *
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public GenericFilterBean genericFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public FilterSecurityInterceptor myFilterSecurityInterceptor()
            throws Exception {
        return new MyFilterSecurityInterceptor();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService()) //自定义获取用户信息
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); //user Details Service验证  重写
        /*使用自定义的authenticationProvider认证逻辑*/
        auth.authenticationProvider(myAuthenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {//配置请求访问策略
        http
                //关闭CSRF、CORS
                .cors().disable()
                .csrf().disable()
                //由于使用Token，所以不需要Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //验证Http请求
                .authorizeRequests()
                //允许所有用户访问首页 与 登录
                .antMatchers("/", "/auth/login").permitAll()
                //其它任何请求都要经过认证通过
                .anyRequest().authenticated()
                .and()
                //设置登出
                .logout().permitAll();
        //添加JWT filter 在
        http.addFilterBefore(genericFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(myFilterSecurityInterceptor());
    }


}
