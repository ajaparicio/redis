package com.aaparicio.redis.command;


import java.util.function.Function;

public interface DeleteFunction<K> extends Function<K, Boolean> {
}
