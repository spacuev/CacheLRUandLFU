package com.sepa.cach;

import java.util.*;

public class LFUCacheImpl<K,V> extends CacheImpl<K,V> {

    private HashMap<K, Integer> keyFrequencyMap = new HashMap<>();

    private TreeMap<Integer, HashSet<K>> frequencyTreeMap = new TreeMap<Integer, HashSet<K>>();

    public LFUCacheImpl(){
        cacheMap = getCacheMap();
    }

    public Map<K, V> getCache() {
        return cacheMap;
    }

    @Override
    public V get(K key) {
        V value = cacheMap.get(key);
        if (value != null) {
            frequencyIncrement(key);
        }
        return value;
    }

    private void frequencyIncrement(K key) {
        Integer frequency = keyFrequencyMap.compute(key, (k, f) -> f + 1);
        delFromSortedFrequencies(key, frequency - 1);
        frequencyTreeMap.computeIfAbsent(frequency, keys -> new HashSet<>()).add(key);
    }

    @Override
    public V put(K key, V value) {
        if (cacheMap.size() >= CACHE_SIZE) {
            evict();
        }
        V oldValue = cacheMap.put(key, value);

        Integer frequency = keyFrequencyMap.computeIfAbsent(key, f -> 1);

        if (!frequencyTreeMap.containsKey(frequency)) {
            frequencyTreeMap.put(frequency, new HashSet<>());
        }
        frequencyTreeMap.get(frequency).add(key);

        return oldValue;
    }

    private void evict() {
        Integer minFrequency = frequencyTreeMap.firstKey();
        K evictionKey = frequencyTreeMap.get(minFrequency).iterator().next();
        delFromSortedFrequencies(evictionKey, minFrequency);
        keyFrequencyMap.remove(evictionKey);
        cacheMap.remove(evictionKey);
    }

    private void delFromSortedFrequencies(K key, Integer frequency) {
        if (frequencyTreeMap.get(frequency).size() > 1) {
            frequencyTreeMap.get(frequency).remove(key);
        } else {
            frequencyTreeMap.remove(frequency);
        }
    }

    private HashMap<K, V> getCacheMap() {
        return new HashMap<K, V>();
    }
}
