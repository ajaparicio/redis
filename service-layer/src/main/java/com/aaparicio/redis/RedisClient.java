package com.aaparicio.redis;

import com.aaparicio.redis.command.DBSizeMapSupplier;
import com.aaparicio.redis.command.DeleteMapFunction;
import com.aaparicio.redis.command.GetMapFunction;
import com.aaparicio.redis.command.IncrementMapFunction;
import com.aaparicio.redis.command.SetMapConsumer;
import com.aaparicio.redis.command.scored.ScoredAddSetFunction;
import com.aaparicio.redis.command.scored.ScoredCardSetSupplier;
import com.aaparicio.redis.command.scored.ScoredRangeSetFunction;
import com.aaparicio.redis.command.scored.ScoredRankSetFunction;

import java.time.Duration;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisClient<K, V> {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ConcurrentMap<K, Object> map;

    public RedisClient() {
        this.map = new ConcurrentHashMap<>();
    }

    public boolean set(K key, V value) {
        return set(new BaseEntry<K, V>(key, value)) != null;
    }

    public boolean set(K key, V value, Duration duration) {
        return set(new BaseEntry<K, V>(key, value, duration)) != null;
    }

    private V set(Entry<K, V> entry) {
        readWriteLock.writeLock().lock();
        try {
            SetMapConsumer<K, V> command = new SetMapConsumer<>(map);
            command.accept(entry);
            return entry.getValue().getValue();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public V get(K key) {
        readWriteLock.readLock().lock();
        try {
            GetMapFunction<K, V> command = new GetMapFunction<>(map);
            return command.apply(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public int del(K... keys) {
        int count = 0;
        for (K key : keys) {
            count += del(key);
        }
        return count;
    }

    public int del(K key) {
        readWriteLock.writeLock().lock();
        try {
            DeleteMapFunction<K> command = new DeleteMapFunction<>(map);
            if (command.apply(key)) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public int incr(K key) {
        readWriteLock.writeLock().lock();
        try {
            IncrementMapFunction<K> command = new IncrementMapFunction<>(map);
            return command.apply(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public int dbsize() {
        readWriteLock.readLock().lock();
        try {
            return new DBSizeMapSupplier(map).get();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public boolean zadd(K key, int scored, V member) {
        readWriteLock.writeLock().lock();
        try {
            SortedSet<ScoredValue<V>> set = (SortedSet<ScoredValue<V>>) get(key);
            if (set == null) {
                set = new TreeSet<>();
                SetMapConsumer<K, SortedSet<ScoredValue<V>>> command = new SetMapConsumer<>(map);
                Entry<K, SortedSet<ScoredValue<V>>> entry = new BaseEntry<>(key, set);
                command.accept(entry);
            }
            ScoredValue<V> scoredValue = new ScoredValue<>(scored, member);
            return new ScoredAddSetFunction<>(set).apply(scoredValue);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public int zcard(K key) {
        readWriteLock.readLock().lock();
        try {
            SortedSet<ScoredValue<V>> set = (SortedSet<ScoredValue<V>>) get(key);
            return new ScoredCardSetSupplier(set).get();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public int zrank(K key, V member) {
        readWriteLock.readLock().lock();
        try {
            SortedSet<ScoredValue<V>> set = (SortedSet<ScoredValue<V>>) get(key);
            return new ScoredRankSetFunction<>(set).apply(member);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public Collection<V> zrange(K key, int start, int stop) {
        readWriteLock.readLock().lock();
        try {
            SortedSet<ScoredValue<V>> set = (SortedSet<ScoredValue<V>>) get(key);
            Range range = new Range(start, stop);
            return new ScoredRangeSetFunction<>(set).apply(range);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    ConcurrentMap getMap() {
        return map;
    }
}
