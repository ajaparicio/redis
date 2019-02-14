# Redis
#ToDo
 - Manage exceptions in the API Rest
 - Create uniq interface for all commands
 - Change the RedisClient to accept commands instead of have the set,get,incr methods
 - Use default interface methods to avoid duplicate code (to manage the concurrence) in RedisClient 
 - Improve the way we are using the typing in the map and list
 - Improve the concurrence management
 - Create unit test for the commands and dont use the RedisClientTest to test everything
 
##Testing
Open the classes:
  - [RedisClientTest](service-layer/src/test/java/com/aaparicio/redis/RedisClientTest.java) to test redis client operation
  - [ScoredRedisClientTest](service-layer/src/test/java/com/aaparicio/redis/ScoredRedisClientTest.java) to test redis client SCORED operation

##Server usage
###To execute the Jetty Server
java -jar web-layer/target/redis.jar

###Execute operations
Set one:1 
```
curl -i -X POST -H "Content-Type:text/plain" -d '1' 'http://localhost:8080/redis/keys/one'
```

Set two:2 with 10 milliseconds expiration
```
curl -i -X POST -H "Content-Type:text/plain" -d '2' 'http://localhost:8080/redis/keys/two;expiration=10'
```

Get one
```
curl -i -X GET 'http://localhost:8080/redis/keys/one'
```

Get two
```
curl -i -X GET 'http://localhost:8080/redis/keys/two'
```

Inc one
```
curl -i -X PUT 'http://localhost:8080/redis/incr/keys/one'
```

Get size
```
curl -i -X GET 'http://localhost:8080/redis/dbsize'
```

Delete one
```
curl -i -X DELETE 'http://localhost:8080/redis/keys/one'
```

Add scored key
```
curl -i -X PUT 'http://localhost:8080/redis/zadd/keys/three;score=1;member=a'
curl -i -X PUT 'http://localhost:8080/redis/zadd/keys/three;score=2;member=b'
```

Get scored key size
```
curl -i -X GET 'http://localhost:8080/redis/zcard/keys/three'
```

Get member position of the scored key
```
curl -i -X GET 'http://localhost:8080/redis/zrank/keys/three;member=a'
curl -i -X GET 'http://localhost:8080/redis/zrank/keys/three;member=b'
```
