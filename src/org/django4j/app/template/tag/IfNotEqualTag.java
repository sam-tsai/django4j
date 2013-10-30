package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class IfNotEqualTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endifnotequal";
    }

    @Override
    public String getName() {
        return "ifnotequal";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new IfNotEqualNode(rootNode, parentNode, content);
    }

}

class IfNotEqualNode extends Node {
    private final ExprArrayNode ean;

    public IfNotEqualNode(final RootNode rootNode, final Node parentNode,
                          final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode en = (new ExprParser(content)).parse();
        if (!(en instanceof ExprArrayNode)) {
            throw new ParserException("the ifnotequal tag param invalid");
        }
        ean = (ExprArrayNode) en;
        if (ean.size() < 2) {
            throw new ParserException("the ifnotequal tag param size must >= 2");
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        ean.resetIter();
        final Object obj = ean.next().value(tEngine, ct);
        boolean isEqual = false;
        while (ean.hasNext()) {
            if (obj.equals(ean.next().value(tEngine, ct))) {
                isEqual = true;
                break;
            }
        }
        if (!isEqual) {
            return renderTrunkChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct));
        } else {
            return renderBranchChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct));
        }
    }
}
