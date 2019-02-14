package com.aaparicio.redis;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.stream.Collectors;

@Path("/redis")
public class RedisService {

    private final RedisClient<String, String> client;

    public RedisService() {
        this.client = new RedisClientFactory().getBasicInstance();
    }

    @POST
    @Path("/keys/{key}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean set(@PathParam("key") String key, String value, @MatrixParam("expiration") Long expirationInMillis) {
        if (expirationInMillis != null) {
            return client.set(key, value, Duration.ofMillis(expirationInMillis));
        } else {
            return client.set(key, value);
        }
    }

    @GET
    @Path("/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("key") String key) {
        return client.get(key);
    }

    @DELETE
    @Path("/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public int del(@PathParam("key") String key) {
        return client.del(key);
    }

    @GET
    @Path("/dbsize")
    @Produces(MediaType.TEXT_PLAIN)
    public int dbsize() {
        return client.dbsize();
    }

    @PUT
    @Path("/incr/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public int incr(@PathParam("key") String key) {
        return client.incr(key);
    }

    @PUT
    @Path("/zadd/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean zadd(@PathParam("key") String key, @MatrixParam("score") int score, @MatrixParam("member") String member) {
        return client.zadd(key, score, member);
    }

    @GET
    @Path("/zcard/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public int zcard(@PathParam("key") String key) {
        return client.zcard(key);
    }

    @GET
    @Path("/zrank/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public int zrank(@PathParam("key") String key, @MatrixParam("member") String member) {
        return client.zrank(key, member);
    }

    @GET
    @Path("/zrange/keys/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public String zrange(@PathParam("key") String key, @QueryParam("start")int start, @QueryParam("end") int end) {
        return client.zrange(key, start, end).stream().collect(Collectors.joining(" "));
    }
}
