package org.django4j.app.template.expr.operator;

import java.util.HashMap;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.IExprNode;

public class JustParserOperator extends AbstractOperator {
    public JustParserOperator() {
        super(new HashMap<String, Integer>());
    }

    public JustParserOperator(final Map<String, Integer> opMap) {
        super(opMap);
    }

    @Override
    public Object value(final ITemplateEngine tEngine,
            final String operatorStr, final RenderContext ct,
            final IExprNode aNode, final IExprNode zNode) throws Exception {
        return null;
    }

}
