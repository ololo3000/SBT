package com.sbt.javaschool.services.expressionprocessor;

import com.sbt.javaschool.common.expression.Expression;
import com.sbt.javaschool.common.expression.ExpressionUnit;
import com.sbt.javaschool.common.metric.CalculatorSvcMetric;
import com.sbt.javaschool.services.divideprocessor.common.DivisionService;
import com.sbt.javaschool.services.expressionprocessor.common.ExpressionService;
import com.sbt.javaschool.services.multiplyprocessor.common.MultiplicationService;
import com.sbt.javaschool.services.subtractprocessor.common.SubtractionService;
import com.sbt.javaschool.services.sumprocessor.common.SumService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;

import java.util.List;

public class ExpressionServiceImpl implements ExpressionService {
    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<String, CalculatorSvcMetric> cache;

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Initializing ExpressionService on node:" + ignite.cluster().localNode());
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing ExpressionService on node:" + ignite.cluster().localNode());
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping ExpressionService on node:" + ignite.cluster().localNode());
    }



    @Override
    public Number calculate(Expression expression) {
        MultiplicationService multiplicationService = ignite
                .services()
                .serviceProxy(MultiplicationService.SERVICE_NAME, MultiplicationService.class, false);
        SumService sumService = ignite
                .services()
                .serviceProxy(SumService.SERVICE_NAME, SumService.class, false);
        SubtractionService subtractionService = ignite
                .services()
                .serviceProxy(SubtractionService.SERVICE_NAME, SubtractionService.class, false);
        DivisionService divisionService = ignite
                .services()
                .serviceProxy(DivisionService.SERVICE_NAME, DivisionService.class, false);
        if (divisionService == null) {
            throw new RuntimeException("divisionServise unavailable");
        }
        if (subtractionService == null) {
            throw new RuntimeException("substractionServise unavailable");
        }
        if (sumService == null) {
            throw new RuntimeException("sumServise unavailable");
        }
        if (multiplicationService == null) {
            throw new RuntimeException("mu;tiplicationServise unavailable");
        }

        List<ExpressionUnit> expressionUnits = expression.getExpressionUnits();
        Number lastStepResult = expression.getInitialNumber();

        for (ExpressionUnit expressionUnit : expressionUnits) {
            switch (expressionUnit.getAction()) {
                case MULT: {
                    lastStepResult = multiplicationService.multiply(lastStepResult, expressionUnit.getArg());
                }
                break;
                case DIV: {
                    lastStepResult = divisionService.divide(lastStepResult, expressionUnit.getArg());
                }
                break;
                case PLUS: {
                    lastStepResult = sumService.sum(lastStepResult, expressionUnit.getArg());
                }
                break;
                case MINUS: {
                    lastStepResult = subtractionService.subtract(lastStepResult, expressionUnit.getArg());
                }
            }
        }

        return lastStepResult;
    }
}
