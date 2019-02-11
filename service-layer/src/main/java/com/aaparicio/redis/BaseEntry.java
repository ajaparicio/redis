package com.aaparicio.redis;

import java.time.Duration;

public class BaseEntry<K, V> implements Entry<K, V> {
    private final K key;
    private final Value<V> value;

    public BaseEntry(K key, V value) {
        this.key = key;
        this.value = new Value<V>(value);
    }

    public BaseEntry(K key, V value, Duration duration) {
        this.key = key;
        this.value = new Value<V>(value, duration);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public Value<V> getValue() {
        return value;
    }
}
