package com.msgc.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;

//@Configuration
/**
 * 暂时不使用该 key 生成策略，由于缓存较少，使用手动设置或默认策略
 *
 * Spring 默认key生成：
 * 默认key的生成按照以下规则：
 * - 如果没有参数,则使用0作为key
 * - 如果只有一个参数，使用该参数作为key
 * - 如果又多个参数，使用包含所有参数的hashCode作为key
 */
public class MyCachingConfig extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

}