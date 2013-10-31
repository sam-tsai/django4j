package org.django4j.app.template.expr.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.ast.abstract_.ExprValueNode;
import org.django4j.app.template.utils.reflect.DotExpr;

public class ExprVariableNode extends ExprValueNode {
    public ExprVariableNode(final String value) {
        super(value);
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        return DotExpr.getValue(getContent(), ct);
    }
}
