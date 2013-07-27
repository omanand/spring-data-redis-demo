package com.wordpress.omanandj.repository.impl;

import com.wordpress.omanandj.repository.IRedisListDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 25/07/13
 * Time: 10:32 PM
 */
@Component("redisListTypeRepository")
public class RedisListTypeRepository implements IRedisListDemo<String, String>{

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void push(String key, String value, boolean right) {
        if(right) {
             redisTemplate.opsForList().rightPush(key,value);
        }
        else {
            redisTemplate.opsForList().leftPush(key,value);
        }
    }

    @Override
    public void multiAdd(String key, Collection<String> values, boolean right) {
     //  redisTemplate.multi();
        //Have to find better implementation for this
        for(String value : values) {
            push(key,value,right);
        }
       // redisTemplate.exec();
    }

    @Override
    public Collection<String> get(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }

    @Override
    public String pop(String key, boolean right) {
       String value;
       if(right) {
           value = redisTemplate.opsForList().rightPop(key);
       }
       else {
           value = redisTemplate.opsForList().leftPop(key);
       }
       return  value;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void trim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key,start,end);
    }

    @Override
    public Long size(String key) {
           return redisTemplate.opsForList().size(key);
    }
}
