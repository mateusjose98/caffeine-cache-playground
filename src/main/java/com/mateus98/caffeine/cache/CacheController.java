package com.mateus98.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("caffeine")
@RestController
@RequiredArgsConstructor
public class CacheController {

    final CacheManager cacheManager;
    @GetMapping(value = "/inspectCache")
    public List<cacheInfo> inspectCache() {
        return cacheManager.getCacheNames()
                .stream()
                .map(this::getCacheInfo)
                .toList();
    }

    private cacheInfo getCacheInfo(String cacheName) {
        Cache<Object, Object> nativeCache =
                (Cache)cacheManager.getCache(cacheName).getNativeCache();
        Set<Object> keys = nativeCache.asMap().keySet();
        CacheStats stats = nativeCache.stats();
        return new cacheInfo(
                cacheName, keys.size(), nativeCache.asMap(), stats.toString());
    }


    private record cacheInfo(
            String name, int size, Map<Object, Object> entries, String stats) {}
}
