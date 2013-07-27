package com.wordpress.omanandj.repository;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 26/07/13
 * Time: 9:21 AM
 */
public interface IRedisSetDemo<K,V> {

    void add(K key, V value);

    boolean isMemberOf(K key, V value);

    Set<V> members(K key);

    V pop(K key);

    void delete(K key);

}
