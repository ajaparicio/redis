package com.aaparicio.redis.command.scored;

import com.aaparicio.redis.ScoredValue;
import com.aaparicio.redis.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ScoredRankSetFunction<V> implements ScoredRankFunction<V> {

    private final SortedSet<ScoredValue<V>> set;

    public ScoredRankSetFunction(SortedSet<ScoredValue<V>> set) {
        this.set = set;
    }

    @Override
    public Integer apply(V member) {
        List<ScoredValue<V>> sortedList = new ArrayList<>(set);
        int index = 0;
        ScoredValue<V> toFind = new ScoredValue<V>(-1, member);
        for (Value<V> value : sortedList) {
            if (value.equals(toFind)) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }
}
