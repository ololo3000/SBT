package com.sbt.javaschool.services.expressionprocessor.common;

import com.sbt.javaschool.common.expression.Expression;
import org.apache.ignite.services.Service;

public interface ExpressionService extends Service {
    public static final String SERVICE_NAME = "ExpressionService";
    public static final String METRICS_CACHE_NAME = "CalculatorSvcMetrics";

    Number calculate(Expression expression);
}
