package org.django4j.app.template.token;

import java.io.Serializable;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.Parser;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;

public abstract class Token implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static Token newToken(final int tokenKind, final int linenum,
                                 final int colnum, final String content) {
        switch (tokenKind) {
            case TokenConst.TOKEN_VARIABLE:
                return new VariableToken(tokenKind, linenum, colnum, content);
            case TokenConst.TOKEN_TAG:
                return new TagToken(tokenKind, linenum, colnum, content);
            case TokenConst.TOKEN_COMMENT:
                return new CommentToken(tokenKind, linenum, colnum, content);
            case TokenConst.TOKEN_RAW:
                return new RawToken(tokenKind, linenum, colnum, content);
            case TokenConst.TOKEN_HTML:
                return new HtmlToken(tokenKind, linenum, colnum, content);
            default:
                return null;
        }
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }

    private final String content;

    private final int tokenKind;
    private final int lineNum;
    private final int colNum;

    public Token(final int tokenKind, int linenum, int colnum,
                 final String content) {
        this.content = content.trim();
        this.tokenKind = tokenKind;
        this.lineNum = linenum;
        this.colNum = colnum;
    }

    public abstract Node getNode(ITemplateEngine tEngine, RootNode rootNode,
                                 Node parentNode) throws Exception;

    public abstract String getTokenName();

    public final void parseNode(final ITemplateEngine tEngine,
                                final RootNode root, final Node parent, final Parser parser)
            throws Exception {
        final Node selfNode = getNode(tEngine, root, parent);
        if (selfNode == null) {
            return;
        }
        parseSubNode(tEngine, root, selfNode, parser);
    }

    public void parseSubNode(final ITemplateEngine tEngine,
                             final RootNode root, final Node parent, final Parser parser)
            throws Exception {
    }

    protected String getContent() {
        return content;
    }

    protected int getTokenKind() {
        return tokenKind;
    }

}
