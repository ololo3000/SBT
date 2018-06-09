package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import org.apache.ignite.Ignite;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.yardstickframework.BenchmarkConfiguration;

public abstract class DivisionAbstractBenchmark extends IgniteAbstractBenchmark {
    private DivisionService divisionService;

    @Override
    public void setUp(BenchmarkConfiguration cfg) throws Exception {
        super.setUp(cfg);
        divisionService = ignite()
                .services()
                .serviceProxy(DivisionService.SERVICE_NAME, DivisionService.class, false);
    }

    public DivisionService service() {
        return divisionService;
    }

}
