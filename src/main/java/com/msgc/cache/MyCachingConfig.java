package com.msgc.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>CustomCachingConfig</code></b>
 * <p>
 * Description: Custom Caching Config.
 * <p>
 * <b>Creation Time:</b> 2018/9/6 17:14
 *
 * @date 2018/9/6
 * @since JDK 1.7
 */
@Configuration
public class MyCachingConfig extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

}