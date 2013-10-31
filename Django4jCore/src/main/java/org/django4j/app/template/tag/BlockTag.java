package org.django4j.app.template.tag;

import org.django4j.app.template.ITag;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.TemplateConst;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;

public class BlockTag implements ITag {

    @Override
    public String getEndToken() {
        return "endblock";
    }

    @Override
    public String getName() {
        return "block";
    }

    @Override
    public String[] getSkipTokens() {
        return null;
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content) {
        return new BlockNode(rootNode, parentNode, content);
    }

}

class BlockNode extends Node {
    private final String blockID;

    public BlockNode(final RootNode rootNode, final Node parentNode,
                     final String content) {
        super(rootNode, parentNode, content);
        blockID = TemplateConst.$BLOCK_PREX + content;
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (ct.has(blockID)) {
            return ct.get(blockID);
        } else {
            final String str = renderChild(tEngine, getChildNodes(),
                    RenderContext.getChild(ct));
            ct.set2Top(blockID, str);
            return str;
        }
    }
}
