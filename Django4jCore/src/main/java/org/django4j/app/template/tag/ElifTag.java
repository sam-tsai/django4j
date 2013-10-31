package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.IBranchNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.BranchTag;

public class ElifTag extends BranchTag {

    @Override
    public String getName() {
        return "elif";
    }

    @Override
    public String[] getSkipTokens() {
        return new String[]{"endif"};
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new ElifNode(rootNode, parentNode, content);
    }
}

class ElifNode extends IfNode implements IBranchNode {
    public ElifNode(final RootNode rootNode, final Node parentNode,
                    final String content) throws Exception {
        super(rootNode, parentNode, content);
    }
}
