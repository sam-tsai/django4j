package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.FloderNode;
import org.django4j.app.template.ast.IBranchNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.BranchTag;

public class EmptyTag extends BranchTag {

    @Override
    public String getName() {
        return "empty";
    }

    @Override
    public String[] getSkipTokens() {
        return new String[] { "endfor" };
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new EmptyNode(tEngine, rootNode, parentNode, content);
    }
}

class EmptyNode extends FloderNode implements IBranchNode {

    public EmptyNode(final ITemplateEngine tEngine, final RootNode rootNode,
            final Node parentNode, final String content) {
        super(rootNode, parentNode, content);
    }
}
