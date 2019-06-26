package com.yx.springboot.demospring2.modelmapper.testpackage;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main1 {

    private static Random random = new Random();

    public static List<Integer> buildNumber() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(random.nextInt());
        }
        return numbers;
    }

    public static void main(String[] args) {
        List<Integer> list = buildNumber();
//        List<String> strings = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List strings = mapper.map(list, listType);
        for (Object str : strings) {
            System.out.println(str);
        }
    }
}
