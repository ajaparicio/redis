package com.aaparicio.redis.command;


import java.util.function.Function;

public interface GetFunction<K, V> extends Function<K,V> {
}
