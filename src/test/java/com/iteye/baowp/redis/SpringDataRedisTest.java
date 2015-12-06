package com.iteye.baowp.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;


/**
 * Created by baowp on 15-6-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "spring-redis.xml")
public class SpringDataRedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void addLink() {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.get("foo2");
        valueOps.set("foo2", "bar2");
        String value = valueOps.get("foo2");
        assertEquals("bar2", value);
    }

}
