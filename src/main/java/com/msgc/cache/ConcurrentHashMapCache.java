package com.msgc.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConcurrentHashMapCache implements ICache{

    private static volatile Map<String, Object> cache = new ConcurrentHashMap<>(128);

    @Override
    public Object get(String key) {
        if(key == null)
            return null;
        return cache.get(key);
    }

    @Override
    public Object put(String key, Object object) {
        return cache.put(key, object);
    }

    @Override
    public Object delete(String key){
        return cache.remove(key);
    }

    /**
     * 模糊 删除
     * @param key 要删除的key
     */
    @Override
    public void clean(String key) {
        Set<String> keySet = cache.keySet();
        keySet.forEach(k -> {
            if(k.contains(key))
                keySet.remove(k);
        });
    }
}
