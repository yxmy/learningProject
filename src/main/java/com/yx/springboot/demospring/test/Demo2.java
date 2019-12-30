package com.yx.springboot.demospring.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Demo2{

    public List<String> list = new ArrayList<>();

    public void set(){
        for(int i = 0; i < 100; i++){
            list.add("i" + i);
            if (i == 50 ) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        get();
                    }
                });

            }
            System.out.println("set list size ：" + list.size());
        }
    }

    public void get(){
        ListIterator<String> stringListIterator = list.listIterator();
        while (stringListIterator.hasNext()){
            System.out.println(stringListIterator.next());
            stringListIterator.remove();
            System.out.println("get list size ：" + list.size());
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println(list);
        Iterator var2 = list.iterator();
        int i = 0;
        while(var2.hasNext()) {
            System.out.println("Loop:" + i);
            i++;
            String item = (String)var2.next();
            if ("2".equals(item)) {
                list.remove(item);
            }
        }

        System.out.println(list);

    }


    public void test(){
        File file = new File("");

    }
}
