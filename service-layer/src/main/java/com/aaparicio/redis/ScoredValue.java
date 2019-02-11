package com.aaparicio.redis;

public class ScoredValue<V> extends Value<V> implements Comparable<ScoredValue<V>> {
    private long score;

    public ScoredValue(long score, V value) {
        super(value);
        this.score = score;
    }

    public long getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoredValue<V> o) {
        return Long.compare(score, o.getScore());
    }
}
