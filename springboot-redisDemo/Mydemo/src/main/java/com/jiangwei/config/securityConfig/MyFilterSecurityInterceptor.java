///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.jiangwei.config.securityConfig;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.InterceptorStatusToken;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.stereotype.Service;
//
///**
// * 加载资源对应的权限bean
// * 授权provider bean
// *
// * @author lalio
// * @email jiangwei.wh@outlook.com
// * @date 2018/11/02
// */
//@Service
//public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {
//
//    // ~ Static fields/initializers
//    // =====================================================================================
//    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
//
//    // ~ Instance fields
//    // ================================================================================================
////    private final FilterInvocationSecurityMetadataSource securityMetadataSource = new MyInvocationSecurityMetadataSource();
//    @Autowired
//    private  FilterInvocationSecurityMetadataSource securityMetadataSource ;
//
//    private final MyAccessDecisionManager aDecisionManager = new MyAccessDecisionManager();
//
//
//    private boolean observeOncePerRequest = true;
//
//    public MyFilterSecurityInterceptor() {
//        super.setAccessDecisionManager(aDecisionManager);
////        super.setSecurityMetadataSource(securityMetadataSource);
//        super.setRejectPublicInvocations(true);
//    }
//
//    // ~ Methods
//    // ========================================================================================================
//    /**
//     * Not used (we rely on IoC container lifecycle services instead)
//     *
//     * @param arg0 ignored
//     *
//     * @throws ServletException never thrown
//     */
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//        super.init(arg0);
//    }
//
//    /**
//     * Not used (we rely on IoC container lifecycle services instead)
//     */
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//
//    /**
//     * Method that is actually called by the filter chain. Simply delegates to
//     * the {@link #invoke(FilterInvocation)} method.
//     *
//     * @param request the servlet request
//     * @param response the servlet response
//     * @param chain the filter chain
//     *
//     * @throws IOException if the filter chain fails
//     * @throws ServletException if the filter chain fails
//     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
//        FilterInvocation fi = new FilterInvocation(request, response, chain);
//        invoke(fi);
//    }
//
//    @Override
//    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
//        return this.securityMetadataSource;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        return this.securityMetadataSource;
//    }
//
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return FilterInvocation.class;
//    }
//
//    @Override
//    public void invoke(FilterInvocation fi) throws IOException, ServletException {
//        if ((fi.getRequest() != null)
//                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
//                && observeOncePerRequest) {
//            // filter already applied to this request and user wants us to observe
//            // once-per-request handling, so don't re-do security checking
//            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//        } else {
//            // first time this request being called, so perform security checking
//            if (fi.getRequest() != null) {
//                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
//            }
//
//            //如果没有权限则生成匿名权限
//            if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                AnonymousAuthenticationToken auth = AnonymousAuthToken();
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//
//            InterceptorStatusToken token = super.beforeInvocation(fi);
//
//            try {
//                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
//            } finally {
//                super.finallyInvocation(token);
//            }
//
//            super.afterInvocation(token, null);
//        }
//    }
//
//    /**
//     * Indicates whether once-per-request handling will be observed. By default
//     * this is <code>true</code>, meaning the
//     * <code>FilterSecurityInterceptor</code> will only execute
//     * once-per-request. Sometimes users may wish it to execute more than once
//     * per request, such as when JSP forwards are being used and filter security
//     * is desired on each included fragment of the HTTP request.
//     *
//     * @return <code>true</code> (the default) if once-per-request is honoured,
//     * otherwise <code>false</code> if <code>FilterSecurityInterceptor</code>
//     * will enforce authorizations for each and every fragment of the HTTP
//     * request.
//     */
//    @Override
//    public boolean isObserveOncePerRequest() {
//        return observeOncePerRequest;
//    }
//
//    @Override
//    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
//        this.observeOncePerRequest = observeOncePerRequest;
//    }
//
//    @Override
//    public AccessDecisionManager getAccessDecisionManager() {
//        return aDecisionManager;
//    }
//
//    @Override
//    public void setAccessDecisionManager(AccessDecisionManager manager) {
//        super.setAccessDecisionManager(aDecisionManager);
//    }
//
//    public AnonymousAuthenticationToken AnonymousAuthToken() {
//        Object anonymousePrincipal = "anonymousUser";
//        List<GrantedAuthority> anonymouseAuthorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
//        AnonymousAuthenticationToken anonymouseAuth = new AnonymousAuthenticationToken(UUID.randomUUID().toString(), anonymousePrincipal, anonymouseAuthorities);
//        return anonymouseAuth;
//    }
//
//}
