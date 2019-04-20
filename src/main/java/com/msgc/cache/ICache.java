package com.msgc.cache;

public interface ICache {
    Object get(String key);

    Object put(String key, Object object);

    Object delete(String key);

    void clean(String key);
}
