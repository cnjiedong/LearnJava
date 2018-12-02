package com.ljd.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSlfj {

    public static void main(String argv[]){
        Logger logger = LoggerFactory.getLogger(Object.class);
        logger.error("123");
    }
}
