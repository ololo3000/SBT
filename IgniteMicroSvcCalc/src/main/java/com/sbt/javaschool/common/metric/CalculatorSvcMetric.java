package com.sbt.javaschool.common.metric;

public class CalculatorSvcMetric {
    private int hitCntr = 0;
    private int instanceCntr = 0;

    public int getInstanceCntr() {
        return instanceCntr;
    }

    public int getHitCntr() {
        return hitCntr;
    }

    public CalculatorSvcMetric incrHitCntr() {
        ++hitCntr;
        return this;
    }

    public CalculatorSvcMetric incrInstanceCntr() {
        ++instanceCntr;
        return this;
    }

    public CalculatorSvcMetric decrInstanceCntr() {
        --instanceCntr;
        return this;
    }
}
