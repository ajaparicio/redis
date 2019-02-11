package com.aaparicio.redis;

import java.time.Duration;
import java.util.Collection;

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

@Path("/redis")
public interface RedisService {

    @PUT
    @Path("/set/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    boolean set(@PathParam("key") String key, @QueryParam("value") String value);

    @PUT
    @Path("/set/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    boolean set(@PathParam("key") String key, @QueryParam("value") String value, @QueryParam("expiration") Duration expiration);

    @GET
    @Path("/get/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    String get(@PathParam("key") String key);

    @DELETE
    @Path("/del/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    int del(@PathParam("key") String key);

    @GET
    @Path("/dbsize")
    @Produces(MediaType.TEXT_PLAIN)
    int dbsize();

    @PUT
    @Path("/incr")
    @Produces(MediaType.TEXT_PLAIN)
    int incr(@PathParam("key") String key);

    @PUT
    @Path("/zadd")
    @Produces(MediaType.TEXT_PLAIN)
    boolean zadd(@PathParam("key") String key, @QueryParam("score") int score, @QueryParam("member") String member);

    @GET
    @Path("/zcard/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    int zcard(@PathParam("key") String key);

    @GET
    @Path("/zrank/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    int zrank(@PathParam("key") String key, @QueryParam("member") String member);

    @GET
    @Path("/zrange/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    Collection<String> zrange(@PathParam("key") String key, @QueryParam("start")int start, @QueryParam("end") int end);
}
