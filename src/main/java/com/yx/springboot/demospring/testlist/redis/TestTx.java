package com.yx.springboot.demospring.testlist.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TestTx {

    public static boolean tryTranscation(int amount){
        int balance;
        int debt;
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.watch("balance");
        balance = Integer.valueOf(jedis.get("balance"));
        if(amount > balance){
            jedis.unwatch();
            System.out.println("modify");
            return false;
        }

        System.out.println("Transaction..........");
        Transaction transaction = jedis.multi();
        transaction.decrBy("balance", amount);
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        transaction.incrBy("debt", amount);
        List<Object> list = transaction.exec();
        System.out.println(list);
        System.out.println(Integer.valueOf(jedis.get("balance")));
        System.out.println(Integer.valueOf(jedis.get("debt")));

        return true;

    }


    public static void main(String[] args) {
        System.out.println("*************begin****************");
        tryTranscation(10);
        System.out.println("*************end****************");
    }
}
