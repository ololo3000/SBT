package com.sbt.javaschool;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class ChildApp {
    public static Logger logger = LoggerFactory.getLogger(ChildApp.class);


    public static void main(String[] args) {
        Ignite ignite = Ignition.start();
        logger.info("Child of Child Two!");
        ChildOneApp.sayer();
    }

}
