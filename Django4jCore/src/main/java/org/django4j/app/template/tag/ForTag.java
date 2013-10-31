package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;
import org.django4j.app.template.utils.reflect.DotExpr;
import org.django4j.app.template.utils.reflect.IterObject;

public class ForTag extends WithEndTag {
    @Override
    public String getEndToken() {
        return "endfor";
    }

    @Override
    public String getName() {
        return "for";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content) {
        return new ForNode(rootNode, parentNode, content);
    }
}

class ForNode extends Node {
    private static final String $FOR_INDEX_0 = "$for_index_0";
    private static final String $FOR_INDEX_1 = "$for_index_1";

    private String expContent = "";

    private String varName = "";

    public ForNode(final RootNode rootNode, final Node parentNode,
                   final String content) {
        super(rootNode, parentNode, content);
        final String[] content_split = content.split(" in ");
        varName = content_split[0];
        expContent = content_split[1];
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final StringBuilder sb = new StringBuilder();
        final IterObject iter = new IterObject(DotExpr.getValue(expContent, ct));
        int i = 0;
        final RenderContext param = RenderContext.getChild(ct);
        while (iter.hasNext()) {
            final Object obj = iter.next();
            param.set(varName, obj);
            param.set($FOR_INDEX_0, i++);
            param.set($FOR_INDEX_1, i);
            sb.append(renderTrunkChild(tEngine, getChildNodes(), param));
        }
        if (i == 0) {
            sb.append(renderBranchChild(tEngine, getChildNodes(), param));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ForNode [varName=" + varName + ", expContent=" + expContent
                + "]";
    }

}
