package com.sbt.javaschool.app;

import com.sbt.javaschool.common.filters.DivisionServiceFilter;
import com.sbt.javaschool.common.filters.ExpressionServiceFilter;
import com.sbt.javaschool.services.divideprocessor.DivisionServiceImpl;
import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.expressionprocessor.ExpressionServiceImpl;
import com.sbt.javaschool.services.expressionprocessor.common.ExpressionService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.services.ServiceConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpressionServiceNodeStartup {
    public static void main(String[] args) {
        Map<String, Boolean> attrs = new HashMap<>();
        attrs.put("expression.service.node", true);

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


        ServiceConfiguration divSvcCfg = new ServiceConfiguration();
        divSvcCfg.setMaxPerNodeCount(1)
                .setService(new ExpressionServiceImpl())
                .setName(ExpressionService.SERVICE_NAME)
                .setNodeFilter(new ExpressionServiceFilter());

        IgniteServices services = ignite.services();
        services.deploy(divSvcCfg);

    }
}
