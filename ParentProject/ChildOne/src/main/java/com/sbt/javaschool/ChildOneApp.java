package com.sbt.javaschool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class ChildOneApp {
    public static Logger logger = LoggerFactory.getLogger(ChildOneApp.class);
    public static void main( String[] args ) {
        logger.info("Hello world!");
    }

    public static void sayer() {
        logger.info("Child One say!");
    }
}
