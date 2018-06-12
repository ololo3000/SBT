package com.sbt.javaschool.app;

import com.sbt.javaschool.common.Company;
import com.sbt.javaschool.common.Person;
import com.sbt.javaschool.common.PersonKey;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.Collections;

public class ClientNodeStartup {
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

        IgniteCache<String, Company> comCache = ignite.cache("ComsCache");
        Company company = comCache.get("com");
        IgniteCompute compute = ignite.compute();

        compute.broadcast(new IgniteRunnable() {
            @IgniteInstanceResource
            private Ignite ignite;

            @Override
            public void run() {
                IgniteCache<PersonKey, Person> personCache = ignite.cache("PersonsCache");

                long started = System.currentTimeMillis();

                for(PersonKey key : company.getPersonKeys()) {
                    Person person = personCache.get(key);
                }

                long elapsed = System.currentTimeMillis() - started;

                System.out.println(ignite.cluster().localNode().id() + " node get all entries for " + elapsed + " ms");
            }
        });

        compute.affinityRun("PersonsCache", company.getName(), new IgniteRunnable() {

            @IgniteInstanceResource
            private Ignite ignite;

            @Override
            public void run() {
                IgniteCache<PersonKey, Person> personCache = ignite.cache("PersonsCache");

                long started = System.currentTimeMillis();

                for(PersonKey key : company.getPersonKeys()) {
                    Person person = personCache.get(key);
                }

                long elapsed = System.currentTimeMillis() - started;

                System.out.println(ignite.cluster().localNode().id() + " node with affinity task get all entries for " + elapsed + " ms");
            }
        });
    }
}
