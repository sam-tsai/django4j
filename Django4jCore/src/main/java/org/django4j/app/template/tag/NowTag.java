package org.django4j.app.template.tag;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprStringNode;
import org.django4j.app.template.expr.operator.JustParserOperator;
import org.django4j.app.template.tag.abstract_.SingleTag;

public class NowTag extends SingleTag {

    @Override
    public String getName() {
        return "now";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new NowNode(rootNode, parentNode, content);
    }

}

class NowNode extends Node {
    private final String formateStr;

    private final SimpleDateFormat sdf;

    public NowNode(final RootNode rootNode, final Node parentNode,
                   final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode exprNode = new ExprParser(content)
                .parse(new JustParserOperator());
        if (!(exprNode instanceof ExprStringNode)) {
            throw new ParserException("now param is need string");
        }
        formateStr = exprNode.getContent();
        sdf = new SimpleDateFormat(formateStr);
    }

    @Override
    public synchronized String render(final ITemplateEngine tEngine,
                                      final RenderContext ct) throws Exception {
        return sdf.format(new Date());
    }

}
