package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.tag.abstract_.SingleTag;
import org.django4j.app.template.utils.reflect.ObjectUtils;

public class FirstOfTag extends SingleTag {

    @Override
    public String getName() {
        return "firstof";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new FirstOfNode(rootNode, parentNode, content);
    }

}

class FirstOfNode extends Node {
    private final ExprArrayNode ean;

    public FirstOfNode(final RootNode rootNode, final Node parentNode,
            final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode en = (new ExprParser(content)).parse();
        if (!(en instanceof ExprArrayNode)) {
            throw new ParserException("the firstof tag param invalid");
        }
        ean = (ExprArrayNode) en;
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        ean.resetIter();
        while (ean.hasNext()) {
            final IExprNode en = ean.next();
            final Object obj = en.value(tEngine, ct);
            if (ObjectUtils.boolVal(obj)) {
                return String.valueOf(obj);
            }
        }
        return "";
    }

}
