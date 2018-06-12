package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.subtractprocessor.common.SubtractionService;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.yardstickframework.BenchmarkConfiguration;

public abstract class SubstractionAbstracrBenchmark extends IgniteAbstractBenchmark {
    private SubtractionService substractionService;

    @Override
    public void setUp(BenchmarkConfiguration cfg) throws Exception {
        super.setUp(cfg);
        substractionService = ignite()
                .services()
                .serviceProxy(SubtractionService.SERVICE_NAME, SubtractionService.class, false);
    }

    public SubtractionService service() {
        return substractionService;
    }

}
