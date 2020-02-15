package com.seesawin.controllers;

import com.seesawin.redis.JedisPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/testJedis/{key}")
    public String testJedis(@PathVariable("key") String key) {
        Jedis jedis = null;
        String name = "";
        try {
            jedis = JedisPoolManager.getMgr().getResource();
            name = jedis.get(key);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
//        JedisPoolManager.getMgr().destroy();
        return name;
    }

    @GetMapping("/testSpringDJedis/{key}")
    public String testSpringDJedis(@PathVariable("key") String key) {
        String age = (String) redisTemplate.opsForValue().get(key);
        return age;
    }
}
