package com.jiangwei.sg.config.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jiangwei.sg.util.Const;
import com.jiangwei.sg.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.jiangwei.sg.util.Const.EXPIRE_TIME;
import static com.jiangwei.sg.util.Const.USER_ID;


/**
 * @Author ：  jiangwei
 * @Date ：  2019/3/28 18:23
 * @Desc ：
 */
@Component
public class JWTUtil {

    private static RedisUtil redisUtil;

    /**
     * 校验token是否正确
     *
     * @param token
     * @param user_id
     * @param secret
     * @return true/false
     * @throws UnsupportedEncodingException
     */
    public static boolean verify(String token, String user_id, String secret) throws InvalidClaimException, TokenExpiredException, UnsupportedEncodingException {
//        if (redisUtil == null) {
//            redisUtil = SpringContextBeanService.getBean(RedisUtil.class);
//        }
//        //从redis查询token表中uid对应的token是否存在
        boolean exists = false;
//        try {
//            exists = redisUtil.hHasKey(Const.JWT_BEAR_TOKEN, user_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }

        //否则
        JWTVerifier verifier = null;
        verifier = JWT.require(Algorithm.HMAC256(secret))
                .withClaim(USER_ID, user_id)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        exists = true;
        System.out.println("Jwt verify ok!");
        return exists;

    }


    /**
     * 生成签名，指定时间后过期
     *
     * @param user_id
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String sign(String user_id, String secret) {
        if (redisUtil == null) {
            redisUtil = SpringContextBeanService.getBean(RedisUtil.class);
        }
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            System.out.println("expire time at :" + date);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String sign = JWT.create()
                    .withClaim(USER_ID, user_id)
                    .withExpiresAt(date)
                    .sign(algorithm);
            new Thread(() -> {
                boolean hset = redisUtil.hset(Const.JWT_BEAR_TOKEN, user_id, sign, 2 * EXPIRE_TIME / 1000);
                System.out.println("new Thread start to cache token of "+user_id+"  ,  save result  success ?:"+hset);
            }).start();
            return sign;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getUserNo(String bearToken) {
        //String token = bearToken.substring(7, bearToken.length());
        DecodedJWT jwt = JWT.decode(bearToken);
        String payload = jwt.getPayload();
        System.out.println("payload of token ::::::::::" + payload.toString());
        return jwt.getClaim(USER_ID).asString();
    }


    /**
     * 获取实体中对应的信息
     * @param bearToken
     * @param payLoadKey，such as 'user_id'  , 'expire'
     * @return
     */
    public static String getClaim(String bearToken, String payLoadKey) {
        //String token = bearToken.substring(7, bearToken.length());
        DecodedJWT jwt = JWT.decode(bearToken);
        String payload = jwt.getPayload();
        System.out.println("payload of token ::::::::::" + payload.toString());
        return jwt.getClaim(payLoadKey).asString();
    }


    /**
     * 拿到token（header/parameter/cookie.......中）
     *
     * @param request
     * @return
     */
    public static String resolveToken(HttpServletRequest request) {
        //头部获取
        String headerToken = request.getHeader(Const.AUTHORIZATION_HEADER);
        if (!StringUtils.isEmpty(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7, headerToken.length());
        }
        //请求参数获取
        String jwt = request.getParameter(Const.AUTHORIZATION_HEADER);
        if (!StringUtils.isEmpty(headerToken) && headerToken.startsWith("Bearer ")) {
            return jwt.substring(7, headerToken.length());
        }

        return null;
    }
}
