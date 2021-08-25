package com.yx.springboot.demospring.innerclass;

/**
 * @author yuanxin
 * @date 2021/8/25
 */
public class Outer3 {

    private static String NAME = "OUTER";

    private String name;

    Outer3(String name) {
        this.name = name;
    }

    static class StaticNested {
        void hello() {
//            Outer3.this.name;
            System.out.println("Hello, " + Outer3.NAME);
        }
    }


}
