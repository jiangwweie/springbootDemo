package com.jiangwei.redisdemo.service;

import com.jiangwei.redisdemo.dao.RedisDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * describe:
 *
 * @author jiangwei
 * @date 2018/10/16
 */
@Service
public class RedisService {

    @Resource
    private RedisDao redisDao;

    public void set(String key, Object value) {
        redisDao.setKey(key,value);
    }

    public Object get(String key) {
        return redisDao.getValue(key);
    }
}
