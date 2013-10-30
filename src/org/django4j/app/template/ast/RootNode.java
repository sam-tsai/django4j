package org.django4j.app.template.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;

import java.io.File;

public class RootNode extends Node {
    private RootNode parentRootNode = null;

    private File templeteFile = null;

    public RootNode() {
        super(null, null, "");
    }

    public File getTempleteFile() {
        return templeteFile;
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final RenderContext param = RenderContext.getChild(ct);
        final String str = renderChild(tEngine, getChildNodes(), param);
        RootNode topRoot = this;
        while (topRoot.parentRootNode != null) {
            topRoot = topRoot.parentRootNode;
            renderChild(tEngine, topRoot.getChildNodes(), param);
        }
        if (topRoot == this) {
            return str;
        } else {
            return topRoot.render(tEngine, param);
        }
    }

    public void setParentRootNode(final RootNode parentRootNode) {
        this.parentRootNode = parentRootNode;
    }

    public void setTempleteFile(final File templeteFile) {
        this.templeteFile = templeteFile;
    }
}
