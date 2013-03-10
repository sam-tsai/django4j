package org.django4j.app.template.expr;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

public interface IExprOperator {
    int CUTOMER_PRIORITY_MAX = 1000;

    int EMPTY_PRIORITY       = -1;

    int FILTER_PRIORITY      = Integer.MAX_VALUE - 1;

    int MAX_PRIORITY         = Integer.MAX_VALUE;

    int getPriority(String operatorStr);

    boolean isOperator(String operatorStr);

    Object value(ITemplateEngine tEngine, String operatorStr, RenderContext ct,
            IExprNode aNode, IExprNode zNode) throws Exception;
}
