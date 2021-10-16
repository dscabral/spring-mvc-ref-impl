package com.example.h2example.cache.impl;

import com.example.h2example.cache.IGenericCache;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class GenericCache<K, V> implements IGenericCache<K, V> {

    // The timeout valeu can be the default, if we instantiate the class without parameters
    public static final Long DEFAULT_CACHE_TIMEOUT = 60000L;

    // cacheMap it's the data structure that store in memory the cache content
    protected Map<K, CacheValue<V>> cacheMap;
    protected Long cacheTimeout;

    public GenericCache() {
        this(DEFAULT_CACHE_TIMEOUT);
    }

    // We can setup a personalized value to cache timeout on class constructor
    public GenericCache(Long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
        this.clear();
    }

    // The clean method remove the expired values form the cacheMap
    @Override
    public void clean() {
        for(K key: this.getExpiredKeys()) {
            this.remove(key);
        }
    }

    @Override
    public void clear() {
        this.cacheMap = new HashMap<>();
    }

    @Override
    public boolean containsKey(K key) {
        return this.cacheMap.containsKey(key);
    }

    // Return a Set of expired values
    protected Set<K> getExpiredKeys() {
        return this.cacheMap.keySet().parallelStream()
                .filter(this::isExpired)
                .collect(Collectors.toSet());
    }

    // Check if each storage cacheMap is expired, based on date time creation and the cache timeout
    protected boolean isExpired(K key) {
        LocalDateTime expirationDateTime = this.cacheMap.get(key).getCreatedAt().plus(this.cacheTimeout, ChronoUnit.MILLIS);
        return LocalDateTime.now().isAfter(expirationDateTime);
    }

    @Override
    public Optional<V> get(K key) {
        this.clean();
        log.info(Optional.ofNullable(this.cacheMap.get(key)).map(CacheValue::getValue).toString());
        return Optional.ofNullable(this.cacheMap.get(key)).map(CacheValue::getValue);
    }

    @Override
    public void put(K key, V value) {
        this.cacheMap.put(key, this.createCacheValue(value));
    }

    // When we put a value inside cacheMap, we create a structure that stores the value and the datetime creation
    protected CacheValue<V> createCacheValue(V value) {
        LocalDateTime now = LocalDateTime.now();
        return new CacheValue<V>() {
            @Override
            public V getValue() {
                return value;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return now;
            }
        };
    }

    @Override
    public void remove(K key) {
        this.cacheMap.remove(key);
    }

    // CacheValue it's the value from cacheMap, it's defined as a interface
    // that store the generic value V and the creation date.
    protected interface CacheValue<V> {
        // Generic value
        V getValue();

        // The getCreatedAt it's used to calculate the expiration date of the data
        LocalDateTime getCreatedAt();
    }
}