package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.IVariable;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.expr.ast.ExprVariableNode;
import org.django4j.app.template.tag.abstract_.SingleTag;

public class CycleTag extends SingleTag {

    @Override
    public String getName() {
        return "cycle";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new CycleNode(rootNode, parentNode, content);
    }

}

class CycleNode extends Node implements IVariable {
    private static final String AS     = "as";

    private static final String SILENT = "silent";

    private final ExprArrayNode ean;

    private int                 index  = 0;

    private final boolean       isSilent;

    private final String        varName;

    public CycleNode(final RootNode rootNode, final Node parentNode,
            final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode en = (new ExprParser(content)).parse();
        if (!(en instanceof ExprArrayNode)) {
            throw new ParserException("the cycle tag param invalid");
        }
        ean = (ExprArrayNode) en;
        int eanSize = ean.size();
        if (eanSize <= 3) {
            isSilent = false;
            varName = null;
            return;
        }
        boolean bSilent = false;
        final IExprNode silentNode = ean.get(eanSize - 1);
        if (silentNode instanceof ExprVariableNode && silentNode.match(SILENT)) {
            eanSize--;
            bSilent = true;
        }
        final IExprNode asNode = ean.get(eanSize - 2);
        final IExprNode varNode = ean.get(eanSize - 1);
        if (asNode instanceof ExprVariableNode && asNode.match(AS)
                && varNode instanceof ExprVariableNode) {
            isSilent = bSilent;
            varName = varNode.getContent();
            ean.remove(ean.size() - 1);
            ean.remove(ean.size() - 1);
            if (isSilent) {
                ean.remove(ean.size() - 1);
            }
        } else {
            isSilent = false;
            varName = null;
        }
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (varName != null) {
            ct.set(varName, this);
        }
        if (isSilent) {
            return "";
        }

        return String.valueOf(value(null, ct));
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (index >= ean.size()) {
            index = 0;
        }
        return ean.get(index++).value(tEngine, ct);
    }
}
