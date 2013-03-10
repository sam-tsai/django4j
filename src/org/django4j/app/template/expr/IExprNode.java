package org.django4j.app.template.expr;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

public interface IExprNode {

    IExprNode getANode();

    String getContent();

    IExprNode getZNode();

    boolean hasANode();

    boolean hasZNode();

    boolean match(final String str);

    void setANode(IExprNode aNode);

    void setZNode(IExprNode zNode);

    Object value(ITemplateEngine tEngine, RenderContext ct) throws Exception;

}
