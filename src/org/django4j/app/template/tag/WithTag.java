package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.expr.operator.JustParserOperator;
import org.django4j.app.template.tag.abstract_.WithEndTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WithTag extends WithEndTag {
    @Override
    public String getEndToken() {
        return "endwith";
    }

    @Override
    public String getName() {
        return "with";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new WithNode(rootNode, parentNode, content);
    }

}

class WithNode extends Node {
    private final Map<String, IExprNode> paramContext = new HashMap<String, IExprNode>();

    public WithNode(final RootNode rootNode, final Node parentNode,
                    final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode exprNode = (new ExprParser(content))
                .parse(new JustParserOperator());

        if (exprNode instanceof ExprArrayNode) {
            final ExprArrayNode ean = (ExprArrayNode) exprNode;
            final int size = ean.size();
            if (size % 3 == 0) {
                for (int i = 0; i < size; i += 3) {
                    paramContext.put(ean.get(i).getContent(), ean.get(i + 2));
                }
            }
        } else {
            throw new ParserException("with tag param invalid");
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final RenderContext param = RenderContext.getChild(ct);

        for (final Entry<String, IExprNode> entry : paramContext.entrySet()) {
            param.set(entry.getKey(), entry.getValue().value(tEngine, ct));
        }
        return renderChild(tEngine, getChildNodes(), param);
    }
}
