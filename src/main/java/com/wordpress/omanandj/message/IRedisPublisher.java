package com.wordpress.omanandj.message;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 27/07/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRedisPublisher {

    public void publish(Object message);
}
