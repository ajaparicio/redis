package com.aaparicio.redis.command;


import java.util.function.Function;

public interface IncrementFunction<K> extends Function<K, Integer> {
}
