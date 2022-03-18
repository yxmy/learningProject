package com.yx.mygroup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TulingUser extends UserDetail{

    private String name;

    private int age;

    private byte [] arr = new byte[1024];

    public TulingUser() {

    }

    public TulingUser(String name) {
        this.name = name;
    }

    public TulingUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
