package com.sepa.cach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheImplTest {

    private CacheImpl cache;

    @BeforeEach
    void setUp() {
        cache = CacheImpl.getInstance(CacheType.LFU);
        cache.setCacheSize(4);
        cache.put(1, "1");
        cache.put(2, "2");
        cache.put(3, "3");
        cache.put(4, "4");
    }

    @Test
    void put() {
        assertEquals(4, cache.getCache().size());

        cache.get(1);
        cache.get(1);
        cache.get(1);

        cache.get(2);
        cache.get(2);

        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.get(3);

        cache.get(4);
        cache.get(4);
        cache.get(4);

        cache.put(5, "5");

        List<Map.Entry> entryList = new ArrayList<>(cache.getCache().entrySet());

        // key 2 should be evicted from the cache as the least popular value

        assertEquals(1, entryList.get(0).getKey());
        assertEquals(3, entryList.get(1).getKey());
        assertEquals(4, entryList.get(2).getKey());
        assertEquals(5, entryList.get(3).getKey());
    }
}