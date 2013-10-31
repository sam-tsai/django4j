package org.django4j.app.template;

import org.django4j.api.INameable;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;

public interface ITag extends INameable {
    String getEndToken();

    String[] getSkipTokens();

    Node parserNode(ITemplateEngine tEngine, RootNode rootNode,
                    Node parentNode, String content) throws Exception;
}
