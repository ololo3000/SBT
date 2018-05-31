package com.sbt.javaschool.services.multiplyprocessor.common;

import org.apache.ignite.services.Service;

public interface MultiplicationService extends Service {
    public static final String SERVICE_NAME = "MultiplicationService";
    public static final String METRICS_CACHE_NAME = "CalculatorSvcMetrics";

    public <T extends Number> Number multiply( T a, T b);
}
