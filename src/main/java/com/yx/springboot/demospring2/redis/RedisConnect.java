package com.yx.springboot.demospring2.redis;

import redis.clients.jedis.Jedis;

public class RedisConnect {

    public static void main(String[] args) {
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
