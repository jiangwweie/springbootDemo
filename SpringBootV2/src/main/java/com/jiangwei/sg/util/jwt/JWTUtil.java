package com.jiangwei.sg.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author liugh
 * @since 2018-05-07
 */
public class JWTUtil {

    //header里面的key
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 过期时间1天
    private static final long EXPIRE_TIME = 2 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userNo, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userNo", userNo)
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException exception1) {
            return false;
        } catch (TokenExpiredException exception2) {
            System.out.println("Expired JWT token ");
            return  false ;
        } catch (InvalidClaimException exception3){
            System.out.println("无效的claim");
            return false ;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserNo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userNo").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,指定时间后过期,一经生成不可修改，令牌在指定时间内一直有效
     *
     * @param userNo 用户编号
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String userNo, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    //Claim是一些实体（通常指的用户）的状态和额外的元数据,此处可以有多个claim
                    //.withClaim("uid",123456)
                    .withClaim("userNo", userNo)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
