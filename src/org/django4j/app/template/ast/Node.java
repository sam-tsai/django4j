package org.django4j.app.template.ast;

import java.util.ArrayList;
import java.util.List;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

public abstract class Node {
    public static String renderBranchChild(final ITemplateEngine tEngine,
            final List<Node> nodeList, final RenderContext ct) throws Exception {
        final StringBuilder sb = new StringBuilder();
        final Node n = getBranchNode(nodeList);
        if (n != null) {
            sb.append(n.render(tEngine, ct));
        }
        return sb.toString();
    }

    public static String renderChild(final ITemplateEngine tEngine,
            final List<Node> nodeList, final RenderContext ct) throws Exception {
        final StringBuilder sb = new StringBuilder();
        for (final Node n : nodeList) {
            sb.append(n.render(tEngine, ct));
        }
        return sb.toString();
    }

    public static String renderTrunkChild(final ITemplateEngine tEngine,
            final List<Node> nodeList, final RenderContext ct) throws Exception {
        final StringBuilder sb = new StringBuilder();
        for (final Node n : nodeList) {
            if (n instanceof IBranchNode) {
                break;
            }
            sb.append(n.render(tEngine, ct));
        }
        return sb.toString();
    }

    private static Node getBranchNode(final List<Node> nodeList) {
        for (final Node n : nodeList) {
            if (n instanceof IBranchNode) {
                return n;
            }
        }
        return null;
    }

    private final List<Node> childNodes = new ArrayList<Node>();
    private final String     content;
    private final Node       parent;
    private final RootNode   root;

    public Node(final RootNode rootNode, final Node parentNode,
            final String content) {
        this.root = rootNode;
        this.parent = parentNode;
        if (parent != null) {
            parent.getChildNodes().add(this);
        }
        this.content = content;
    }

    public Node getParent() {
        return this.parent;
    }

    public RootNode getRoot() {
        return this.root;
    }

    public abstract String render(ITemplateEngine tEngine, RenderContext ct)
            throws Exception;

    protected List<Node> getChildNodes() {
        return childNodes;
    }

    protected String getContent() {
        return content;
    }

}
