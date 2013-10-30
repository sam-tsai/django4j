package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.Template;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.tag.abstract_.SingleTag;

import java.io.File;

public class ExtendsTag extends SingleTag {
    @Override
    public String getName() {
        return "extends";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new ExtendsNode(rootNode, parentNode, content);
    }
}

class ExtendsNode extends Node {
    private IExprNode exprNode = null;

    public ExtendsNode(final RootNode rootNode, final Node parentNode,
                       final String content) throws Exception {
        super(rootNode, parentNode, content);
        exprNode = (new ExprParser(content)).parse();
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final Object obj = exprNode.value(tEngine, ct);
        final RootNode root = getRoot();
        if (obj instanceof String) {
            final String fileName = (String) obj;
            final File f = new File(root.getTempleteFile().getParentFile(),
                    fileName);
            if (f.equals(root.getTempleteFile())) {
                return "";
            }
            final Template t = tEngine.loadTemplate(f);
            root.setParentRootNode(t.getRoot());
        } else if (obj instanceof Template) {
            final Template t = (Template) obj;
            root.setParentRootNode(t.getRoot());
        }
        return "";
    }

}
