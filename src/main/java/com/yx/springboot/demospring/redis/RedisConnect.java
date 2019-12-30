package com.yx.springboot.demospring.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

public class RedisConnect {

    private static StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        stringRedisTemplate.opsForValue();
        Jedis jedis = new Jedis("localhost", 6379);
        for(int i = 0; i < 20; i++){
            System.out.println(jedis.setnx("k"+i, "v"+i));
        }
        System.out.println(jedis.select(1));
        for(int i = 0; i < 20; i++){
            System.out.println(jedis.setnx("k"+i, "v"+i));
        }
    }



}
