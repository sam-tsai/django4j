package org.django4j.app.template.expr.token;

import java.io.Serializable;

import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.IExprOperator;

public abstract class ExprToken implements Serializable {
    public static final int   TOKEN_EMPTY      = 0;

    public static final int   TOKEN_FILTER     = 2;

    public static final int   TOKEN_FILTERP    = 3;

    public static final int   TOKEN_OP         = 5;

    public static final int   TOKEN_STRING     = 1;

    public static final int   TOKEN_VAR        = 4;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static ExprToken newToken(final int tokenKind, final String content,
            final IExprOperator operator) {
        switch (tokenKind) {
        case ExprToken.TOKEN_FILTER:
            return new ExprFilterToken(content);
        case ExprToken.TOKEN_FILTERP:
            return new ExprFilterParamToken(content);
        case ExprToken.TOKEN_OP:
            return new ExprOperToken(content, operator);
        case ExprToken.TOKEN_STRING:
            return new ExprStringToken(content);
        case ExprToken.TOKEN_VAR:
            return new ExprVariableToken(content);
        default:
            return null;
        }
    }

    private final String content;

    private final int    tokenKind;

    public ExprToken(final int tokenKind, final String content) {
        this.content = ((content != null) ? content.trim() : "");
        this.tokenKind = tokenKind;
    }

    public String getContent() {
        return content;
    }

    public abstract IExprNode getNode() throws Exception;

    public int getTokenKind() {
        return tokenKind;
    }

    @Override
    public String toString() {
        return getTokenKind() + ":" + getContent();
    }
}
