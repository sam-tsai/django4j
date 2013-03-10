package org.django4j.app.template.tag;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.Template;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.abstract_.ExprValueNode;
import org.django4j.app.template.expr.operator.JustParserOperator;
import org.django4j.app.template.tag.abstract_.SingleTag;

public class SsiTag extends SingleTag {
    @Override
    public String getName() {
        return "ssi";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new SsiNode(rootNode, parentNode, content);
    }
}

class SsiNode extends Node {
    private static final Map<String, Integer> MAPOFOP = new HashMap<String, Integer>();
    static {
        MAPOFOP.put("parsed", 1);
    }

    private final IExprNode                   includeFileExprNode;

    private final boolean                     isParsed;

    public SsiNode(final RootNode rootNode, final Node parentNode,
            final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode exprNode = (new ExprParser(content))
                .parse(new JustParserOperator(MAPOFOP));
        IExprNode exprFileNameNode = exprNode;
        if (exprNode.match("parsed")) {
            isParsed = true;
            exprFileNameNode = exprNode.getANode();
        } else {
            isParsed = false;
        }
        if (exprFileNameNode instanceof ExprValueNode) {
            includeFileExprNode = exprNode;
        } else {
            throw new ParserException(
                    "ssi tag param invalid,you need the includ filename param");
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final String absolutePath = (String) includeFileExprNode.value(tEngine,
                ct);
        final File f = new File(absolutePath);
        if (f.exists() && f.isFile()) {
            final Template t = tEngine.loadTemplate(f);
            return t.render(isParsed ? ct : new RenderContext());
        }
        return "";
    }
}
