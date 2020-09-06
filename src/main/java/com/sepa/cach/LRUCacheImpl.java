package com.sepa.cach;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl<K, V> extends CacheImpl<K, V> {

    public LRUCacheImpl(){
        cacheMap = getCacheMap();
    }

    public Map<K, V> getCache() {
        return cacheMap;
    }

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cacheMap.put(key, value);
    }

    public LinkedHashMap<K, V> getCacheMap() {
        return new LinkedHashMap<K, V>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }
}
