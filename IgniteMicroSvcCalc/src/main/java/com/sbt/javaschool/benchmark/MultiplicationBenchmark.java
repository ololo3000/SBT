package com.sbt.javaschool.benchmark;

import java.util.Map;

public class MultiplicationBenchmark extends MultiplicationAbstractBenchmark {
    @Override
    public boolean test(Map<Object, Object> map) throws Exception {
        service().multiply(1,2);
        return true;
    }
}
