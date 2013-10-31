package org.django4j.app.template.expr.operator;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.expr.IExprNode;
import org.django4j.app.template.utils.reflect.ObjectUtils;

public class LogicOperator extends AbstractOperator {

    private static final Map<String, Integer> MAPOFOP = new HashMap<String, Integer>();

    static {
        MAPOFOP.put("or", 1);
        MAPOFOP.put("and", 2);
        MAPOFOP.put("not", 3);
        MAPOFOP.put("in", 4);
        MAPOFOP.put("==", 5);
        MAPOFOP.put("!=", 5);
        MAPOFOP.put("<", 5);
        MAPOFOP.put(">", 5);
        MAPOFOP.put("<=", 5);
        MAPOFOP.put(">=", 5);
    }

    public LogicOperator() {
        super(MAPOFOP);
    }

    @Override
    public int getPriority(final String operatorStr) {
        return MAPOFOP.containsKey(operatorStr) ? MAPOFOP.get(operatorStr)
                : EMPTY_PRIORITY;
    }

    @Override
    public boolean isOperator(final String operatorStr) {
        return MAPOFOP.containsKey(operatorStr);
    }

    @Override
    public Object value(final ITemplateEngine tEngine,
                        final String operatorStr, final RenderContext ct,
                        final IExprNode aNode, final IExprNode zNode) throws Exception {
        if (operatorStr.equals("==")) {
            return isEqual(tEngine, ct, aNode, zNode);
        } else if (operatorStr.equals("!=")) {
            return !isEqual(tEngine, ct, aNode, zNode);
        } else if (operatorStr.equals("<")) {
            return compare(tEngine, ct, aNode, zNode) < 0;
        } else if (operatorStr.equals(">")) {
            return compare(tEngine, ct, aNode, zNode) > 0;
        } else if (operatorStr.equals("<=")) {
            return compare(tEngine, ct, aNode, zNode) <= 0;
        } else if (operatorStr.equals(">=")) {
            return compare(tEngine, ct, aNode, zNode) >= 0;
        } else if (operatorStr.equals("or")) {
            if (aNode == null || zNode == null) {
                throw new Exception("or operation error,this need two oprater");
            }
            return ObjectUtils.boolVal(aNode.value(tEngine, ct))
                    || ObjectUtils.boolVal(zNode.value(tEngine, ct));
        } else if (operatorStr.equals("and")) {
            if (aNode == null || zNode == null) {
                throw new Exception("and operation error,this need two oprater");
            }
            return ObjectUtils.boolVal(aNode.value(tEngine, ct))
                    && ObjectUtils.boolVal(zNode.value(tEngine, ct));
        } else if (operatorStr.equals("not")) {
            if (zNode == null) {
                throw new Exception("not operation error,this need a oprater");
            }
            return !ObjectUtils.boolVal(zNode.value(tEngine, ct));
        } else if (operatorStr.equals("in")) {
            if (aNode == null || zNode == null) {
                throw new Exception("in operation error,this need two oprater");
            }
            return inVal(aNode.value(tEngine, ct), zNode.value(tEngine, ct));
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private int compare(final ITemplateEngine tEngine, final RenderContext ct,
                        final IExprNode aNode, final IExprNode zNode) throws Exception {
        if (aNode == null || zNode == null) {
            throw new Exception("!= operation error,this need two oprater");
        }
        final Object aVal = aNode.value(tEngine, ct);
        final Object zVal = zNode.value(tEngine, ct);
        if (aVal instanceof Comparable) {
            final Comparable<Object> aValCom = (Comparable<Object>) aVal;
            return aValCom.compareTo(zVal);
        }
        if (zVal instanceof Comparable) {
            final Comparable<Object> zValCom = (Comparable<Object>) zVal;
            return zValCom.compareTo(aVal) * -1;
        }
        throw new Exception("<,> operation error,this need a Comparable value");
    }

    private boolean inVal(final Object containerObj, final Object obj)
            throws Exception {
        if (containerObj == null) {
            return false;
        }
        if (containerObj instanceof Collection) {
            final Collection<?> c = (Collection<?>) containerObj;
            return c.contains(obj);
        } else if (containerObj instanceof Map) {
            final Map<?, ?> c = (Map<?, ?>) containerObj;
            return c.containsKey(obj);
        }
        final Class<?> cls = containerObj.getClass();
        for (final Method m : cls.getDeclaredMethods()) {
            final String mName = m.getName();
            if (mName.equalsIgnoreCase("has")
                    || mName.equalsIgnoreCase("contains")) {
                final Type[] ts = m.getGenericParameterTypes();
                if (ts != null && ts.length == 1) {
                    if (!m.isAccessible()) {
                        m.setAccessible(true);
                    }
                    final Object rtobj = m.invoke(containerObj, obj);
                    if (rtobj == null) {
                        return false;
                    } else if (rtobj instanceof Boolean) {
                        return ((Boolean) rtobj).booleanValue();
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isEqual(final ITemplateEngine tEngine,
                            final RenderContext ct, final IExprNode aNode, final IExprNode zNode)
            throws Exception {
        if (aNode == null || zNode == null) {
            throw new Exception("== operation error,this need two oprater");
        }
        final Object aVal = aNode.value(tEngine, ct);
        final Object zVal = zNode.value(tEngine, ct);
        return (aVal == null) ? (zVal == null) : (aVal.equals(zVal));
    }

}
