package com.aaparicio.redis.command;

import com.aaparicio.redis.Value;
import com.aaparicio.redis.ExpirationException;


import java.util.Map;

public class GetMapFunction<K, V> implements GetFunction<K, V> {

    private final Map map;

    public GetMapFunction(Map map) {
        this.map = map;
    }

    @Override
    public V apply(K key) {
        final V result;
        final Value<V> value = (Value<V>) map.get(key);
        if (value != null) {
            if (value.hasExpired()) {
                throw new ExpirationException(String.format("The key has expired [%s]", value));
            } else {
                result = value.getValue();
            }
        } else {
            result = null;
        }
        return result;
    }
}
