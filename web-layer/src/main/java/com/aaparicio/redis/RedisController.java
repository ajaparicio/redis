package com.aaparicio.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

public class RedisController implements RedisService {

    private final RedisClient<String, String> client;

    public RedisController() {
        this.client = new RedisClient<>();
    }

    @Override
    public boolean set(String key, String value) {
        return client.set(key, value);
    }

    @Override
    public boolean set(String key, String value, Duration expiration) {
        return client.set(key, value, expiration);
    }

    @Override
    public String get(String key) {
        return client.get(key);
    }

    @Override
    public int del(String key) {
        return client.del(key);
    }

    @Override
    public int dbsize() {
        return client.dbsize();
    }

    @Override
    public int incr(String key) {
        return client.incr(key);
    }

    @Override
    public boolean zadd(String key, int score, String member) {
        return client.zadd(key, score, member);
    }

    @Override
    public int zcard(String key) {
        return client.zcard(key);
    }

    @Override
    public int zrank(String key, String member) {
        return client.zrank(key, member);
    }

    @Override
    public Collection<String> zrange(String key, int start, int end) {
        return client.zrange(key, start, end);
    }

    ConcurrentMap getMap() {
        return client.getMap();
    }
}
