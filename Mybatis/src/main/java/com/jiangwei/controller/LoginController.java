package com.jiangwei.controller;//package com.jiangwei.controller;
//
//import com.jiangwei.entity.Role;
//import com.jiangwei.entity.User;
//import com.jiangwei.service.impl.RoleServiceImpl;
//import com.jiangwei.service.impl.UserService;
//import com.jiangwei.service.jedis.impl.JedisClientPool;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//@RestController("/sys")
//public class LoginController {
//
////    @Autowired
//    UserService userService ;
//
//    @Autowired
//    RoleServiceImpl roleService ;
//
//
//
//
//    /**
//     * 跳转到登录页面
//     * @param request
//     * @return
//     */
//    @RequestMapping("/toLogin")
//    public String toLogin(HttpServletRequest request){
//        return "page/login";
//    }
//
//    //用户退出
//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
//        String currentUser = (String)session.getAttribute("currentUser");
//        System.out.println("用户[" + currentUser + "]准备登出");
//        SecurityUtils.getSubject().logout();
//        System.out.println("用户[" + currentUser + "]已登出");
//        return "请重新登录";
//
//
//    }
//
//    /**
//     * 验证登录：
//     * @param request
//     * @param response
//     * @param userName
//     * @param
//     * @return
//     */
//    @RequestMapping("/Login")
//    public String Login(HttpServletRequest request, HttpServletResponse response, String userName, String passWord){
//        UsernamePasswordToken token=new UsernamePasswordToken();
//        token.setRememberMe(true);
//        //获取当前的Subject
//        Subject currentUser = SecurityUtils.getSubject();
//        try {
//            currentUser.login(token);
//            System.out.println("对用户[" + token.getUsername() + "]进行登录验证...验证通过");
//        }catch(Exception e){
//            //这里细分，大概有五种异常，有兴趣可以点击文章最后的链接去看看。
//            e.printStackTrace();
//            System.out.println("用户名或密码不正确");
//            request.setAttribute("message_login", "用户名或密码不正确");
//            return InternalResourceViewResolver.FORWARD_URL_PREFIX +"/sys/toLogin";
//        }
//        //验证是否登录成功，这里的isAuthenticated()方法有时候不怎么灵通，具体我也不知道啥原因，欢迎小伙伴找我探讨~
//        if(currentUser.isAuthenticated()){
//            User au=userService.selectByName(userName);
//            User sessionAu= (User) request.getSession().getAttribute("adminUser");
//            if(au == null && sessionAu == null){
//                System.out.println(111);
//                return InternalResourceViewResolver.FORWARD_URL_PREFIX +"/sys/toLogin";
//            }
//            request.setAttribute("adminUser", au);
//            HttpSession session = request.getSession();
//            session.setAttribute("adminUser", au);
//            session.setAttribute("token", token);
//            List<Role> pList=roleService.getRole(au.getId());
//            request.setAttribute("list", pList);
//            return "forward:/sys/theHome";
//        }else{
//            System.out.println("未通过！");
//            token.clear();
//            return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/sys/toLogin";
//        }
//    }
//
//}