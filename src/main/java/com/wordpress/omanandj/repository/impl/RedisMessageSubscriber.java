package com.wordpress.omanandj.repository.impl;

import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 27/07/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RedisMessageSubscriber implements MessageListener {

    private static final Logger LOGGER = new Log4jLoggerFactory().getLogger(RedisMessageSubscriber.class.getName());
    @Override
    public void onMessage(Message message, byte[] pattern) {
        LOGGER.info("Message Received on channel " + new String(message.getChannel()) + " message :: " + message.toString() );
    }
}
