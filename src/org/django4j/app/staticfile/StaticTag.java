package org.django4j.app.staticfile;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.SingleTag;

public class StaticTag extends SingleTag {
    @Override
    public String getName() {
        return "static";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new StaticNode(rootNode, parentNode, content);
    }

}

class StaticNode extends Node {

    public StaticNode(final RootNode rootNode, final Node parentNode,
            final String content) {
        super(rootNode, parentNode, content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
