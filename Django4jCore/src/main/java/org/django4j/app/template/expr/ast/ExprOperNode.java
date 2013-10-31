package org.django4j.app.template.expr.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.exception.ExcutueException;
import org.django4j.app.template.expr.IExprOperator;
import org.django4j.app.template.expr.ast.abstract_.ExprCalculateNode;

public class ExprOperNode extends ExprCalculateNode {
    private final IExprOperator operator;

    public ExprOperNode(final String content, final IExprOperator operator) {
        super(content);
        this.operator = operator;
    }

    @Override
    public String toString() {
        return getContent() + "\n" + getANode() + "\n" + getZNode();
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (operator == null) {
            throw new ExcutueException("the operator is empty");
        }
        return operator
                .value(tEngine, getContent(), ct, getANode(), getZNode());
    }
}
