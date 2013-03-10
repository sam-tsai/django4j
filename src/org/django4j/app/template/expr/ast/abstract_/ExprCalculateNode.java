package org.django4j.app.template.expr.ast.abstract_;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.IExprNode;

public abstract class ExprCalculateNode extends ExprAbstractNode {

    private IExprNode aNode;

    private IExprNode zNode;

    public ExprCalculateNode(final String content) {
        super(content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#getANode()
     */
    @Override
    public IExprNode getANode() {
        return aNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#getZNode()
     */
    @Override
    public IExprNode getZNode() {
        return zNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#hasANode()
     */
    @Override
    public boolean hasANode() {
        return aNode != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#hasZNode()
     */
    @Override
    public boolean hasZNode() {
        return zNode != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#setANode(org.django4j.django
     * .expr.ast.IExprNode1)
     */
    @Override
    public void setANode(final IExprNode aNode) {
        this.aNode = aNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#setZNode(org.django4j.django
     * .expr.ast.IExprNode1)
     */
    @Override
    public void setZNode(final IExprNode zNode) {
        this.zNode = zNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.django4j.django.expr.ast.IExprNode1#value(org.django4j.Context)
     */
    @Override
    public abstract Object value(ITemplateEngine tEngine, RenderContext ct)
            throws Exception;

}
