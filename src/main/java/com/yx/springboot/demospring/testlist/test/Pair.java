package com.yx.springboot.demospring.testlist.test;

public class Pair<T> {

    private T first;

    private T last;

    public T getFirst() {
        return this.first;
    }

    public T getLast() {
        return this.last;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setLast(T last) {
        this.last = last;
    }

}
