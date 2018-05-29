package com.sbt.javaschool.services.divideprocessor.common;

import org.apache.ignite.services.Service;

public interface DivisionService extends Service {
    public static final String SERVICE_NAME = "DivisionService";
    public static final String METRICS_CACHE_NAME = "CalculatorSvcMetrics";

    float divide(float a, float b);
}
