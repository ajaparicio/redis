package com.aaparicio.redis.command;

import java.util.Map;

public class DeleteMapFunction<K> implements DeleteFunction<K> {

    private final Map map;

    public DeleteMapFunction(Map map) {
        this.map = map;
    }

    @Override
    public Boolean apply(K key) {
        return map.remove(key) != null;
    }
}
