package com.sbt.javaschool.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

public class IgniteApp {

    public static void main(String[] args) {
        IgniteConfiguration cfg1 = new IgniteConfiguration();
        cfg1.setIgniteInstanceName("1");

        IgniteConfiguration cfg2 = new IgniteConfiguration();
        cfg2.setIgniteInstanceName("2");

        IgniteConfiguration cfg3 = new IgniteConfiguration();
        cfg3.setIgniteInstanceName("3");


        Ignite ignite1 = Ignition.start(cfg1);
        Ignite ignite2 = Ignition.start(cfg2);
        Ignite ignite3 = Ignition.start(cfg3);

        IgniteCompute compute = ignite1.compute();
        compute.broadcast(() -> {
            int ctr = 0;
            while( Incr.integer.incrementAndGet() < 10_000_000) {
                ++ctr;
            }
            System.out.println(ctr);
        });

    }
}
