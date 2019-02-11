package com.aaparicio.redis.command.scored;

import com.aaparicio.redis.ScoredValue;

import java.util.SortedSet;

public class ScoredAddSetFunction<V> implements ScoredAddFunction<V> {

    private final SortedSet<ScoredValue<V>> set;

    public ScoredAddSetFunction(SortedSet<ScoredValue<V>> set) {
        this.set = set;
    }

    @Override
    public Boolean apply(ScoredValue<V> value) {
        return set.add(value);
    }
}
