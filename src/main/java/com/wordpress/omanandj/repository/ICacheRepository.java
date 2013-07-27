package com.wordpress.omanandj.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 24/07/13
 * Time: 11:27 PM
 */
public interface ICacheRepository<K,V> {
    void put(K key, V value);

    void multiPut(Map<K,V> keyValues);

    V get(K key);

    List<V> multiGet(Collection<K> keys);

    void delete(K key);

}
