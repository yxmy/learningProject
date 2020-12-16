package com.yx.springboot.demospring.testlist.datastructureandalgorithm;

public class ArrayTestClass {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(test(30));
        System.out.println(System.currentTimeMillis());
        System.out.println(test1(30));
        System.out.println(System.currentTimeMillis());
    }

    public static int test(int n){
        if(n == 1){
            return 0;
        }else if (n == 2){
            return 1;
        }
        int first = 0;
        int second = 1;
        int tmp = 0;
        while (n > 2) {
            tmp = second;
            second = first + second;
            first = tmp;
            n--;
        }
        return second;
    }

    public static int test1(int n){
        if (n == 1){
            return 0;
        }else if(n == 2){
            return 1;
        }else {
            return test1(n - 1 ) + test1(n - 2 );
        }
    }

}
