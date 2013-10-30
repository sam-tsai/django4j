package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.Template;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ExcutueException;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.expr.ast.ExprOperNode;
import org.django4j.app.template.expr.ast.ExprStringNode;
import org.django4j.app.template.expr.ast.ExprVariableNode;
import org.django4j.app.template.expr.operator.JustParserOperator;
import org.django4j.app.template.tag.abstract_.SingleTag;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IncludeTag extends SingleTag {

    @Override
    public String getName() {
        return "include";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new IncludeNode(rootNode, parentNode, content);
    }

}

class IncludeNode extends Node {
    private static final Map<String, Integer> MAP_OP_INCLUDE = new HashMap<String, Integer>();
    private static final String ONLY = "only";
    private static final String WITH = "with";

    static {
        MAP_OP_INCLUDE.put(WITH, 1);
        MAP_OP_INCLUDE.put(ONLY, 2);
    }

    private IExprNode fileNameExprNode = null;

    private boolean isOnly = false;

    private final Map<String, IExprNode> paramContext = new HashMap<String, IExprNode>();

    public IncludeNode(final RootNode rootNode, final Node parentNode,
                       final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode exprNode = new ExprParser(content)
                .parse(new JustParserOperator(MAP_OP_INCLUDE));
        if (exprNode instanceof ExprStringNode
                || exprNode instanceof ExprVariableNode) {
            fileNameExprNode = exprNode;
        } else if (exprNode instanceof ExprOperNode) {
            final String op = exprNode.getContent();
            if (op.equals(WITH)) {
                final IExprNode aexprNode = exprNode.getANode();
                if (aexprNode instanceof ExprStringNode
                        || aexprNode instanceof ExprVariableNode) {
                    fileNameExprNode = aexprNode;
                } else {
                    throw new ParserException(
                            "include tag param invalid,you need the includ filename param");
                }
                IExprNode zexprNode = exprNode.getZNode();
                if (zexprNode instanceof ExprOperNode
                        && zexprNode.getContent().equals(ONLY)) {
                    isOnly = true;
                    zexprNode = zexprNode.getANode();
                }
                if (zexprNode instanceof ExprArrayNode) {
                    final ExprArrayNode ean = (ExprArrayNode) zexprNode;
                    final int size = ean.size();
                    if (size % 3 == 0) {
                        for (int i = 0; i < size; i += 3) {
                            paramContext.put(ean.get(i).getContent(),
                                    ean.get(i + 2));
                        }
                    }
                } else {
                    throw new ParserException("include tag param invalid");
                }
            }
        } else {
            throw new ParserException("include tag param invalid");
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final Object obj = fileNameExprNode.value(tEngine, ct);
        final RootNode root = getRoot();
        Template t = null;
        if (obj instanceof String) {
            final String fileName = (String) obj;
            final File f = new File(root.getTempleteFile().getParentFile(),
                    fileName);
            t = tEngine.loadTemplate(f);
        } else if (obj instanceof Template) {
            t = (Template) obj;
        } else {
            throw new ExcutueException("include file is invalid");
        }
        RenderContext param = ct;
        if (isOnly) {
            param = new RenderContext();
        }
        for (final Entry<String, IExprNode> entry : paramContext.entrySet()) {
            param.set(entry.getKey(), entry.getValue().value(tEngine, ct));
        }
        return t.render(param);
    }
}
