package com.yx.springboot.demospring.testlist.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo2 {

    public List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
    }


    public static void test1() {
        double a = 1.0;
        double b = 3.0;
        double c = -4.0;
        // 求平方根可用 Math.sqrt():
        // System.out.println(Math.sqrt(2)); ==> 1.414
        // TODO:
        double r1 = 0;
        double r2 = 0;
        double sqrt = Math.sqrt(b * b - (4.0 * a * c));
        r1 = (-b + sqrt) / 2.0 * a;
        r2 = (-b - sqrt) / 2.0 * a;

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r1 == 1 && r2 == -4 ? "测试通过" : "测试失败");
    }

    public static void test2() {
        String expression = "11111";
        char a = expression.charAt(0);
        System.out.println(a >= '0');
        System.out.println(a >= 'r');
    }

    public static void test3() {
        int[][] deepArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8}
        };
        System.out.println(Arrays.deepToString(deepArray));
    }

}
