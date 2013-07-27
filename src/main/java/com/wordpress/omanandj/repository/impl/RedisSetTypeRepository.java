package com.wordpress.omanandj.repository.impl;

import com.wordpress.omanandj.repository.IRedisSetDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 26/07/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("redisSetTypeRepository")
public class RedisSetTypeRepository implements IRedisSetDemo<String,String> {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void add(String key, String value) {
         redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public boolean isMemberOf(String key, String value) {
        return redisTemplate.opsForSet().isMember(key,value);
    }

    @Override
    public Set<String> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public String pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
