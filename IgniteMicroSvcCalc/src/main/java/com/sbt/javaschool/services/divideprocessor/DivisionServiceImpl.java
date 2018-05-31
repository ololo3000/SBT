package com.sbt.javaschool.services.divideprocessor;

import com.sbt.javaschool.common.metric.CalculatorSvcMetric;
import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;

public class DivisionServiceImpl implements DivisionService {
    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<String, CalculatorSvcMetric> cache;

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Initializing DivisionService on node:" + ignite.cluster().localNode());
        cache = ignite.cache(METRICS_CACHE_NAME);
        cache.putIfAbsent(SERVICE_NAME, new CalculatorSvcMetric());
        cache.put(SERVICE_NAME, cache.get(SERVICE_NAME).incrInstanceCntr());
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing DivisionService on node:" + ignite.cluster().localNode());
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping DivisionService on node:" + ignite.cluster().localNode());
        cache.put(SERVICE_NAME, cache.get(SERVICE_NAME).decrInstanceCntr());
    }

    @Override
    public <T extends Number> Number divide(T a, T b) {
        cache.put(SERVICE_NAME, cache.get(SERVICE_NAME).incrHitCntr());

        if (b.equals(0)) {
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        }

        if (a instanceof Integer) {
            return (Number) (a.intValue() / b.intValue());
        } else if (a instanceof Float) {
            return (Number) (a.floatValue() / b.floatValue());
        }  else if (a instanceof Double) {
            return (Number) (a.doubleValue() / b.doubleValue());
        } else if (a instanceof Long) {
            return (Number) (a.longValue() / b.longValue());
        } else if (a instanceof Short) {
            return (Number) (a.shortValue() / b.shortValue());
        } else {
            throw new ClassCastException();
        }
    }
}
