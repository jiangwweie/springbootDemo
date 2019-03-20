/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jiangwei.config.securityConfig;


import com.jiangwei.dao.security.RoleMapper;
import com.jiangwei.dao.security.UrlConfigMapper;
import com.jiangwei.entity.security.Role;
import com.jiangwei.entity.security.UrlConfig;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 检查用户是否够权限访问资源
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/02
 */
@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {



    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;


    /**
     * 不要使用自定义构造方法，否则导致注入会出错？
     * 应该是构造器中调用了loadResourceDefine方法，先于service注入，所以抛出异常。
     * 由spring调用
     * @param
     */
//    public MyInvocationSecurityMetadataSource(UrlConfigMapper urlConfigMapper,RoleMapper roleMapper){
//    this.urlConfigMapper =urlConfigMapper ;
//    this.roleMapper = roleMapper ;
//    loadResourceDefine();
//    }


    @Resource
    RoleMapper roleMapper;

    @Resource
    UrlConfigMapper urlConfigMapper;

    public MyInvocationSecurityMetadataSource() throws Exception {
        loadResourceDefine();
    }

    //tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
    private void loadResourceDefine() {
        resourceMap = new HashMap<>();
        try {
            //查询所有资源权限
            List<UrlConfig> configs = urlConfigMapper.findAll();
            Collection<ConfigAttribute> atts ;
            for (UrlConfig config :configs){
                //单个资源路径对应的角色要求
                String[] rids = config.getRoles().split(",");
                atts = new ArrayList<>();
                for (String rid:rids){
                    Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(rid));
                    ConfigAttribute ca = new SecurityConfig(role.getName());
                    atts.add(ca);
                }
                resourceMap.put(config.getUrl(),atts);
                System.out.println(resourceMap);
            }

//            resourceMap.put("/auth/login", genConfigAttr(new String[]{"ROLE_ANONYMOUS","ROLE_USER","ROLE_ADMIN"}));
//            resourceMap.put("/user/*", genConfigAttr(new String[]{"ROLE_USER","ROLE_ADMIN"}));//所有user的下面的路径
//            resourceMap.put("/admin/*", genConfigAttr(new String[]{"ROLE_ADMIN"}));//所有admin下面的路径
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null||resourceMap.size()==0) {
            loadResourceDefine();
        }
        // 将参数转为url    
        String url = ((FilterInvocation) object).getRequestUrl();
        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            RequestMatcher urlMatcher = new AntPathRequestMatcher(resURL);
            if (urlMatcher.matches(((FilterInvocation) object).getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    private Collection<ConfigAttribute> genConfigAttr(String[] roleArray) {
        if (roleArray == null || roleArray.length == 0) {
            return null;
        }
        Collection<ConfigAttribute> atts = new ArrayList<>();
        for (int i = 0; i <roleArray.length ; i++) {
            String role = (String) roleArray[i];
            ConfigAttribute ca = new SecurityConfig(role);
            atts.add(ca);
        }

        return atts;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
