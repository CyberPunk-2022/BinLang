package com.xianglan.qnytv.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例内存缓存
 * todo 改成redis缓存
 */
public class SingletonMapCache {
    private static final SingletonMapCache INSTANCE = new SingletonMapCache();
    private static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    private SingletonMapCache() {

    }

    public static SingletonMapCache getInstance() {
        return INSTANCE;
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return cache.get(key);
    }

    public Boolean containsKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return cache.containsKey(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
