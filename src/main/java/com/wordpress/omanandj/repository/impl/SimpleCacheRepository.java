package com.wordpress.omanandj.repository.impl;

import com.wordpress.omanandj.repository.ICacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 24/07/13
 * Time: 11:32 PM
 */
@Component("simpleCacheRepository")
public class SimpleCacheRepository implements ICacheRepository<String,String> {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void put(String key, String value) {
         redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void multiPut(Map<String, String> keyValues) {
         redisTemplate.opsForValue().multiSet(keyValues);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
