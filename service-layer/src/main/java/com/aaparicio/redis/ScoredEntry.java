package com.aaparicio.redis;

public interface ScoredEntry<K, V> extends Entry<K, V>{
    ScoredValue<V> getValue();
}
