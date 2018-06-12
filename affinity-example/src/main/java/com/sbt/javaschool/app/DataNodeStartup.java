package com.sbt.javaschool.app;

import com.sbt.javaschool.common.Company;
import com.sbt.javaschool.common.Person;
import com.sbt.javaschool.common.PersonKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.Collections;

public class DataNodeStartup {
    public static void main(String[] args) {

        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setUserAttributes(Collections.singletonMap("data.node", true))
                .setPeerClassLoadingEnabled(true)
                .setDiscoverySpi(spi)
                .setCacheConfiguration(
                        new CacheConfiguration<PersonKey, Person>().setName("PersonsCache").setCacheMode(CacheMode.PARTITIONED),
                        new CacheConfiguration<String, Company>().setName("ComsCache").setCacheMode(CacheMode.PARTITIONED));

        Ignite ignite = Ignition.start(cfg);
    }
}
