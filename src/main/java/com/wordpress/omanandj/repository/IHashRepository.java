package com.wordpress.omanandj.repository;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ojha
 * Date: 26/07/13
 * Time: 11:17 PM
 */
public interface IHashRepository<V> {
    void put(V obj);

    void multiPut(Collection<V> keys);

    V get(V key);

    List<V> multiGet(Collection<V> keys);

    void delete(V key);

    List<V> getObjects();

    void delete();
}
