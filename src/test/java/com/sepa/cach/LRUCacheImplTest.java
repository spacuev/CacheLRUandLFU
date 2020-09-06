package com.sepa.cach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheImplTest {

    private CacheImpl cache;

    @BeforeEach
    void setUp() {
        cache = CacheImpl.getInstance(CacheType.LRU);
        cache.setCacheSize(4);
        cache.put(1, "1");
        cache.put(2, "2");
        cache.put(3, "3");
        cache.put(4, "4");
        cache.put(5, "5");
    }

    @Test
    void put() {
        assertEquals(4, cache.getCache().size());
        List<Map.Entry> entryList = new ArrayList<>(cache.getCache().entrySet());

        // key 1 should be evicted from the cache as oldest value

        assertEquals(2, entryList.get(0).getKey());
        assertEquals(3, entryList.get(1).getKey());
        assertEquals(4, entryList.get(2).getKey());
        assertEquals(5, entryList.get(3).getKey());
    }
}