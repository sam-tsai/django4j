package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.operator.LogicOperator;
import org.django4j.app.template.tag.abstract_.WithEndTag;
import org.django4j.app.template.utils.reflect.ObjectUtils;

public class IfTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endif";
    }

    @Override
    public String getName() {
        return "if";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new IfNode(rootNode, parentNode, content);
    }

}

class IfNode extends Node {
    private IExprNode conExpr = null;

    public IfNode(final RootNode rootNode, final Node parentNode,
                  final String content) throws Exception {
        super(rootNode, parentNode, content);
        conExpr = (new ExprParser(content)).parse(new LogicOperator());
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final StringBuilder sb = new StringBuilder();
        if (ObjectUtils.boolVal(conExpr.value(tEngine, ct))) {
            sb.append(renderTrunkChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct)));
        } else {
            sb.append(renderBranchChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct)));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "IfNode [conditionExpr=" + getContent() + "]";
    }
}
