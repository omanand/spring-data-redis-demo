package com.wordpress.omanandj;

import com.wordpress.omanandj.config.AppConfiguration;
import com.wordpress.omanandj.message.IRedisPublisher;
import com.wordpress.omanandj.model.User;
import com.wordpress.omanandj.repository.ICacheRepository;
import com.wordpress.omanandj.repository.IHashRepository;
import com.wordpress.omanandj.repository.IRedisListDemo;
import com.wordpress.omanandj.repository.IRedisSetDemo;
import com.wordpress.omanandj.repository.impl.RedisListTypeRepository;
import com.wordpress.omanandj.repository.impl.RedisSetTypeRepository;
import com.wordpress.omanandj.repository.impl.SimpleCacheRepository;
import com.wordpress.omanandj.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER = new Log4jLoggerFactory().getLogger(App.class.getName());

    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        ICacheRepository<String,String> simpleCacheRepository =
                    (SimpleCacheRepository)context.getBean("simpleCacheRepository");

        //Delete the key if exists
        simpleCacheRepository.delete("blog.author.name");
        //Test simple put first.
        simpleCacheRepository.put("blog.author.name", "Omanand Jha Vatsa");

        //Let's retrieve the key to make sure that value is same.
        String name = simpleCacheRepository.get("blog.author.name");
        //Print the name
        LOGGER.info("***********************************Redis Value Type Demo***************************");
        LOGGER.info("Get Demo :: " + name);

        //Now to a multi put in the redis data store

        Map<String,String> fruits = new HashMap<String, String>();

        fruits.put("apple", "red");
        fruits.put("mango", "yellow");
        fruits.put("papaya", "green");

        simpleCacheRepository.multiPut(fruits);

        //Now get back fruit and its colors

        List<String> fruitsFromDB = simpleCacheRepository.multiGet(Arrays.asList("apple","mango","papaya","blog.author.name"));

        LOGGER.info("MultiGet demo :: {}", fruitsFromDB);


        LOGGER.info("***********************************Redis List Demo***************************");
        IRedisListDemo<String,String> redisListDemo =
                (RedisListTypeRepository)context.getBean("redisListTypeRepository");

        //Delete the key of exists
        redisListDemo.delete("list:demo");

        //Push the element to the right
        redisListDemo.push("list:demo", "10",true);
        redisListDemo.push("list:demo", "40",true);
        //Push the element to the left
        redisListDemo.push("list:demo", "30",false);
        redisListDemo.push("list:demo", "20",false);
        //Try multi add
        Collection<String> valuesToTestMultiPush = Arrays.asList(new String[]{"40","50","60"});
        //Add same collection to left and right push
        redisListDemo.multiAdd("list:demo",valuesToTestMultiPush,true);


        //Fetch the entire list for the list:demo
        Collection<String> values = redisListDemo.get("list:demo");

        LOGGER.info("list:demo ::  {}", values);

        //Try right pop
        String value = redisListDemo.pop("list:demo",true);
        LOGGER.info("Right Pop Value :: {}" , value);

        //Try left pop
        value = redisListDemo.pop("list:demo",false);
        LOGGER.info("Left Pop Value :: {}",  value);


        //Now trim the list
        redisListDemo.trim("list:demo", 0,2);
        //fetch the values
        values = redisListDemo.get("list:demo");
        LOGGER.info("After trimming list:demo :: {} ",  values);

        //Get the size of the list
        Long size = redisListDemo.size("list:demo");
        LOGGER.info("size of list:demo :: {}", size);

        LOGGER.info("***********************************Redis Set Demo***************************");

        IRedisSetDemo<String,String> redisSetDemo =
                (RedisSetTypeRepository)context.getBean("redisSetTypeRepository");

        redisSetDemo.delete("set:demo");
        Set<String> names = new HashSet<String>();

        redisSetDemo.add("set:demo", "Omanand");
        redisSetDemo.add("set:demo", "Jha");
        redisSetDemo.add("set:demo", "Vatsa");
        names = redisSetDemo.members("set:demo");
        LOGGER.info("set:demo :: {}",  names);


        LOGGER.info("***********************************Redis Hash Demo***************************");

        IHashRepository<User> userRepository =
                (UserRepository)context.getBean("userRepository");

        userRepository.delete();
        //Try simple put of user in data store
        User user1 = new User(1000L,"user1","*****",25,"omanandj" );
        User user2 = new User(1001L,"user2","*****",25,"omanandj" );

        //Add user1 and user 2 to redis data store
        userRepository.put(user1);
        userRepository.put(user2);

        //Fetch current users
        List<User> users = userRepository.getObjects();
        LOGGER.info("Users : {}" , users);

        //Fetch user1
        User user1FromDb = userRepository.get(user1);
        LOGGER.info("User1 : {}", user1FromDb);


        //delete a user
        userRepository.delete(user2);
        users = userRepository.getObjects();
        LOGGER.info("Users After deleting user2 : {}", users);

        //Try multi put and multi get

        users = new ArrayList<User>();
        for(int i = 0; i < 20; ++i) {
            User user = new User(1100L + i, "user" + i, "*****",25+i, "omanandj");
            users.add(user);

        }
        userRepository.multiPut(users);
        List<User> fetchedUsers = userRepository.multiGet(users);
        LOGGER.info("Multi Get Users : {}", fetchedUsers);



        LOGGER.info("***********************************Redis PubSub Demo***************************");
        IRedisPublisher redisPublisher = context.getBean(IRedisPublisher.class);

        //Now publish a simple string message
        redisPublisher.publish("Hello Omanand");
        redisPublisher.publish("How are you?");
        redisPublisher.publish("Are you receiving my message?");

        //Get the message listener container and shutdown the listener now.
        RedisMessageListenerContainer container = context.getBean(RedisMessageListenerContainer.class);
        container.stop();
    }
}
