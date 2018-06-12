package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import org.yardstickframework.BenchmarkConfiguration;

import java.util.Map;

public class SumBenchmark extends SumAbstractBenchmark{
    @Override
    public boolean test(Map<Object, Object> map) throws Exception {
        service().sum(1,2);
        return true;
    }
}
