package org.django4j.app.template.tag;

import java.util.HashMap;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.tag.abstract_.SingleTag;

public class TemplateTagTag extends SingleTag {

    @Override
    public String getName() {
        return "templatetag";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
                           final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new TemplateTagNode(rootNode, parentNode, content);
    }

}

class TemplateTagNode extends Node {
    private static final Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("openblock", "{%");
        map.put("closeblock", "%}");
        map.put("openvariable", "{{");
        map.put("closevariable", "}}");
        map.put("openbrace", "{");
        map.put("closebrace", "}");
        map.put("opencomment", "{#");
        map.put("closecomment", "#}");

    }

    public TemplateTagNode(final RootNode rootNode, final Node parentNode,
                           final String content) {
        super(rootNode, parentNode, content);
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        if (map.containsKey(getContent())) {
            return map.get(getContent());
        }
        return "";
    }

}
