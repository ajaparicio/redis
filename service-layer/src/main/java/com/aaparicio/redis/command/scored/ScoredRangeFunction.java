package com.aaparicio.redis.command.scored;


import com.aaparicio.redis.Range;
import com.aaparicio.redis.ScoredValue;

import java.util.Collection;
import java.util.function.Function;

public interface ScoredRangeFunction<V> extends Function<Range, Collection<V>> {
}
