package org.django4j.app.template.tag;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.TemplateConst;
import org.django4j.app.template.ast.FloderNode;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.WithEndTag;

public class AutoescapeTag extends WithEndTag {

    @Override
    public String getEndToken() {
        return "endautoescape";
    }

    @Override
    public String getName() {
        return "autoescape";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content) {
        return new AutoescapeNode(tEngine, rootNode, parentNode, content);
    }

}

class AutoescapeNode extends FloderNode {
    private static final String ON = "on";
    private final boolean bEscape;

    public AutoescapeNode(final ITemplateEngine tEngine,
                          final RootNode rootNode, final Node parentNode, final String content) {
        super(rootNode, parentNode, content);
        bEscape = ON.equalsIgnoreCase(content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        ct.set(TemplateConst.$AUTOESCAPE, bEscape);
        return super.render(tEngine, ct);
    }
}
