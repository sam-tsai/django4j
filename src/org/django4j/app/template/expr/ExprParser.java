package org.django4j.app.template.expr;

import java.util.List;

import org.django4j.app.template.expr.ast.ExprArrayNode;
import org.django4j.app.template.expr.token.ExprFilterToken;
import org.django4j.app.template.expr.token.ExprToken;

public class ExprParser {

    private static int getLowestPriorityOpToken(
            final List<ExprToken> tokenList, final IExprOperator operator) {
        int lpriority = IExprOperator.MAX_PRIORITY;
        int index = -1;
        for (int i = tokenList.size() - 1; i >= 0; i--) {
            final ExprToken tk = tokenList.get(i);
            int p = IExprOperator.EMPTY_PRIORITY;
            if (tk instanceof ExprFilterToken) {
                p = IExprOperator.FILTER_PRIORITY;
            } else if (operator != null) {
                p = operator.getPriority(tk.getContent());
            }
            if (p != IExprOperator.EMPTY_PRIORITY && p < lpriority) {
                index = i;
                lpriority = p;
            }
        }
        return index;
    }

    private static IExprNode parse(final List<ExprToken> tokenList,
            final IExprOperator operator) throws Exception {
        final int size = tokenList.size();
        if (size == 1) {
            final IExprNode node = tokenList.get(0).getNode();
            return node;
        }
        if (size == 0) {
            return null;
        }
        int index = getLowestPriorityOpToken(tokenList, operator);
        if (index != -1) {
            final IExprNode node = tokenList.get(index).getNode();
            if (index != 0) {
                node.setANode(parse(tokenList.subList(0, index), operator));
            }
            if (index != size - 1) {
                index++;
                node.setZNode(parse(tokenList.subList(index, size), operator));
            }
            return node;
        } else {
            final ExprArrayNode ean = new ExprArrayNode();
            for (final ExprToken tk : tokenList) {
                ean.add(tk.getNode());
            }
            return ean;
        }
    }

    private final String exprString;

    public ExprParser(final String str) {
        exprString = str;
    }

    public IExprNode parse() throws Exception {
        final ExprTokenizer tk = new ExprTokenizer(exprString, null);
        return parse(tk.getAllToken(), null);
    }

    public IExprNode parse(final IExprOperator operator) throws Exception {
        final ExprTokenizer tk = new ExprTokenizer(exprString, operator);
        return parse(tk.getAllToken(), operator);
    }
}
