package com.jiangwei.sg.controller;


import com.alibaba.fastjson.JSONObject;
import com.jiangwei.sg.util.Const;
import com.jiangwei.sg.util.HttpUtils;
import com.jiangwei.sg.util.ResultModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping("hello")
    public String sayHi(){
        System.out.println("hello world");
        return "hello";
    }

    @RequestMapping(value = "test1",method = RequestMethod.POST)
    public String test(@RequestParam("ids[]") int[] ids, int is_valid){
        System.out.println(is_valid);

        for (int id : ids){
            System.out.println(id);
        }
        return  "ok";

    }


    @RequestMapping(value = "gitOauth", method = RequestMethod.POST)
    public ResultModel oauth(HttpServletRequest request) throws Exception {
        JSONObject inparams = HttpUtils.getParams(request);
        String code = inparams.getString("code");
        String state = inparams.getString("state");
        System.out.println("code-----------" + code);
        System.out.println("state-----------" + state);
//        ResultModel result = new ResultModel(true, "登录成功");
//        //github账号登录
//        if ("github".equals(state)) {
//            String me = HttpUtils.doGet
//                    ("https://github.com/login/oauth/access_token?client_id=" + Const.client_id + "&client_secret=" + Const.client_secret + "&code=" + code + "&redirect_uri=" + Const.redirect_uri);
//
//            String atoke = me.split("&")[0];
//            System.out.println("atoke----------------" + atoke);
//            String res = HttpUtils.doGet("https://api.github.com/user?" + atoke + "");
//            JSONObject user = (JSONObject) JSON.parse(res);
//            System.out.println("获取到用户信息" + user.toJSONString());
//            String gitName = user.getString("login");
//            User user1 = userService.selectByGitName(gitName);
//            if (user1 != null) {
//                Map map1 = new HashMap<>();
//                map1.put("user_info", user1);
//                return new Result("1", "登陆成功", map1);
//            } else {
//                User user2 = new User(gitName);
//                userService.insertSelective(user2);
//                HashMap<Object, Object> map = new HashMap<>();
//                User suser = userService.selectByGitName(gitName);
//                System.out.println(suser.toString());
//                map.put("user_info", suser);
//                result.setResultMap(map);
//                return result;
//            }
//            //微博账号登录
//        } else if ("weibo".equals(state)) {
//            String url = "https://api.weibo.com/oauth2/access_token";
//            //通过code获取accessToken
//            NameValuePair id = new BasicNameValuePair("client_id",ParamUtil.WEIBO_KEY);
//            NameValuePair secret = new BasicNameValuePair("client_secret",ParamUtil.WEIBO_SECRET);
//            NameValuePair uri = new BasicNameValuePair("redirect_uri",ParamUtil.WEIBO_URL);
//            NameValuePair c = new BasicNameValuePair("code",code);
//            NameValuePair t = new BasicNameValuePair("grant_type","authorization_code");
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            list.add(id);
//            list.add(secret);
//            list.add(uri);
//            list.add(c);
//            list.add(t);
//
//            String re = HttpUtils.doPost1( url,list);
//            JSONObject resultjo = JSONObject.parseObject(re);
//            //是否有错误码
//            Integer errorCode = resultjo.getInteger("error_code");
//            String error = resultjo.getString("error");
//            String errorMsg = resultjo.getString("error_description");
//            //有错误码则返回信息
//            if (errorCode != null && errorCode != 0)
//                return new Result(errorCode.toString(), error + (errorMsg == null ? "" : errorMsg));
//            //没有错误码则获取到了accesstoken
//            String accessToken = resultjo.getString("access_token");
//            String uid = resultjo.getString("uid");
//            // 通过accessToken换取用户信息
//            String userinfo = HttpUtils.doGet("https://api.weibo.com/2/users/show.json?access_token=" + accessToken+"&uid="+uid);
//            JSONObject userjo = JSONObject.parseObject(userinfo);
//            //是否有错误码
//            errorCode = userjo.getInteger("error_code");
//            error = userjo.getString("error");
//            errorMsg = userjo.getString("error_description");
//            //有错误码则返回信息
//            if (errorCode != null && errorCode != 0)
//                return new Result(errorCode.toString(), error + (errorMsg == null ? "" : errorMsg));
//            //没有错误码则获取到用户的信息
//            String nickname = userjo.getString("screen_name");
//            System.out.println("微博用户的昵称为:" + nickname);
//            User user1 = userService.selectByWeibo(nickname);
//            if (user1 != null) {
//                Map map1 = new HashMap<>();
//                map1.put("user_info", user1);
//                return new Result("1", "登陆成功", map1);
//            } else {
//                User user2 = new User();
//                user2.setAdd1(nickname);
//                userService.insertSelective(user2);
//
//                HashMap<Object, Object> map = new HashMap<>();
//                User suser = userService.selectByWeibo(nickname);
//                System.out.println(suser.toString());
//                map.put("user_info", suser);
//                result.setResultMap(map);
//                return result;
//
//            }
//        }
        return null;
    }
}
