package org.django4j.app.template.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

public class RawNode extends Node {
    public RawNode(final RootNode rootNode, final Node parentNode,
            final String content) {
        super(rootNode, parentNode, content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct) {
        return getContent();
    }

}
