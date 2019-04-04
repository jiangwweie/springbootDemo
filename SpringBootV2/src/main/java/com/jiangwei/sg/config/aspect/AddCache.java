package com.jiangwei.sg.config.aspect;


import java.lang.annotation.*;


/**
 * Created by mark on 16/11/29.
 * @usage  缓存注解类
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AddCache {
        String key();
        String fieldKey() ;
        int expireTime() default 3600;



        Class type();

}
