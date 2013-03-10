package org.django4j.app.template.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.ast.Node;
import org.django4j.app.template.ast.RootNode;
import org.django4j.app.template.exception.ParserException;
import org.django4j.app.template.expr.ExprParser;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.expr.ast.ExprFilterNode;
import org.django4j.app.template.expr.ast.ExprOperNode;
import org.django4j.app.template.expr.ast.ExprStringNode;
import org.django4j.app.template.expr.ast.abstract_.ExprValueNode;
import org.django4j.app.template.expr.operator.JustParserOperator;
import org.django4j.app.template.tag.abstract_.SingleTag;
import org.django4j.app.template.utils.reflect.DotExpr;
import org.django4j.app.template.utils.reflect.IterObject;

public class RegroupTag extends SingleTag {
    @Override
    public String getName() {
        return "regroup";
    }

    @Override
    public Node parserNode(final ITemplateEngine tEngine,
            final RootNode rootNode, final Node parentNode, final String content)
            throws Exception {
        return new RegroupNode(rootNode, parentNode, content);
    }

}

class GroupObject {
    private final Object       grouper;

    private final List<Object> list;

    public GroupObject(final Object grouper) {
        this.grouper = grouper;
        this.list = new ArrayList<Object>();
    }

    public void addItem(final Object item) {
        list().add(item);
    }

    public Object grouper() {
        return grouper;
    }

    public List<Object> list() {
        return list;
    }
}

class RegroupNode extends Node {
    private static final String               AS      = "as";

    private static final String               BY      = "by";

    private static final Map<String, Integer> MAPOFOP = new HashMap<String, Integer>();
    static {
        MAPOFOP.put(AS, 1);
        MAPOFOP.put(BY, 2);
    }

    private final String                      field;

    private final IExprNode                   groupNode;

    private final String                      varName;

    public RegroupNode(final RootNode rootNode, final Node parentNode,
            final String content) throws Exception {
        super(rootNode, parentNode, content);
        final IExprNode exprNode = (new ExprParser(content))
                .parse(new JustParserOperator(MAPOFOP));
        if (!(exprNode instanceof ExprOperNode) || !exprNode.match(AS)) {
            throw new ParserException(
                    "regroup param is invalid, need a as opion");
        }
        final IExprNode asVarNameNode = exprNode.getZNode();
        if (asVarNameNode == null || !(asVarNameNode instanceof ExprStringNode)) {
            throw new ParserException(
                    "regroup param is invalid, need a as opion");
        }
        varName = asVarNameNode.getContent();

        final IExprNode byOperNode = exprNode.getANode();
        if (byOperNode == null || !(byOperNode instanceof ExprOperNode)
                || !exprNode.match(BY)) {
            throw new ParserException(
                    "regroup param is invalid, need a by opion");
        }
        final IExprNode byFieldNode = byOperNode.getZNode();
        if (byFieldNode == null || !(byFieldNode instanceof ExprStringNode)) {
            throw new ParserException(
                    "regroup param is invalid, need a by opion");
        }
        field = byFieldNode.getContent();
        final IExprNode byGroupNode = byOperNode.getANode();
        if (byGroupNode == null
                || !(byGroupNode instanceof ExprValueNode || byGroupNode instanceof ExprFilterNode)) {
            throw new ParserException(
                    "regroup param is invalid, need a group param");
        }
        groupNode = byGroupNode;
    }

    @Override
    public String render(final ITemplateEngine tEngine, final RenderContext ct)
            throws Exception {
        final List<GroupObject> group_object_list = new ArrayList<GroupObject>();
        final Map<Object, GroupObject> map = new HashMap<Object, GroupObject>();
        final GroupObject nullGroupObj = new GroupObject(null);
        final Object toRegroupObjList = groupNode.value(tEngine, ct);
        final IterObject iter = new IterObject(toRegroupObjList);
        while (iter.hasNext()) {
            final Object toRegroupObj = iter.next();
            final Object grouper = DotExpr.getDotValue(toRegroupObj, field);
            if (grouper == null) {
                nullGroupObj.addItem(toRegroupObj);
            } else {
                GroupObject go = null;
                if (map.containsKey(grouper)) {
                    go = map.get(grouper);
                } else {
                    go = new GroupObject(grouper);
                    group_object_list.add(go);
                }
                go.addItem(toRegroupObj);
            }
        }
        if (nullGroupObj.list().size() != 0) {
            group_object_list.add(nullGroupObj);
        }
        ct.set(varName, group_object_list);
        return "";
    }
}
