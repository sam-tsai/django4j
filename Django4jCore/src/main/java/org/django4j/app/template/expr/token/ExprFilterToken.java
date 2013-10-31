package org.django4j.app.template.expr.token;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprFilterNode;

public class ExprFilterToken extends ExprToken {
    private static final long serialVersionUID = 1L;

    public ExprFilterToken(final String content) {
        super(ExprToken.TOKEN_FILTER, content);
    }

    @Override
    public IExprNode getNode() throws Exception {
        return new ExprFilterNode(getContent());
    }
}
