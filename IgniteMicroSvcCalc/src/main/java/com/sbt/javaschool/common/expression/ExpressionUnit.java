package com.sbt.javaschool.common.expression;

public class ExpressionUnit {
    private ArithmeticalAction action;
    private Number arg;

    public ExpressionUnit(ArithmeticalAction action, Number arg) {
        this.action = action;
        this.arg = arg;
    }

    public Number getArg() {
        return arg;
    }

    public ArithmeticalAction getAction() {
        return action;
    }
}
