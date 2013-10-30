package org.django4j.app.template.token;

public abstract class EmptyNameToken extends Token {

    /**
     *
     */
    private static final long serialVersionUID = -1214288358950193477L;

    public EmptyNameToken(final int tokenKind, int linenum, int colnum,
                          final String content) {
        super(tokenKind, linenum, colnum, content);
    }

    @Override
    public final String getTokenName() {
        return "";
    }

}
