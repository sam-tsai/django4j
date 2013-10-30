package org.django4j.app.template;

import org.django4j.app.template.token.Token;
import org.django4j.app.template.token.TokenConst;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private int curPos = 0;

    private final char[] is;

    private int length = 0;

    private int tokenKind = -1;

    private int tokenStart = 0;

    private int lineNum = 0;
    private int colNum = 0;

    public Tokenizer(final String inputstream) {
        is = inputstream.toCharArray();
        length = inputstream.length();
        tokenKind = TokenConst.TOKEN_HTML;
    }

    public List<Token> getAllToken() {
        final List<Token> lsToken = new ArrayList<Token>();
        char c = 0;
        lineNum = 1;
        while (curPos < length) {
            c = is[curPos++];
            if (c == '\n') {
                lineNum += 1;
                colNum = 0;
            }
            colNum++;
            if (curPos != length) {
                if (tokenKind != TokenConst.TOKEN_RAW
                        && tokenKind != TokenConst.TOKEN_COMMENT && c == '{') {
                    c = is[curPos];
                    if (c == '{' || c == '%' || c == '#' || c == '-') {
                        newToken(tokenKind, tokenStart, curPos - 1, lineNum,
                                colNum, lsToken);
                        tokenStart = curPos + 1;
                        tokenKind = TokenConst.getTokenKind(c);
                        curPos++;
                    }
                }
                if ((is[curPos] == '}')
                        && ((tokenKind == TokenConst.TOKEN_VARIABLE && c == '}')
                        || (tokenKind == TokenConst.TOKEN_COMMENT && c == '#')
                        || (tokenKind == TokenConst.TOKEN_TAG && c == '%') || (tokenKind == TokenConst.TOKEN_RAW && c == '-'))) {
                    newToken(tokenKind, tokenStart, curPos - 1, lineNum,
                            colNum, lsToken);
                    tokenKind = TokenConst.TOKEN_HTML;
                    tokenStart = curPos + 1;
                }
            }
        }
        newToken(tokenKind, tokenStart, curPos, lineNum, colNum, lsToken);
        return lsToken;
    }

    private void newToken(final int tokenKind, final int start, final int end,
                          final int linenum, final int colnum, final List<Token> lsToken) {
        if (start < end) {
            lsToken.add(Token.newToken(tokenKind, linenum, colnum,
                    String.valueOf(is, start, end - start)));
        }
    }
}
