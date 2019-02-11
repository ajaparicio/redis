package com.aaparicio.redis.command.scored;


import java.util.function.Function;

public interface ScoredRankFunction<V> extends Function<V, Integer> {
}
