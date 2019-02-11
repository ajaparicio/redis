package com.aaparicio.redis.command.scored;


import com.aaparicio.redis.ScoredValue;

import java.util.function.Function;

public interface ScoredAddFunction<V> extends Function<ScoredValue<V>, Boolean> {
}
