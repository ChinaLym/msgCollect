package com.msgc.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CaffeineConfig {
    private static final int DEFAULT_MAXSIZE = 500;
    private static final int DEFAULT_TTL = 600;

    public enum Caches{
        tableCache,             //收集表
        fieldCache,             //表字段缓存
        commentCache(100),      //表评论缓存
        answerRecordCache,      //用户-表 填写记录缓存
        answerCache,            //表填写内容缓存
        favoriteRecord          //收藏缓存
        ;
        Caches(){}
        Caches(int ttl){
            this.ttl = ttl;
        }
        private int maxSize = DEFAULT_MAXSIZE;      //最大數量
        private int ttl     = DEFAULT_TTL;          //过期时间（秒）

        public int getMaxSize() {
            return maxSize;
        }
        public int getTtl() {
            return ttl;
        }
    }

    /**
     * 创建基于Caffeine的Cache Manager
     * @return CacheManager
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        for(Caches c : Caches.values()){
            caches.add(new CaffeineCache(c.name(),
                    Caffeine.newBuilder().recordStats()
                            .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                            .maximumSize(c.getMaxSize())
                            .build())
            );
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}