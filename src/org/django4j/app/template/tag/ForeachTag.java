package org.django4j.app.template.tag;

import org.django4j.DjangoConst;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class ForeachTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endforeach";
    }

    @Override
    public String getName() {
        return "foreach";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content) {
        return new ForNode(rootNode, parentNode, DjangoConst.$DEFAULT_VAR
                + " in " + content);
    }

}
