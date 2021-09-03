package com.yx.springboot.demospring.java8.functionRef;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author yuanxin
 * @date 2021/9/3
 */
@FunctionalInterface
public interface BufferedReaderProcessor {

    String process(BufferedReader bufferedReader) throws IOException;
}
