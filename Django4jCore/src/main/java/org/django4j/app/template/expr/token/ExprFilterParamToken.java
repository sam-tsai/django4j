package org.django4j.app.template.expr.token;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprStringNode;

public class ExprFilterParamToken extends ExprToken {
    private static final long serialVersionUID = 1L;

    public ExprFilterParamToken(final String content) {
        super(ExprToken.TOKEN_FILTERP, content);
    }

    @Override
    public IExprNode getNode() {
        return new ExprStringNode(getContent());
    }

}
