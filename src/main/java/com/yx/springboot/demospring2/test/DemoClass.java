package com.yx.springboot.demospring2.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DemoClass {

    public static void main(String[] args) {
        File file = new File("/home/pc-yx/gitProject");
        Map<String, Integer> map = new HashMap<>();
        for(File file1 : file.listFiles()){
            String businessDateFileName = file1.getName();
            if (file1.isDirectory()){
                for(File file2 : file1.listFiles()){
                    String cinemaCodeFileName = file2.getName();
                    if(file2.isDirectory()){
                        map.put(businessDateFileName + "," + cinemaCodeFileName, getFileCount(file2));
                    }
                }
            }
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + "::::::" + entry.getValue());
        }
    }

    public static int getFileCount(File file){
        int count = 0;
        for(File file1 : file.listFiles()){
            if(file1.isDirectory()){
                count += getFileCount(file1);
            }else{
                count++;
            }
        }
        return count;
    }
}
