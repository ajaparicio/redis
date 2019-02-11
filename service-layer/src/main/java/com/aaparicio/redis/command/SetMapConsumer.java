package com.aaparicio.redis.command;

import com.aaparicio.redis.Entry;

import java.util.Map;

public class SetMapConsumer<K, V> implements SetConsumer<K, V> {

    private final Map map;

    public SetMapConsumer(Map map) {
        this.map = map;
    }

    @Override
    public void accept(Entry<K, V> entry) {
        map.put(entry.getKey(), entry.getValue());
    }
}
