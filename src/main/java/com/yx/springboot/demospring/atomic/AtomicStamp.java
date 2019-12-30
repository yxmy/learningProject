package com.yx.springboot.demospring.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStamp {

    static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19, 0);

    public static void main(String[] args) {
        for(int i=0;i<3;i++){
            int stamp = money.getStamp();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        while (true){
                            Integer moneyReference = money.getReference();
                            if(moneyReference < 20){
                                if(money.compareAndSet(moneyReference, moneyReference + 20, stamp, stamp+1)){
                                    System.out.println("余额小于20元，充值成功，当前余额：" + money.getReference());
                                    break;
                                }
                            }else{
                                break;
                            }
                        }
                    }
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++){
                    while (true){
                        int stamp = money.getStamp();
                        Integer m = money.getReference();
                        if(m > 10){
                            System.out.println("余额大于10元");
                            if(money.compareAndSet(m, m - 10, stamp, stamp + 1)){
                                System.out.println("成功消费10元，余额：" + money.getReference());
                                break;
                            }
                        }else {
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                }
            }
        }).start();
    }
}
