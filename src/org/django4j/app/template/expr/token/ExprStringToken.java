package org.django4j.app.template.expr.token;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprStringNode;

public class ExprStringToken extends ExprToken {
    private static final long serialVersionUID = 1L;

    public ExprStringToken(final String content) {
        super(ExprToken.TOKEN_STRING, content);
    }

    @Override
    public IExprNode getNode() {
        return new ExprStringNode(getContent());
    }
}
