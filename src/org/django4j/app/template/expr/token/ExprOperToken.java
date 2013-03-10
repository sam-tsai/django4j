package org.django4j.app.template.expr.token;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.IExprOperator;
import org.django4j.app.template.expr.ast.ExprOperNode;

public class ExprOperToken extends ExprToken {
    private static final long   serialVersionUID = 1L;

    private final IExprOperator operator;

    public ExprOperToken(final String content, final IExprOperator operator) {
        super(ExprToken.TOKEN_OP, content);
        this.operator = operator;
    }

    @Override
    public IExprNode getNode() {
        return new ExprOperNode(getContent(), operator);
    }
}
