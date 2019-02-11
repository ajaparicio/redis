package com.aaparicio.redis.command.scored;

import com.aaparicio.redis.Range;
import com.aaparicio.redis.ScoredValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class ScoredRangeSetFunction<V> implements ScoredRangeFunction<V> {

    private final SortedSet<ScoredValue<V>> set;

    public ScoredRangeSetFunction(SortedSet<ScoredValue<V>> set) {
        this.set = set;
    }

    @Override
    public Collection<V> apply(Range range) {
        List<ScoredValue<V>> sortedList = new ArrayList<>(set);
        return sortedList.subList(getStart(range), getEnd(range)).
                stream().map(value -> value.getValue()).collect(Collectors.toList());
    }

    private int getStart(Range range) {
        return getPosition(range.getStart());
    }

    private int getEnd(Range range) {
        return getPosition(range.getEnd());
    }

    private int getPosition(Integer index) {
        if(index < 0) {
            return set.size() + index;
        } else {
            return index;
        }
    }
}
