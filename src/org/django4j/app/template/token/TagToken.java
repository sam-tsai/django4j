package org.django4j.app.template.token;

import org.django4j.app.template.ITag;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.Parser;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RawNode;
import org.django4j.app.template.ast.RootNode;

public final class TagToken extends Token {
    private static final long serialVersionUID = 1L;

    private ITag tag = null;

    private final String tagContent;

    private final String tagName;

    public TagToken(final int tokenKind, int linenum, int colnum,
                    final String content) {
        super(tokenKind, linenum, colnum, content);
        final String[] content_split = content.trim().split(" ");
        tagName = content_split[0];
        tagContent = content.substring(tagName.length()).trim();
    }

    @Override
    public Node getNode(final ITemplateEngine tEngine, final RootNode rootNode,
                        final Node parentNode) throws Exception {
        tag = tEngine.getTag(tagName);
        if (tag == null) {
            return new RawNode(rootNode, parentNode, getContent());
        }
        return tag.parserNode(tEngine, rootNode, parentNode, tagContent);
    }

    @Override
    public String getTokenName() {
        return tagName;
    }

    @Override
    public void parseSubNode(final ITemplateEngine tEngine,
                             final RootNode root, final Node parent, final Parser parser)
            throws Exception {
        if (tag == null) {
            return;
        }
        final String endTokenName = tag.getEndToken();
        final String[] skipTokenName = tag.getSkipTokens();
        boolean hasSkipToken = false;
        if ((skipTokenName != null && skipTokenName.length != 0)) {
            hasSkipToken = true;
        }

        if (!hasSkipToken && (endTokenName == null || endTokenName.isEmpty())) {
            return;
        }

        while (parser.hasNext()) {
            if (hasSkipToken && parser.tryMatch(skipTokenName)) {
                return;
            }
            final Token token = parser.next();
            if (token.getTokenName().equals(endTokenName)) {
                return;
            }
            token.parseNode(tEngine, root, parent, parser);
        }
    }
}
