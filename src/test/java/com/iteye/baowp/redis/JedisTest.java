package com.iteye.baowp.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by baowp on 15-6-20.
 */
public class JedisTest {

    @Test
    public void testJedis(){
        Jedis jedis=new Jedis("localhost",6379);
        jedis.set("foo", "bar2");
        String value = jedis.get("foo");
        System.out.println(value);
        System.out.println(jedis.bitcount("foo"));
    }
}
