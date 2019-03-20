package com.jiangwei.redisdemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/16
 */
@Repository
public class RedisDao {

    @Resource
    private RedisTemplate<String,Object> template;

    public  void setKey(String key,Object value){
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> ops = template.opsForValue();
        //1小时过期
        //ops.set(key,value,1, TimeUnit.HOURS);
        //偏移6个字符，覆盖原有字符串
        ops.set(key, value,6);
        ops.set(key, value);
    }

    public Object getValue(String key){
        //操作hash
        HashOperations<String, Object, Object> hops = this.template.opsForHash();
        //操作set
        SetOperations<String, Object> set = this.template.opsForSet();
        //操作有序set
        ZSetOperations<String, Object> zSet = this.template.opsForZSet();

        //操作String
        ValueOperations<String, Object> ops = this.template.opsForValue();
        return ops.get(key);
    }

    /**
     * @description 存储数据到列表中
     * @param listKey
     * @param value
     */
    public void leftPushList(String listKey , Object value ){
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
         ListOperations<String,Object>  list = this.template.opsForList();
         list.leftPush(listKey,value);
    }

    /**
     * @description 存储数据到列表中
     * @param listKey
     * @param value
     */
    public void rightPushList(String listKey , Object value ){
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        ListOperations<String,Object>  list = this.template.opsForList();
        list.rightPush(listKey,value);
    }



}
