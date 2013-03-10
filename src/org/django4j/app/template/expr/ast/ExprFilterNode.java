package org.django4j.app.template.expr.ast;

import org.django4j.app.template.IFilter;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.abstract_.ExprCalculateNode;

public class ExprFilterNode extends ExprCalculateNode {
    public ExprFilterNode(final String content) {
        super(content);
    }

    public Object value(final ITemplateEngine tEngine,
            final IExprNode exprNode, final RenderContext ct) throws Exception {
        final IFilter filter = tEngine.getFilter(getContent());
        if (exprNode == null) {
            throw new Exception("filter operation error,this need a operand");
        }
        final Object aVal = exprNode.value(tEngine, ct);
        String param = "";
        if (hasZNode()) {
            param = String.valueOf(getZNode().value(tEngine, ct));
        }
        return filter.doFilter(aVal, param);
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        return value(tEngine, getANode(), ct);
    }
}
