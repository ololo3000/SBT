package com.sbt.javaschool.app;

import com.sbt.javaschool.common.filters.SumServiceFilter;
import com.sbt.javaschool.services.sumprocessor.SumServiceImpl;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.services.ServiceConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SumServiceNodeStartup {
    public static void main(String[] args) {
        Map<String, Boolean> attrs = new HashMap<>();
        attrs.put("sum.service.node", true);

        DataStorageConfiguration storageCfg = new DataStorageConfiguration();
        storageCfg.getDefaultDataRegionConfiguration().setMaxSize(
                1L*1024 * 1024 * 1024);

        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);


        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setUserAttributes(attrs)
                .setPeerClassLoadingEnabled(true)
                .setDataStorageConfiguration(storageCfg)
                .setDiscoverySpi(spi);


        Ignite ignite = Ignition.start(cfg);

        ServiceConfiguration sumSvcCfg = new ServiceConfiguration();
        sumSvcCfg.setMaxPerNodeCount(1)
                .setService(new SumServiceImpl())
                .setName(SumServiceImpl.SERVICE_NAME)
                .setNodeFilter(new SumServiceFilter());

        IgniteServices services = ignite.services();
        services.deploy(sumSvcCfg);
    }
}
