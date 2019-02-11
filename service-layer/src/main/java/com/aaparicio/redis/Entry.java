package com.aaparicio.redis;

public interface Entry<K, V> {
    K getKey();
    Value<V> getValue();
}
