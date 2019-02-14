package com.aaparicio.redis.command;

import com.aaparicio.redis.ExpirationException;
import com.aaparicio.redis.Value;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IncrementMapFunction<K> implements IncrementFunction<K> {

    private final Map map;

    public IncrementMapFunction(Map map) {
        this.map = map;
    }

    @Override
    public Integer apply(K key) {
        Integer result;
        Value value = (Value) map.get(key);
        if (value != null) {
            if (value.hasExpired()) {
                throw new ExpirationException(String.format("The key has expired [%s]", value));
            } else {
                result = Integer.valueOf(value.getValue().toString());
                result++;
            }
        } else {
            result = 1;
        }
        value = new Value<>(result);
        map.put(key, value);

        return result;
    }
}
