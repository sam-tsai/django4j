package org.django4j.app.template.expr.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.ast.abstract_.ExprValueNode;

public class ExprStringNode extends ExprValueNode {
    public ExprStringNode(final String value) {
        super(value);
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct) {
        return getContent();
    }
}
