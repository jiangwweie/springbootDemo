package com.jiangwei.sg.config.shiro;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.jiangwei.sg.entity.SysUser;
import com.jiangwei.sg.service.impl.RoleServiceImpl;
import com.jiangwei.sg.service.impl.UserServiceImpl;
import com.jiangwei.sg.util.Const;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangwei
 * @Date ：  2019/3/28 20:30
 */
@Component
public class MyRealm extends AuthorizingRealm {


    private UserServiceImpl userService;

//    private UserToRoleService userToRoleService;

//    private MenuService menuService;

    private RoleServiceImpl roleService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(RoleServiceImpl.class);
        }
        //这里查询出了user信息，进而可以查询出该用户的菜单和操作权限，
        String userNo = JWTUtil.getUserNo(principals.toString());
        try {
             SysUser user = userService.getById(Integer.valueOf(userNo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //流程：redis查询菜单权限，有则添加至simpleAuthorizationInfo，没有则查询数据库，然后存入缓存
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        /*
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        //添加控制角色级别的权限
        Set<String> roleNameSet = new HashSet<>();
        roleNameSet.add(role.getRoleName());
        simpleAuthorizationInfo.addRoles(roleNameSet);
        */
        simpleAuthorizationInfo.addRole("user");
        ArrayList<String> pers = new ArrayList<>();

        //这里根据业务逻辑添加权限，写死做一个展示
        //        List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        //        for (Menu per : menuList) {
        //             if (!ComUtil.isEmpty(per.getCode())) {
        //                  pers.add(String.valueOf(per.getCode()));
        //              }
        //        }
        pers.add("menu:read");
        pers.add("menu:del");
        pers.add("menu:update");
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }


    /**
     * Subject.login会使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws InvalidClaimException, TokenExpiredException {
        if (userService == null) {
            this.userService = SpringContextBeanService.getBean(UserServiceImpl.class);
        }
        String token = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }
        SysUser userBean = null;
        try {
            userBean = userService.getById(Integer.valueOf(userNo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        //校验token
        try {
            JWTUtil.verify(token, userNo, Const.PRIVATE_SECRET);
            System.out.println("mrealm success");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("算法编码不支持");
        }

        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
