package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.sumprocessor.common.SumService;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.yardstickframework.BenchmarkConfiguration;

public abstract class SumAbstractBenchmark extends IgniteAbstractBenchmark {
    private SumService sumService;

    @Override
    public void setUp(BenchmarkConfiguration cfg) throws Exception {
        super.setUp(cfg);
        sumService = ignite()
                .services()
                .serviceProxy(SumService.SERVICE_NAME, SumService.class, false);
    }

    public SumService service() {
        return sumService;
    }

}
