package com.yx.springboot.demospring.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2018/4/20.
 */
@Getter
@Setter
public class Person {

    private String name;

    private int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
}
