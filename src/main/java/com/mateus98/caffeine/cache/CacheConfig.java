package com.mateus98.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
@Log4j2
public class CacheConfig {


    private Cache<Object, Object> buildCache(int initialCapacity, int maximumSize, int duration, TimeUnit timeUnit) {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .recordStats()
                .evictionListener((Object key, Object value,
                                   RemovalCause cause) ->
                        log.info(String.format(
                                "Key %s was evicted (%s)%n", key, cause)))
                .removalListener((Object key, Object value,
                                  RemovalCause cause) ->
                        log.info(String.format(
                                "Key %s was removed (%s)%n", key, cause)))
                .expireAfterAccess(duration, timeUnit)
                .scheduler(Scheduler.systemScheduler())
                .build();
    }


    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.registerCustomCache(
                "ContratoCache", buildCache(1, 5, 5, TimeUnit.SECONDS)
        );
        caffeineCacheManager.registerCustomCache(
                "OutroCache", buildCache(1, 2, 10, TimeUnit.SECONDS)
        );
        return caffeineCacheManager;
    }

}
