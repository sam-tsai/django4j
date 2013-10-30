package org.django4j.app.template.expr;

import org.django4j.app.template.expr.token.ExprToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExprTokenizer {
    private static final Set<Character> OPERATOR_CHAR = new HashSet<Character>();

    static {
        OPERATOR_CHAR.add('+');
        OPERATOR_CHAR.add('-');
        OPERATOR_CHAR.add('*');
        OPERATOR_CHAR.add('/');
        OPERATOR_CHAR.add('>');
        OPERATOR_CHAR.add('<');
        OPERATOR_CHAR.add('=');
        OPERATOR_CHAR.add('!');
        OPERATOR_CHAR.add('%');
        OPERATOR_CHAR.add('^');
        OPERATOR_CHAR.add(')');
        OPERATOR_CHAR.add('(');
    }

    private int curPos = 0;

    private char curqoute = '\'';

    private final String is;

    private final int length;

    private final IExprOperator operator;

    private int tokenStart = 0;

    public ExprTokenizer(final String inputstream, final IExprOperator operator) {
        this.is = inputstream;
        this.operator = operator;
        length = inputstream.length();
        curPos = 0;
        tokenStart = 0;
    }

    public List<ExprToken> getAllToken() {
        final List<ExprToken> lsToken = new ArrayList<ExprToken>();
        char c = 0;
        while (curPos < length) {
            c = is.charAt(curPos++);
            if (c == ' ' || c == '\"' || c == '\'' || c == '|' || c == ':'
                    || OPERATOR_CHAR.contains(c)) {
                newToken(lsToken, ExprToken.TOKEN_VAR, tokenStart, curPos - 1);
                tokenStart = curPos;
            } else {
                continue;
            }
            if (c == ' ') {
                skip();
            } else if (c == '\"' || c == '\'') {
                curqoute = c;
                skipString();
                newToken(lsToken, ExprToken.TOKEN_STRING, tokenStart,
                        curPos - 1);
            } else if (c == '|') {
                skipFilter();
                newToken(lsToken, ExprToken.TOKEN_FILTER, tokenStart, curPos);
            } else if (c == ':') {
                skipFilterParam();
                newToken(lsToken, ExprToken.TOKEN_FILTERP, tokenStart, curPos);
            } else if (OPERATOR_CHAR.contains(c)) {
                skipOp();
                newToken(lsToken, ExprToken.TOKEN_OP, tokenStart - 1, curPos);
            }
            tokenStart = curPos;
        }
        newToken(lsToken, ExprToken.TOKEN_VAR, tokenStart, curPos);
        return lsToken;
    }

    private void newToken(final List<ExprToken> lsToken, final int kind,
                          final int start, final int end) {
        if (end <= start) {
            return;
        }
        final String str = is.substring(start, end);
        int tmpKind = kind;
        if (kind == ExprToken.TOKEN_VAR) {
            if (operator != null && operator.isOperator(str)) {
                tmpKind = ExprToken.TOKEN_OP;
            }
        }
        final ExprToken token = ExprToken.newToken(tmpKind, str, operator);
        if (token != null) {
            lsToken.add(token);
        }
    }

    private void skip() {
        while (curPos < length) {
            if (is.charAt(curPos) == ' ') {
                curPos++;
                continue;
            }
            break;
        }
    }

    private void skipFilter() {
        skip();
        char c;
        while (curPos < length) {
            c = is.charAt(curPos);
            if (c != ':' && c != ' ') {
                curPos++;
                continue;
            }
            break;
        }
    }

    private void skipFilterParam() {
        skip();
        char c;
        boolean inString = false;
        while (curPos < length) {
            c = is.charAt(curPos);
            if (c == '\"') {
                inString = !inString;
                curPos++;
                continue;
            }
            if ((c == ' ' || c == '|') && !inString) {
                break;
            } else {
                curPos++;
            }
        }
    }

    private void skipOp() {
        char c;
        while (curPos < length) {
            c = is.charAt(curPos);
            if (OPERATOR_CHAR.contains(c)) {
                curPos++;
            } else {
                break;
            }
        }
    }

    private void skipString() {
        while (curPos < length) {
            if (is.charAt(curPos++) == curqoute) {
                break;
            }
        }
    }
}
