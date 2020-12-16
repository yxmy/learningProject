package com.yx.springboot.demospring.testlist.javareflect;

import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * https://mp.weixin.qq.com/s/SdMMyRI4OTEc8XnAyupgpA
 */
public class ReflectDemo {

    /**
     * 实例对象表达方式
     */
    @Test
    public void test1() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        User user = new User();

        Class class1 = user.getClass();
        Class<User> class2 = User.class;
        Class class3 = Class.forName("com.yx.springboot.demospring.testlist.javareflect.ReflectDemo");

        User user1 = class2.newInstance();
    }

    /**
     * 基本数据类型获取Class实例
     */
    @Test
    public void test2(){
        Class<String> c1 = String.class;
        Class<Integer> c2 = int.class;
        Class<Void> c3 = void.class;
        System.out.println(c1.getName());
        System.out.println(c2.getSimpleName());
        System.out.println(c3.getName());
    }

    /**
     * 获取类的所有方法并打印
     * 一个成员变量就是一个method对象
     * getMethod所有的public方法，包括父类继承的public方法
     * getDeclaredMethods 获取该类的所有方法，包括private，但是不包括继承的方法
     */
    @Test
    public void test3(){
        Class<String> c = String.class;
        System.out.println("类名为:" + c.getName());
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            //获取方法的返回类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getSimpleName() + "---------");
            //获取方法名
            System.out.print(method.getName() + "(");
            Class[] parameterTypes = method.getParameterTypes();
            for (Class parameter : parameterTypes) {
                System.out.print(parameter.getName() + ",");
            }
            System.out.println(")");
        }
    }

    /**
     * 获取成员变量的信息
     * getFields 获取public
     * getDeclaredFields 获取所有的
     */
    @Test
    public void test4(){
        Class<String> c = String.class;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Class fieldClass = field.getType();
            System.out.println(fieldClass.getName() + "------" + field.getName());
        }
    }

    /**
     * 获取构造函数的信息
     */
    @Test
    public void test5(){
        Class<String> cl = String.class;
        Constructor[] declaredConstructors = cl.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            String name = declaredConstructor.getName();
            System.out.print(name + "(");
            Class[] parameterTypes = declaredConstructor.getParameterTypes();
            for (Class parameterType : parameterTypes) {
                System.out.print(parameterType.getName() + ",");
            }
            System.out.println(")");
        }
    }

    /**
     * 反射操作
     */
    @Test
    public void test6() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<User> c = User.class;
        User user = new User();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            Method setMethod = c.getDeclaredMethod("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1), field.getType());
            setMethod.invoke(user, "111");

            Method getMethod = c.getDeclaredMethod("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
            Object invoke = getMethod.invoke(user);
            System.out.println(invoke.toString());
        }


    }

}
