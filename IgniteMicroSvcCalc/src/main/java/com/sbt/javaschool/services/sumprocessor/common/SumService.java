package com.sbt.javaschool.services.sumprocessor.common;

import org.apache.ignite.services.Service;

public interface SumService extends Service {
    public static final String SERVICE_NAME = "SumService";
    public static final String METRICS_CACHE_NAME = "CalculatorSvcMetrics";

    public <T extends Number> Number sum(T a, T b);
}
