package com.sepa.cach;

import java.util.Map;

public abstract class CacheImpl<K,V> {

    protected int CACHE_SIZE = 10;

    protected Map<K, V> cacheMap;

    public static CacheImpl getInstance(CacheType cacheType) {

        if (cacheType == CacheType.LRU) {
            return new LRUCacheImpl();
        }
        if (cacheType == CacheType.LFU) {
            return new LFUCacheImpl();
        }
        throw new IllegalArgumentException("Wrong cache type");
    }

    public void setCacheSize(int cacheSize) {
        CACHE_SIZE = cacheSize;
    }

    public abstract Map<K, V> getCache();

    public abstract V get(K key);

    public abstract V put(K key, V value);

    @Override
    public String toString() {
        return "CacheImpl{" +
                "CACHE_MAP=" + getCache() +
                '}';
    }
}
