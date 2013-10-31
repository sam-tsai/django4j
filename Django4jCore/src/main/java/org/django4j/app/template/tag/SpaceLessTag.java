package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class SpaceLessTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endspaceless";
    }

    @Override
    public String getName() {
        return "spaceless";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new SpaceLessNode(rootNode, parentNode, content);
    }
}

class SpaceLessNode extends Node {
    public SpaceLessNode(final RootNode rootNode, final Node parentNode,
                         final String content) {
        super(rootNode, parentNode, content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        String str = renderChild(tEngine, getChildNodes(),
                RenderContext.getChild(ct));
        str = str.replaceAll(">[ \t\n]*<", "><");
        return str;
    }
}
