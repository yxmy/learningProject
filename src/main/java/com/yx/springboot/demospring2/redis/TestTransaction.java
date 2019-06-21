package com.yx.springboot.demospring2.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTransaction {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        Transaction transaction = jedis.multi();
        transaction.set("k1", "v1");
        transaction.set("k2", "v2");
        transaction.set("k3", "v3");
        transaction.set("k4", "v4");
        transaction.set("k5", "v5");
        transaction.discard();
    }

}
