package com.wordpress.omanandj.repository;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 25/07/13
 * Time: 11:24 PM
 */
public interface IRedisListDemo<K,V> {

    void push(K key, V value, boolean right);

    void multiAdd(K key,Collection<V> values, boolean right);

    Collection<V> get(K key);

    V pop(K key, boolean right);

    void delete(K key);

    void trim(K key, int start, int end);

    Long size(K key);

}
