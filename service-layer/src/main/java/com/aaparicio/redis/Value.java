package com.aaparicio.redis;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Duration;

public class Value<V> {
    private final V value;
    private final Duration expiration;

    private Long createdTimeMillis;

    public Value(V value) {
        this(value, null);
    }

    public Value(V value, Duration expiration) {
        this.value = value;
        this.expiration = expiration;
        this.createdTimeMillis = System.currentTimeMillis();
    }

    public V getValue() {
        return value;
    }

    public boolean hasExpired() {
        if (expiration != null) {
            long current = System.currentTimeMillis();
            return current > createdTimeMillis + expiration.toMillis();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.value)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Value rhs = (Value) obj;
        return new EqualsBuilder()
                .append(value, rhs.getValue())
                .isEquals();
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", expiration=" + expiration +
                ", createdTimeMillis=" + createdTimeMillis +
                '}';
    }
}
