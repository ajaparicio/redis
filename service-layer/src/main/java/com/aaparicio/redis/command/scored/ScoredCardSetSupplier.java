package com.aaparicio.redis.command.scored;

import java.util.SortedSet;

public class ScoredCardSetSupplier implements ScoredCardSupplier {

    private final SortedSet set;

    public ScoredCardSetSupplier(SortedSet set) {
        this.set = set;
    }

    @Override
    public Integer get() {
        return set.size();
    }
}
