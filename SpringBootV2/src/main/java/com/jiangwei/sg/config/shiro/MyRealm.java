package com.jiangwei.sg.config.shiro;

import com.jiangwei.sg.entity.SysUser;
import com.jiangwei.sg.entity.UserToRole;
import com.jiangwei.sg.service.RoleService;
import com.jiangwei.sg.service.UserService;
import com.jiangwei.sg.service.UserToRoleService;
import com.jiangwei.sg.util.jwt.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liugh
 * @since 2018-05-03
 */
public class MyRealm extends AuthorizingRealm {
    private UserService userService;
    private UserToRoleService userToRoleService;
//    private MenuService menuService;
    private RoleService roleService;
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
        if (userToRoleService == null) {
            this.userToRoleService = SpringContextBeanService.getBean(UserToRoleService.class);
        }
//        if (menuService == null) {
//            this.menuService = SpringContextBeanService.getBean(MenuService.class);
//        }
        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(RoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());
        SysUser user = userService.getById(Integer.valueOf(userNo));
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUid());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        /*
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        //添加控制角色级别的权限
        Set<String> roleNameSet = new HashSet<>();
        roleNameSet.add(role.getRoleName());
        simpleAuthorizationInfo.addRoles(roleNameSet);
        */
        //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
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
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (userService == null) {
            this.userService = SpringContextBeanService.getBean(UserService.class);
        }
        String token = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new UnauthorizedException("token invalid");
        }
        SysUser userBean = userService.getUserByUname(userNo);
        if (userBean == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, userBean.getPassword())) {
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
