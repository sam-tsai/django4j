package org.django4j.app.template.expr.operator;

import org.django4j.app.template.expr.IExprOperator;

import java.util.Map;

public abstract class AbstractOperator implements IExprOperator {
    private final Map<String, Integer> opMap;

    public AbstractOperator(final Map<String, Integer> opMap) {
        this.opMap = opMap;
    }

    @Override
    public int getPriority(final String operatorStr) {
        return opMap.containsKey(operatorStr) ? opMap.get(operatorStr)
                : EMPTY_PRIORITY;
    }

    @Override
    public boolean isOperator(final String operatorStr) {
        return opMap.containsKey(operatorStr);
    }

}
