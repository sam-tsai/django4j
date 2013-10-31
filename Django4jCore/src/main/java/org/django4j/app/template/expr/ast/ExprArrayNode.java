package org.django4j.app.template.expr.ast;

import java.util.ArrayList;
import java.util.List;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.abstract_.ExprValueNode;

public class ExprArrayNode extends ExprValueNode {
    private int index = 0;

    private final List<IExprNode> nodeList = new ArrayList<IExprNode>();

    private int size = 0;

    public ExprArrayNode() {
        super("");
    }

    public void add(final IExprNode node) {
        nodeList.add(node);
        size++;
    }

    public IExprNode get(final int index) {
        return nodeList.get(index);
    }

    public boolean hasNext() {
        return index < size;
    }

    public IExprNode next() {
        return nodeList.get(index++);
    }

    public void remove(final int index) {
        nodeList.remove(index);
        size--;
    }

    public void resetIter() {
        index = 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "" + nodeList;
    }

    @Override
    public Object value(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final List<Object> lsValue = new ArrayList<Object>();
        for (final IExprNode node : nodeList) {
            lsValue.add(node.value(tEngine, ct));
        }
        return lsValue;
    }
}
