package org.django4j.app.template.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

public class FloderNode extends Node {
    public FloderNode(final RootNode rootNode, final Node parentNode,
                      final String content) {
        super(rootNode, parentNode, content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        return renderChild(tEngine, getChildNodes(), RenderContext.getChild(ct));
    }
}
