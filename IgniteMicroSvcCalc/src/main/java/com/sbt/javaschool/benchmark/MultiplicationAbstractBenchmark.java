package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.multiplyprocessor.common.MultiplicationService;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.yardstickframework.BenchmarkConfiguration;

public abstract class MultiplicationAbstractBenchmark extends IgniteAbstractBenchmark {
    private MultiplicationService multiplicationService;

    @Override
    public void setUp(BenchmarkConfiguration cfg) throws Exception {
        super.setUp(cfg);
        multiplicationService = ignite()
                .services()
                .serviceProxy(MultiplicationService.SERVICE_NAME, MultiplicationService.class, false);
    }

    public MultiplicationService service() {
        return multiplicationService;
    }

}
