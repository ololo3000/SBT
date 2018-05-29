package com.sbt.javaschool.app;

import com.sbt.javaschool.common.filters.DivisionServiceFilter;
import com.sbt.javaschool.common.metric.CalculatorSvcMetric;
import com.sbt.javaschool.services.divideprocessor.DivisionServiceImpl;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



public class CalculatorServicesMetricsNodeStartup {

    public static void main(String[] args) {
        Map<String, Boolean> attrs = new HashMap<>();
        attrs.put("metrics.service.node", true);

        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);

        CacheConfiguration<String, CalculatorSvcMetric> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setName("CalculatorSvcMetrics");

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setUserAttributes(attrs)
                .setPeerClassLoadingEnabled(true)
                .setDiscoverySpi(spi)
                .setCacheConfiguration(cacheCfg);


        Ignite ignite = Ignition.start(cfg);
    }
}
