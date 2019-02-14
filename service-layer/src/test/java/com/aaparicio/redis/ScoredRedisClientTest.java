package com.aaparicio.redis;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ScoredRedisClientTest {
    private RedisClient<String, String> redisClient;

    @BeforeMethod
    public void setUp() {
        redisClient = new RedisClient<>();
    }

    @Test
    public void testZAdd() {
        String anyKey = "key";
        int anyScore = 1;
        String anyMember = "one";

        redisClient.zadd(anyKey, anyScore, anyMember);

        Map map = redisClient.getMap();
        Value value = (Value) map.get(anyKey);
        SortedSet<ScoredValue<String>> set = (SortedSet<ScoredValue<String>>) value.getValue();
        ScoredValue<String> scoredValue = set.first();
        assertNotNull(map);
        assertNotNull(value);
        assertNotNull(set);
        assertNotNull(scoredValue);
        assertEquals(scoredValue.getScore(), anyScore);
        assertEquals(scoredValue.getValue(), anyMember);
    }

    @Test
    public void testZAddTwice() {
        String anyKey = "key";
        int anyScore = 1;
        int otherScore = 2;
        String anyMember = "one";
        String otherMember = "two";

        redisClient.zadd(anyKey, anyScore, anyMember);
        redisClient.zadd(anyKey, otherScore, otherMember);

        Map map = redisClient.getMap();
        Value value = (Value) map.get(anyKey);
        SortedSet<ScoredValue<String>> set = (SortedSet<ScoredValue<String>>) value.getValue();
        assertNotNull(map);
        assertNotNull(value);
        assertNotNull(set);
        assertEquals(set.size(), 2);
    }

    @Test
    public void testZCard() {
        String anyKey = "key";
        int anyScore = 1;
        int otherScore = 2;
        String anyMember = "one";
        String otherMember = "two";

        redisClient.zadd(anyKey, anyScore, anyMember);
        redisClient.zadd(anyKey, otherScore, otherMember);
        int card = redisClient.zcard(anyKey);

        assertEquals(card, 2);
    }

    @Test
    public void testZRank() {
        String anyKey = "key";
        int anyScore = 1;
        int otherScore = 2;
        String anyMember = "one";
        String otherMember = "two";

        redisClient.zadd(anyKey, anyScore, anyMember);
        redisClient.zadd(anyKey, otherScore, otherMember);
        int anyRank = redisClient.zrank(anyKey, anyMember);
        int otherRank = redisClient.zrank(anyKey, otherMember);

        assertEquals(anyRank, 0);
        assertEquals(otherRank, 1);
    }

    @Test
    public void testZRankNotOrder() {
        String anyKey = "key";
        int firstScore = -1;
        int secondScore = 2;
        int lastScore = 17;
        String firstMember = "first";
        String secondMember = "second";
        String lastMember = "last";

        redisClient.zadd(anyKey, secondScore, secondMember);
        redisClient.zadd(anyKey, lastScore, lastMember);
        redisClient.zadd(anyKey, firstScore, firstMember);
        int firstRank = redisClient.zrank(anyKey, firstMember);
        int secondRank = redisClient.zrank(anyKey, secondMember);
        int lastRank = redisClient.zrank(anyKey, lastMember);

        assertEquals(firstRank, 0);
        assertEquals(secondRank, 1);
        assertEquals(lastRank, 2);
    }

    @Test
    public void testZRange() {
        String anyKey = "key";
        int firstScore = -1;
        int secondScore = 2;
        int lastScore = 17;
        String firstMember = "first";
        String secondMember = "second";
        String lastMember = "last";

        redisClient.zadd(anyKey, secondScore, secondMember);
        redisClient.zadd(anyKey, lastScore, lastMember);
        redisClient.zadd(anyKey, firstScore, firstMember);
        Collection<String> strings = redisClient.zrange(anyKey, 0, 2);

        assertNotNull(strings);
        assertEquals(strings.size(), 2);
    }

    @Test
    public void testZRangeAll() {
        String anyKey = "key";
        int firstScore = -1;
        int secondScore = 2;
        int lastScore = 17;
        String firstMember = "first";
        String secondMember = "second";
        String lastMember = "last";

        redisClient.zadd(anyKey, secondScore, secondMember);
        redisClient.zadd(anyKey, lastScore, lastMember);
        redisClient.zadd(anyKey, firstScore, firstMember);
        Collection<String> strings = redisClient.zrange(anyKey, 0, 3);

        assertNotNull(strings);
        assertEquals(strings.size(), 3);
    }

    @Test
    public void testZRangeOutOfIOndex() {
        String anyKey = "key";
        int firstScore = -1;
        int secondScore = 2;
        String firstMember = "first";
        String secondMember = "second";

        redisClient.zadd(anyKey, secondScore, secondMember);
        redisClient.zadd(anyKey, firstScore, firstMember);
        Collection<String> strings = redisClient.zrange(anyKey, 0, 5);

        assertNotNull(strings);
        assertEquals(strings.size(), 2);
    }

    @Test
    public void testZRangeNegativeIndex() {
        String anyKey = "key";
        int firstScore = -1;
        int secondScore = 2;
        String firstMember = "first";
        String secondMember = "second";

        redisClient.zadd(anyKey, secondScore, secondMember);
        redisClient.zadd(anyKey, firstScore, firstMember);
        Collection<String> strings = redisClient.zrange(anyKey, -1, 1);

        assertNotNull(strings);
        assertEquals(strings.size(), 0);
    }
}
