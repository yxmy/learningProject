package com.yx.mygroup.innerclass;

/**
 * Outer类被编译为Outer.class，而Inner类被编译为Outer$Inner.class。
 *
 * @author yuanxin
 * @date 2021/8/25
 */
public class Outer {

    private String name;

    Outer(String name) {
        this.name = name;
    }

    class Inner {
        void hello() {
            Outer.this.name = "Changed";
            System.out.println("Hello, " + Outer.this.name);
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer("Nested"); // 实例化一个Outer
        Outer.Inner inner = outer.new Inner(); // 实例化一个Inner
        inner.hello();
    }
}