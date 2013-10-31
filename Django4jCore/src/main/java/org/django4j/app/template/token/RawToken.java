package org.django4j.app.template.token;

import java.util.HashMap;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RawNode;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.utils.StringUtils;

public final class RawToken extends EmptyNameToken {
    private static final Map<Character, String> ESCAPEMAP = new HashMap<Character, String>();
    private static final long serialVersionUID = 1L;

    static {
        ESCAPEMAP.put('&', "&amp");
        ESCAPEMAP.put('<', "&lt");
        ESCAPEMAP.put('>', "&gt");
        ESCAPEMAP.put('\'', "&#39");
        ESCAPEMAP.put('"', "&quot");
        ESCAPEMAP.put(' ', "&nbsp;");
        ESCAPEMAP.put('\n', "<br/>");
        ESCAPEMAP.put('\t', "&nbsp;&nbsp;&nbsp;&nbsp;");
    }

    public RawToken(final int tokenKind, int linenum, int colnum,
                    final String content) {
        super(tokenKind, linenum, colnum, content);
    }

    @Override
    public Node getNode(final ITemplateEngine tEngine, final RootNode rootNode,
                        final Node parentNode) {
        return new RawNode(rootNode, parentNode, StringUtils.convertString(
                getContent(), ESCAPEMAP));
    }
}
