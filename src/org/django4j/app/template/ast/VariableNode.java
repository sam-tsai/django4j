package org.django4j.app.template.ast;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.TemplateConst;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.utils.StringUtils;
import org.django4j.app.template.utils.reflect.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class VariableNode extends Node {
    private static final Map<Character, String> ESCAPEMAP = new HashMap<Character, String>();

    static {
        ESCAPEMAP.put('&', "&amp");
        ESCAPEMAP.put('<', "&lt");
        ESCAPEMAP.put('>', "&gt");
        ESCAPEMAP.put('\'', "&#39");
        ESCAPEMAP.put('"', "&quot");
    }

    private IExprNode exprNode = null;

    public VariableNode(final RootNode rootNode, final Node parentNode,
                        final String content) throws Exception {
        super(rootNode, parentNode, content);
        exprNode = (new ExprParser(content)).parse();
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final RenderContext param = RenderContext.getChild(ct);
        final String str = String.valueOf(exprNode.value(tEngine, param));

        if (param.hasno(TemplateConst.$SAFE)
                && param.has(TemplateConst.$AUTOESCAPE)
                && ObjectUtils.boolVal(param.get(TemplateConst.$AUTOESCAPE))) {
            return StringUtils.convertString(str, ESCAPEMAP);
        } else {
            return str;
        }
    }
}
