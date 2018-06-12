package com.sbt.javaschool.app;

import com.sbt.javaschool.common.Company;
import com.sbt.javaschool.common.Person;
import com.sbt.javaschool.common.PersonKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.Collections;

public class InputClientNodeStartup {

    public static void main(String[] args) {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setUserAttributes(Collections.singletonMap("input.client.node", true))
                .setClientMode(true)
                .setPeerClassLoadingEnabled(true)
                .setDiscoverySpi(spi);

        Ignite ignite = Ignition.start(cfg);



        IgniteCache<PersonKey, Person> personCache = ignite.cache("PersonsCache");
        IgniteCache<String, Company> comCache = ignite.cache("ComsCache");

        Company company = new Company("com");
        for (int i = 0; i < 100; i++) {
            Person person = new Person(Integer.toString(i), company);
            PersonKey personKey = new PersonKey(person);
            personCache.put(personKey, person);
            company.addPerson(personKey);
        }
        comCache.put(company.getName(), company);
    }
}
