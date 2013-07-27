package com.wordpress.omanandj.config;

import com.wordpress.omanandj.repository.impl.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan("com.wordpress.omanandj")
public class AppConfiguration {
    @Bean
    public RedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory jRedisConnectionFactory = new JedisConnectionFactory(new JedisPoolConfig());
        jRedisConnectionFactory.setHostName("localhost");
        jRedisConnectionFactory.setPort(6379);
        jRedisConnectionFactory.setPassword("");
        return jRedisConnectionFactory;

    }
    @Bean(name= "stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(getConnectionFactory());
        return  stringRedisTemplate;
    }
    @Bean(name = "redisTemplate")
    public <String,V> RedisTemplate<String,V> getRedisTemplate(){
        RedisTemplate<String,V> redisTemplate =  new RedisTemplate<String, V>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return redisTemplate;
    }

    @Bean
    ChannelTopic createTopic()
    {
        return new ChannelTopic("pubsub:channel");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
          return new MessageListenerAdapter(new RedisMessageSubscriber());
    }
    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory( getConnectionFactory() );
        container.addMessageListener( messageListenerAdapter(), createTopic() );

        return container;
    }
}
