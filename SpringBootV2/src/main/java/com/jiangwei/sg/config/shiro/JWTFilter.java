package com.jiangwei.sg.config.shiro;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jiangwei.sg.util.Const;
import com.jiangwei.sg.util.RedisUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jw
 * @Date ：  2019/3/28 20:30
 * <p>
 * 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin->MyRealm
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private static RedisUtil redisUtil;

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        if (httpServletRequest.getRequestURI().contains("login")) {
            super.preHandle(request, response);
        }
        //获取到token
        String authorization = JWTUtil.resolveToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(authorization)) {
            responseError(request, response);
            return false;
        }
        return super.preHandle(request, response);
    }


    /**
     * 检测权限
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                //如果JWT验证抛出过期
                if (throwable != null && throwable instanceof TokenExpiredException) {
                    if (this.refresh(request, response)) {
                        return true;
                    }else {
                        //token已经超过可刷新时间了
                        responseError(request, response);
                        return false;
                    }
                }else{
                    //token异常
                    responseError(request, response);
                    return false;
                }

            }
        }
        return true;
    }


    public boolean refresh(ServletRequest request, ServletResponse response) {
        if (redisUtil == null) {
            redisUtil = SpringContextBeanService.getBean(RedisUtil.class);
        }
        String authorization = JWTUtil.resolveToken((HttpServletRequest) request);
        String uid = JWTUtil.getUserNo(authorization);
        //如果缓存中存在，那么刷新token
        boolean has = redisUtil.hHasKey("bear_token", uid);

        if (has) {
            authorization = JWTUtil.sign(uid, Const.PRIVATE_SECRET);
            System.out.println("gen new JWT.......");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("Access-Control-Allow-Headers", Const.AUTHORIZATION_HEADER);
            httpServletResponse.setHeader(Const.AUTHORIZATION_HEADER, "Bearer " + authorization);
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authorization = JWTUtil.resolveToken((HttpServletRequest) request);
        return authorization != null;
    }

    /**
     * 如果带有Authorization，那么执行shiro的login方法，token提交到realm中校验
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String authorization = JWTUtil.resolveToken((HttpServletRequest) request);
        JWTToken token = new JWTToken(authorization);
        // login会提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // REALM执行完成代表登入成功，返回true，接下来进入controller执行
//        String user_id = JWTUtil.getUserNo(authorization);
        return true;
    }


    /**
     * 非法url返回身份错误信息
     */
    public void responseError(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            response.setContentType("application/json; charset=utf-8");
            Map map = new HashMap();
            map.put("state",403);
            map.put("data","访问受限，请重新登录获取凭证");
            out.print(map.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
