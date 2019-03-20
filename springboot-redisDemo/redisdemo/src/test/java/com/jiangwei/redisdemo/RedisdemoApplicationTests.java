package com.jiangwei.redisdemo;

import com.jiangwei.redisdemo.dao.RedisUtil;
import com.jiangwei.redisdemo.entity.City;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisdemoApplicationTests {


    @Resource
    private RedisTemplate<String, Object> template;

    @Resource
    RedisUtil redisUtil;

    /**
     * a example for multiset of RedisTemplate
     */
    @Test
    public void multisetTest() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> ops = template.opsForValue();
        Map map = new HashMap();
        map.put("hubei", new City("wuhan", "武汉"));
        map.put("chongqin", new City("chongqin", "重庆"));
        map.put("sichuan", new City("sichuan", "成都"));
        ops.multiSet(map);
        City city = (City) ops.get("city");
        System.out.println(city.toString());
    }

    /**
     * example for list (lset ,lget)
     * lset ->  rightpush
     */
    @Test
    public void test() {
        boolean lSet1 = redisUtil.lSet("province", "hubei");
        System.out.println(lSet1);
        boolean lSet2 = redisUtil.lSet("province", "sichuan");
        System.out.println(lSet2);
        boolean lSet3 = redisUtil.lSet("province", "jiangxi");
        System.out.println(lSet3);
        List<Object> provinces = redisUtil.lGet("province", 0, -1);
        System.out.println(provinces.toString());
    }

    @Test
    public void test1() {
        long listSize = redisUtil.lGetListSize("province");
        System.out.println(listSize);
        long size2 = redisUtil.lGetListSize("hubei");
        System.out.println(size2);
    }

    @Test
    public void test2() {
        boolean hset = redisUtil.hset("couple", "jiangwei", "yingying");
        System.out.println(hset);
    }

    @Test
    public void test3() {
//        redisUtil.hset("provinces", "hubei", "wuhan");
//        redisUtil.hset("provinces", "jiangxi", "nanchang");
//        redisUtil.hset("provinces", "guangdong", "shenzhen");
//        redisUtil.hdel("provinces","guangdong");
        System.out.println(redisUtil.hasKey("provinces"));
        System.out.println(redisUtil.hmget("provinces"));
    }

    @Test
    public void test4() {
        long testSet = redisUtil.sSet("testSet", new String[]{"set1", "set2", "set3", "set4", "set5", "set6", "set7", "set8"});
        redisUtil.setRemove("testSet","set2");
        System.out.println(redisUtil.sGet("testSet"));
    }

    @Test
    public void test5() {
        System.out.println(redisUtil.randomRemove("testSet"));
        System.out.println(redisUtil.sGet("testSet"));
    }

    @Test
    public void test6() {
        System.out.println(redisUtil.a("testSet"));
        System.out.println(redisUtil.sGet("testSet"));
    }

}
