package com.yx.springboot.demospring.testlist.test;

import com.yx.springboot.demospring.interfaces.Hello2;
import com.yx.springboot.demospring.testlist.enums.FilmPublishType;
import com.yx.springboot.demospring.testlist.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class Demo2 extends HashMap<String, Object> {

    public List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        test11();
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

    public static void test4() {
        try {
            Class clazz = Class.forName("java.util.String");
            Field[] fields = clazz.getFields();
            fields[0].setAccessible(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void test5() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method.getName());
                if (method.getName().equals("sayHello")) {
                    System.out.println("Hello, " + args[0]);
                }
                if (method.getName().equals("sayHello2")) {
                    System.out.println("Hello2, " + args[0]);
                }
                return null;
            }
        };
        Hello2 hello = (Hello2) Proxy.newProxyInstance(Hello2.class.getClassLoader(), new Class[]{Hello2.class}, handler);
        hello.sayHello("袁鑫");
        hello.sayHello2("袁鑫");
    }

    public static void test6() {
        Class<Demo2> demo2Class = Demo2.class;
        Type type = demo2Class.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument);
            }
        }
    }

    public static void test7() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Object[] objects = list.toArray();
        String[] strings = list.toArray(new String[0]);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public static void test8() {
        Map<FilmPublishType, Object> map = new EnumMap<>(FilmPublishType.class);
        map.put(FilmPublishType.cooperation, 111);
        map.put(FilmPublishType.alone, 222);
    }

    public static void test9() {
        Map<User, Integer> map = new TreeMap<>((o1, o2) -> Integer.compare(o2.getAge(), o1.getAge()));

        Queue<User> queue = new PriorityQueue<>(new UserComparator());
        queue.offer(new User("张三", 14));
        queue.offer(new User("李四", 12));
        queue.offer(new User("张三", 17));
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
    }

    static class UserComparator implements Comparator<User> {

        @Override
        public int compare(User o1, User o2) {
            if (o1.getAge() == o2.getAge()) {
                return 0;
            }
            return Integer.compare(o1.getAge(), o2.getAge());
        }
    }

    public static void test10() {
        Deque<String> deque = new LinkedList<>();
        deque.offerLast("A");
        deque.offerLast("B");
        deque.offerFirst("C");
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollFirst());
    }

    public static void test11() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("洗牌前：" + list);
        Collections.shuffle(list);
        System.out.println("洗牌后：" + list);
    }

}
