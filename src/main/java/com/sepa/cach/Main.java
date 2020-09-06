package com.sepa.cach;

public class Main {

    public static void main(String[] args) {

        // check LRU cache
        CacheImpl cache = CacheImpl.getInstance(CacheType.LRU);
        cache.setCacheSize(4);

        cache.put(1, "1");
        cache.put(2, "2");
        cache.put(3, "3");
        cache.put(4, "4");

        // key 1 should be evicted from the cache as oldest value
        cache.put(5, "5");
        System.out.println("LRU cache: "+ cache.getCache());

        // -----------------------------------------------------
        // check LFU cache

        CacheImpl cache2 = CacheImpl.getInstance(CacheType.LFU);
        cache2.setCacheSize(4);

        cache2.put(1, "1");
        cache2.put(2, "2");
        cache2.put(3, "3");
        cache2.put(4, "4");

        cache2.get(1);
        cache2.get(1);
        cache2.get(1);

        cache2.get(2);
        cache2.get(2);

        cache2.get(3);
        cache2.get(3);
        cache2.get(3);
        cache2.get(3);

        cache2.get(4);
        cache2.get(4);
        cache2.get(4);

        // key 2 should be evicted from the cache as the least popular value
        cache2.put(5, "5");

        System.out.println("LFU cache: "+ cache2.getCache());
    }
}
