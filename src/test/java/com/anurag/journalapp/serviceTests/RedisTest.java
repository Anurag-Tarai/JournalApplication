package com.anurag.journalapp.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("email", "anurag@gmail.com");
        Object name = redisTemplate.opsForValue().get("name");
        Assertions.assertNotNull(name);
    }
}
