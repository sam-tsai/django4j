package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.FloderNode;
import org.django4j.app.template.ast.IBranchNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.BranchTag;

public class ElseTag extends BranchTag {

    @Override
    public String getName() {
        return "else";
    }

    @Override
    public String[] getSkipTokens() {
        return new String[] { "endif", "endifequal", "endifchanged",
                "endifnotequal" };
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content) {
        return new ElseNode(tEngine, rootNode, parentNode, content);
    }
}

class ElseNode extends FloderNode implements IBranchNode {

    public ElseNode(final ITemplateEngine tEngine, final RootNode rootNode,
            final Node parentNode, final String content) {
        super(rootNode, parentNode, content);
    }
}
