package com.jiangwei.config.aspect.annotation;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

/**
 * 缓存注解
 *
 * @author liudajiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {


    String key();

    String fieldKey();

}