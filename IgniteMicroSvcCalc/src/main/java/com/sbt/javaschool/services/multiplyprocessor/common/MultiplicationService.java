package com.sbt.javaschool.services.multiplyprocessor.common;

import org.apache.ignite.services.Service;

public interface MultiplicationService extends Service {
    public static final String SERVICE_NAME = "MultiplicationService";
    public static final String METRICS_CACHE_NAME = "CalculatorSvcMetrics";

    float multiply(float a, float b);
}
