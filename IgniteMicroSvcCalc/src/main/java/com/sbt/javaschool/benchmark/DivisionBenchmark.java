package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.apache.ignite.yardstick.jdbc.AbstractJdbcBenchmark;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class DivisionBenchmark extends DivisionAbstractBenchmark {

    @Override
    public boolean test(Map<Object, Object> map) throws Exception {
        service().divide(getRandomInt(1, 100),getRandomInt(1, 100));
        return true;
    }

    private int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
