package com.yx.mygroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClass {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("aaa");
        stringBuilder.insert(0, "first ");
        System.out.println(stringBuilder);
    }

}
