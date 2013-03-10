package org.django4j.app.template.token;

public class TokenConst {
    public static final int TOKEN_COMMENT  = 3;

    public static final int TOKEN_EMPTY    = 0;

    public static final int TOKEN_HTML     = 5;

    public static final int TOKEN_RAW      = 4;

    public static final int TOKEN_TAG      = 2;

    public static final int TOKEN_VARIABLE = 1;

    public static int getTokenKind(final char c) {
        switch (c) {
        case '{':
            return TOKEN_VARIABLE;
        case '%':
            return TOKEN_TAG;
        case '#':
            return TOKEN_COMMENT;
        case '-':
            return TOKEN_RAW;
        default:
            return TOKEN_EMPTY;
        }
    }
}
