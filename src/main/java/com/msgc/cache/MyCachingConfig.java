package com.msgc.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCachingConfig extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

}