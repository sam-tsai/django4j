package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprVariableNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class IfChangedTag extends WithEndTag {
    @Override
    public String getEndToken() {
        return "endifchange";
    }

    @Override
    public String getName() {
        return "ifchange";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new IfChangedNode(rootNode, parentNode, content);
    }
}

class IfChangedNode extends Node {
    private final IExprNode exprNode;

    private final boolean hasParam;

    private Object lastObj = null;

    public IfChangedNode(final RootNode rootNode, final Node parentNode,
                         final String content) throws Exception {
        super(rootNode, parentNode, content);

        if (content == null || content.isEmpty()) {
            hasParam = false;
            exprNode = null;
        } else {
            exprNode = (new ExprParser(content)).parse();
            if (exprNode instanceof ExprVariableNode) {
                hasParam = true;
            } else {
                throw new ParserException("ifchanged filter param must be var");
            }
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (hasParam) {
            final Object curObj = exprNode.value(tEngine, ct);
            final Object tmpObj = lastObj;
            lastObj = curObj;
            if ((tmpObj == null && curObj != null)
                    || (tmpObj != null && !tmpObj.equals(curObj))) {
                return renderTrunkChild(tEngine, getChildNodes(),
                        RenderContext.getChild(ct));
            } else {
                return renderBranchChild(tEngine, getChildNodes(),
                        RenderContext.getChild(ct));
            }
        } else {
            final String curObj = renderTrunkChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct));
            final Object tmpObj = lastObj;
            lastObj = curObj;
            if ((tmpObj == null && curObj != null)
                    || (tmpObj != null && !tmpObj.equals(curObj))) {
                return curObj;
            } else {
                return renderBranchChild(tEngine, getChildNodes(),
                        RenderContext.getChild(ct));
            }
        }
    }

}
