package com.ljd.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Car {
    private Logger logger = LogManager.getLogger(this.getClass());
    String name = "car";
    public  void run(){
        logger.debug("i'm car, i start running");
    }
}
