package com.yx.mygroup.interfaces;

public interface Hello {

    void sayHello(String name);

    default String add(){
        return "这是接口的一个默认方法";
    }
}