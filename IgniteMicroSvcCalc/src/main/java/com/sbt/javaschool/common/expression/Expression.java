package com.sbt.javaschool.common.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Expression {
    private Number initialNumber;
    private List<ExpressionUnit> expressionUnits = new ArrayList<>();

    public Expression(Number initialNumber) {
        this.initialNumber = initialNumber;
    }

    public Expression plus(Number arg) {
        expressionUnits.add(new ExpressionUnit(ArithmeticalAction.PLUS, arg));
        return  this;
    }

    public Expression minus(Number arg) {
        expressionUnits.add(new ExpressionUnit(ArithmeticalAction.MINUS, arg));
        return  this;
    }

    public Expression mult(Number arg) {
        expressionUnits.add(new ExpressionUnit(ArithmeticalAction.MULT, arg));
        return  this;
    }

    public Expression div(Number arg) {
        expressionUnits.add(new ExpressionUnit(ArithmeticalAction.DIV, arg));
        return  this;
    }

    public List<ExpressionUnit> getExpressionUnits() {
        return Collections.unmodifiableList(expressionUnits);
    }

    public Number getInitialNumber() {
        return initialNumber;
    }
}
