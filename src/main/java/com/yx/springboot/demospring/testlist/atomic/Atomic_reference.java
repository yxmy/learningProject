package com.yx.springboot.demospring.testlist.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class Atomic_reference {

  static final AtomicReference<String> string = new AtomicReference("abc");

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(
              new Runnable() {
                @Override
                public void run() {
                  try {
                    Thread.sleep(Math.abs((int) Math.random() * 100));
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                  if (string.compareAndSet("abc", "def")) {
                    System.out.println(Thread.currentThread().getId() + "Change successful");
                  } else {
                    System.out.println(Thread.currentThread().getId() + "Change failure");
                  }
                }
              })
          .start();
    }
  }
}
