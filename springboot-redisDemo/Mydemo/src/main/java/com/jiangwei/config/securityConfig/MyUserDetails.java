package com.jiangwei.config.securityConfig;

import com.jiangwei.entity.security.Role;
import com.jiangwei.entity.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * describe:自定义userdetails
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/02
 */
public class MyUserDetails implements UserDetails {

//    Principal相当于用户名
//    credentials相当于密码
    private User user;

    public MyUserDetails(User user) {

        this.user = user;
    }

    /**
     * @return authentication中对应的权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles.size()>=1){
            for (Role role : roles){
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authorities;
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList("");

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
