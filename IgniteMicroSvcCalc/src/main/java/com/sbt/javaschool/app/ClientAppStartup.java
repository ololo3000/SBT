package com.sbt.javaschool.app;

import com.sbt.javaschool.common.expression.Expression;
import com.sbt.javaschool.common.metric.CalculatorSvcMetric;
import com.sbt.javaschool.services.divideprocessor.DivisionServiceImpl;
import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.expressionprocessor.common.ExpressionService;
import com.sbt.javaschool.services.multiplyprocessor.common.MultiplicationService;
import com.sbt.javaschool.services.subtractprocessor.common.SubtractionService;
import com.sbt.javaschool.services.sumprocessor.common.SumService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientAppStartup {
    public static void main(String[] args) {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1", "127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setPeerClassLoadingEnabled(true)
                .setDiscoverySpi(spi)
                .setClientMode(true);

        Ignite ignite = Ignition.start(cfg);

        DivisionService divisionService = ignite
                .services()
                .serviceProxy(DivisionService.SERVICE_NAME, DivisionService.class, false);
        System.out.println(divisionService.divide(5.0f, 1.7f));

        MultiplicationService multiplicationService = ignite
                .services()
                .serviceProxy(MultiplicationService.SERVICE_NAME, MultiplicationService.class, false);
        System.out.println(multiplicationService.multiply(5.0f, 1.7f));

        SumService sumService = ignite
                .services()
                .serviceProxy(SumService.SERVICE_NAME, SumService.class, false);
        System.out.println(sumService.sum(5.0f, 1.7f));

        SubtractionService subtractionService = ignite
                .services()
                .serviceProxy(SubtractionService.SERVICE_NAME, SubtractionService.class, false);
        System.out.println(subtractionService.subtract(5.0f, 1.7f));

        ExpressionService expressionService = ignite
                .services()
                .serviceProxy(ExpressionService.SERVICE_NAME, ExpressionService.class, false);
        System.out.println(
                expressionService.calculate(new Expression(5.0f).minus(1).div(-10).plus(7).mult(-2)));

        IgniteCache<String, CalculatorSvcMetric> cache = ignite.cache(DivisionService.METRICS_CACHE_NAME);
        System.out.println(cache.get(DivisionService.SERVICE_NAME).getHitCntr());
        System.out.println(cache.get(DivisionService.SERVICE_NAME).getInstanceCntr());

    }
}
