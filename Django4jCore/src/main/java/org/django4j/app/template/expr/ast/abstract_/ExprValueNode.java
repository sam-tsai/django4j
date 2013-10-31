package org.django4j.app.template.expr.ast.abstract_;

import org.django4j.app.template.expr.IExprNode;

public abstract class ExprValueNode extends ExprAbstractNode {

    public ExprValueNode(final String content) {
        super(content);
    }

    @Override
    public final IExprNode getANode() {
        return null;
    }

    @Override
    public final IExprNode getZNode() {
        return null;
    }

    @Override
    public final boolean hasANode() {
        return false;
    }

    @Override
    public final boolean hasZNode() {
        return false;
    }

    @Override
    public final void setANode(final IExprNode aNode) {

    }

    @Override
    public final void setZNode(final IExprNode zNode) {

    }

}
