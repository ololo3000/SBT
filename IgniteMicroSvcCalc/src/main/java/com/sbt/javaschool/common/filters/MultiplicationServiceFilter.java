package com.sbt.javaschool.common.filters;

import com.sbt.javaschool.common.metric.CalculatorSvcMetric;
import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.multiplyprocessor.common.MultiplicationService;
import com.sbt.javaschool.services.subtractprocessor.common.SubtractionService;
import com.sbt.javaschool.services.sumprocessor.common.SumService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.CacheException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiplicationServiceFilter implements IgnitePredicate<ClusterNode> {


    @IgniteInstanceResource
    private Ignite ignite;

    @Override
    public boolean apply(ClusterNode clusterNode) {
        if (clusterNode.isClient()) {
            return false;
        }

        Boolean multiplicationSvcNode = clusterNode.attribute("multiplication.service.node");
        if (multiplicationSvcNode != null && multiplicationSvcNode) {
            return true;
        }

        IgniteCache<String, CalculatorSvcMetric> cache = ignite.cache(MultiplicationService.METRICS_CACHE_NAME);
        CalculatorSvcMetric metric = cache.get(MultiplicationService.SERVICE_NAME);

        if (metric == null) {
            return false;
        }

        int val = metric.getHitCntr() / (metric.getInstanceCntr() + 1);

        for (String svcName : Arrays.asList(DivisionService.SERVICE_NAME,
                MultiplicationService.SERVICE_NAME,
                SubtractionService.SERVICE_NAME,
                SumService.SERVICE_NAME)) {
            metric = cache.get(svcName);
            if (metric == null) {
                continue;
            }

            if (metric.getHitCntr() / (metric.getInstanceCntr() + 1) > val) {
                return false;
            }
        }

        return true;
    }
}
