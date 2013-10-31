package org.django4j.app.template.expr.ast.abstract_;

import org.django4j.app.template.expr.IExprNode;

public abstract class ExprAbstractNode implements IExprNode {
    private final String content;

    public ExprAbstractNode(final String content) {
        this.content = content;
    }

    @Override
    public final String getContent() {
        return content;
    }

    @Override
    public final boolean match(final String str) {
        return content.equals(str);
    }

    @Override
    public String toString() {
        return content;
    }
}
