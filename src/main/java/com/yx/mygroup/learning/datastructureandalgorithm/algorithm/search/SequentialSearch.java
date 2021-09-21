package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.search;

import java.util.List;

/**
 * 顺序表查找
 *
 * @author yuanxin
 * @date 2021/9/12
 */
public class SequentialSearch {

    public String search1(List<String> list, String str) {
        for (String s : list) {
            if (str.equals(s)) {
                return s;
            }
        }
        return null;
    }
}
