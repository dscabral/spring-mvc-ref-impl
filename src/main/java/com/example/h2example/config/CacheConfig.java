package com.example.h2example.config;

import com.example.h2example.cache.IGenericCache;
import com.example.h2example.cache.impl.GenericCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public <K, V> GenericCache<K, V> getCache(@Value("${app.cache-timeout}") Long cacheTimeout) {
        return new GenericCache<K, V>(cacheTimeout);
    }
}
