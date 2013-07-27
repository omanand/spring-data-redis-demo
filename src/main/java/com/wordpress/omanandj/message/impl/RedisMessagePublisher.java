package com.wordpress.omanandj.message.impl;

import com.wordpress.omanandj.message.IRedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 27/07/13
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RedisMessagePublisher implements IRedisPublisher {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    @Override
    public void publish(Object message) {
        redisTemplate.convertAndSend(topic.getTopic(),message.toString());
    }
}
