package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprFilterNode;
import org.django4j.app.template.expr.ast.ExprStringNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class FilterTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endfilter";
    }

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new FilterNode(rootNode, parentNode, content);
    }

}

class FilterNode extends Node {
    private final ExprFilterNode exprFilterNode;

    public FilterNode(final RootNode rootNode, final Node parentNode,
                      final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode en = (new ExprParser("|" + content)).parse(null);
        if (!(en instanceof ExprFilterNode)) {
            throw new ParserException("this filter param is invalid" + content);
        }
        exprFilterNode = (ExprFilterNode) en;
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final String str = renderChild(tEngine, getChildNodes(),
                RenderContext.getChild(ct));
        return String.valueOf(exprFilterNode.value(tEngine, new ExprStringNode(
                str), ct));
    }
}
