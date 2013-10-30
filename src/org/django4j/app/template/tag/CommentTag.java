package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.CommentNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class CommentTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endcomment";
    }

    @Override
    public String getName() {
        return "comment";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content) {
        return new CommentNode(rootNode, parentNode, content);
    }

}
