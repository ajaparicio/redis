package com.aaparicio.redis;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class RedisClientTest {
    private RedisClient<String, String> redisClient;

    @BeforeMethod
    public void setUp() {
        redisClient = new RedisClient<>();
    }

    @Test
    public void testSet() {
        String anyKey = "one";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);

        assertTrue(redisClient.getMap().containsKey(anyKey));
    }

    @Test
    public void testSetTwoTimesTheSameKey() {
        String anyKey = "one";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);
        redisClient.set(anyKey, anyValue);

        assertEquals(redisClient.getMap().size(), 1);
    }

    @Test
    public void testSetWithDuration() {
        String anyKey = "one";
        String anyValue = "1";
        Duration anyDuration = Duration.ofMillis(1);

        redisClient.set(anyKey, anyValue, anyDuration);

        assertTrue(redisClient.getMap().containsKey(anyKey));
    }

    @Test
    public void testGet() {
        String anyKey = "one";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);
        String value = redisClient.get(anyKey);

        assertEquals(value, anyValue);
    }

    @Test
    public void testGetWithValidDuration() {
        String anyKey = "one";
        String anyValue = "1";
        Duration anyDuration = Duration.ofHours(1);

        redisClient.set(anyKey, anyValue, anyDuration);
        String value = redisClient.get(anyKey);

        assertEquals(value, anyValue);
    }

    @Test (expectedExceptions = ExpirationException.class)
    public void testGetExpiredValue() throws InterruptedException {
        String anyKey = "one";
        String anyValue = "1";
        Duration anyDuration = Duration.ofNanos(1);

        redisClient.set(anyKey, anyValue, anyDuration);
        Thread.sleep(1);
        redisClient.get(anyKey);
    }

    @Test
    public void testDelNotExistingValue() {
        String anyKey = "one";

        int deletes = redisClient.del(anyKey);

        assertEquals(deletes, 0);
    }

    @Test
    public void testDelExistingValue() {
        String anyKey = "one";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);

        int deletes = redisClient.del(anyKey);

        assertEquals(deletes, 1);
    }

    @Test
    public void testDelExistingValues() {
        String anyKey = "one";
        String otherKey = "two";
        String anyValue = "1";
        String otherValue = "2";

        redisClient.set(anyKey, anyValue);
        redisClient.set(otherKey, otherValue);

        int deletes = redisClient.del(anyKey, otherKey);

        assertEquals(deletes, 2);
    }

    @Test
    public void testDelExistingOneValue() {
        String anyKey = "one";
        String otherKey = "two";
        String anyValue = "1";
        String otherValue = "2";

        redisClient.set(anyKey, anyValue);
        redisClient.set(otherKey, otherValue);

        int deletes = redisClient.del(anyKey);

        assertEquals(deletes, 1);
    }

    @Test
    public void testDelExistingAndNoExistingValue() {
        String anyKey = "one";
        String otherKey = "two";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);

        int deletes = redisClient.del(anyKey, otherKey);

        assertEquals(deletes, 1);
    }

    @Test
    public void testGetEmptySize() {
        int size = redisClient.dbsize();

        assertEquals(size, 0);
    }

    @Test
    public void testGetSize() {
        String anyKey = "one";
        String otherKey = "two";
        String anyValue = "1";
        String otherValue = "2";

        redisClient.set(anyKey, anyValue);
        redisClient.set(otherKey, otherValue);

        int size = redisClient.dbsize();

        assertEquals(size, 2);
    }

    @Test
    public void testIncr() {
        String anyKey = "one";

        int result = redisClient.incr(anyKey);

        assertEquals(result, 1);
    }

    @Test
    public void testIncrTwice() {
        String anyKey = "one";

        redisClient.incr(anyKey);
        int result = redisClient.incr(anyKey);

        assertEquals(result, 2);
    }

    @Test
    public void testIncrExistedValue() {
        String anyKey = "one";
        AtomicInteger anyValue = new AtomicInteger(17);

        RedisClient<String, AtomicInteger> redisClient = new RedisClient<>();
        redisClient.set(anyKey, anyValue);

        int result = redisClient.incr(anyKey);

        assertEquals(result, 18);
    }

    @Test (expectedExceptions = ClassCastException.class)
    public void testIncrWrongValue() {
        String anyKey = "one";
        String anyValue = "1";

        redisClient.set(anyKey, anyValue);
        redisClient.incr(anyKey);
    }
}
