package com.jiangwei.config.redis;

import com.alibaba.druid.util.StringUtils;
import com.jiangwei.util.StringCompress;
import org.springframework.cache.interceptor.KeyGenerator;
import sun.security.provider.MD5;

import java.lang.reflect.Method;
import java.security.AlgorithmParameterGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CacheKeyGenerator implements KeyGenerator {

    @Override

    public Object generate(Object target, Method method, Object... params) {

        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getName());

        key.append(".");

        key.append(method.getName());

        for (Object obj : params) {
            key.append(obj.toString());
        }
//        byte[] compress = StringCompress.compress(key.toString());
//        System.out.println("key-------------------------"+compress.toString());
        return key.toString();

    }

}