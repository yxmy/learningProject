package com.yx.springboot.demospring.testlist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User extends UserDetail{

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;

    private int age;

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            return Objects.equals(this.getName(), ((User) o).getName()) && this.getAge() == user.getAge();
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.age;
    }
}
