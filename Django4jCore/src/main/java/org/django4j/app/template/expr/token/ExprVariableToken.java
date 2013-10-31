package org.django4j.app.template.expr.token;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprVariableNode;

public class ExprVariableToken extends ExprToken {
    private static final long serialVersionUID = 1L;

    public ExprVariableToken(final String content) {
        super(ExprToken.TOKEN_VAR, content);
    }

    @Override
    public IExprNode getNode() {
        return new ExprVariableNode(getContent());
    }
}
