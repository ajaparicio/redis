package com.aaparicio.redis;

public class RedisClientFactory {
    private static final RedisClient<String, String> INSTANCE = new RedisClient<>();

    public RedisClient<String, String> getBasicInstance() {
        return INSTANCE;
    }
}
