package org.django4j.app.template.token;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.CommentNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;

public class CommentToken extends EmptyNameToken {
    private static final long serialVersionUID = 1L;

    public CommentToken(final int tokenKind, int linenum, int colnum,
            final String content) {
        super(tokenKind, linenum, colnum, content);
    }

    @Override
    public Node getNode(final ITemplateEngine tEngine, final RootNode rootNode,
            final Node parentNode) {
        return new CommentNode(rootNode, parentNode, getContent());
    }
}
