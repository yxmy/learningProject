package com.yx.mygroup;

import com.sun.nio.zipfs.ZipInfo;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class TestClass {

    public static void main(String[] args) throws IOException {
        System.out.println(ReentrantLock.class.getClassLoader());
        System.out.println(ZipInfo.class.getClassLoader());
        System.out.println(TestClass.class.getClassLoader());

        // AppClassLoader
        System.out.println(ClassLoader.getSystemClassLoader());
        // ExtClassLoader
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        // BootstrapClassLoader
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());

    }
}
