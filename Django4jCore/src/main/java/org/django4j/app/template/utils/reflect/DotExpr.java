package org.django4j.app.template.utils.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.django4j.app.template.IVariable;
import org.django4j.app.template.RenderContext;
import org.django4j.app.template.utils.StringUtils;

public class DotExpr {
    public static Object getDotValue(final Object obj, final String dotString)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        final List<String> dotStringList = StringUtils.splitString2List(
                dotString, "\\.");
        return getDotValue(obj, dotStringList);
    }

    public static Object getValue(final String content, final RenderContext ct)
            throws Exception {
        final List<String> dotStringList = StringUtils.splitString2List(
                content, "\\.");
        final int size = dotStringList.size();
        if (size == 0) {
            return null;
        }
        Object obj = ct.get(dotStringList.get(0));

        if (obj == null) {
            final boolean isDigitalString = StringUtils.isDigitalString(dotStringList.get(0));
            int digital = 0;
            if (isDigitalString) {
                digital = Integer.parseInt(dotStringList.get(0));
            }
            return digital;
        }
        if (size > 1) {
            obj = getDotValue(obj, dotStringList.subList(1, size));
        }
        if (obj instanceof IVariable) {
            final IVariable variObj = (IVariable) obj;
            obj = variObj.value(null, ct);
        }
        return obj;
    }

    private static Object getDotValue(final Object obj,
                                      final List<String> dotStringList) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Object curObj = obj;
        for (final String _dotString_ : dotStringList) {
            if (curObj == null) {
                break;
            }
            curObj = getDotValue_(curObj, _dotString_);
        }
        return curObj;
    }

    private static Object getDotValue_(final Object obj, final String dotString)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        if (obj == null) {
            return null;
        }
        final boolean isDigitalString = StringUtils.isDigitalString(dotString);
        int digital = 0;
        if (isDigitalString) {
            digital = Integer.parseInt(dotString);
        }
        if (obj instanceof Map<?, ?>) {
            final Map<?, ?> m = (Map<?, ?>) obj;
            if (m.containsKey(dotString)) {
                return m.get(dotString);
            }
            if (isDigitalString && m.containsKey(digital)) {
                return m.get(digital);
            }
        }
        if (isDigitalString && obj instanceof List<?>) {
            final List<?> l = (List<?>) obj;
            if (digital > 0 && digital < l.size()) {
                return l.get(digital);
            }
        }
        if (isDigitalString && obj.getClass().isArray()) {
            if (digital > 0 && digital < Array.getLength(obj)) {
                return Array.get(obj, digital);
            }
        }
        if (isDigitalString && obj instanceof Class
                && ((Class<?>) obj).isEnum()) {
            final Object[] enumConsts = ((Class<?>) obj).getEnumConstants();
            if (digital > 0 && digital < Array.getLength(obj)) {
                return enumConsts[digital];
            }
        }
        if (isDigitalString && obj instanceof String) {
            final String str = (String) obj;
            if (digital > 0 && digital < str.length()) {
                return str.charAt(digital);
            }
        }
        final String geterString1 = "get" + dotString;
        final String geterString2 = "is" + dotString;
        final Class<?> cls = obj.getClass();
        for (final Field f : cls.getFields()) {
            if (f.getName().equalsIgnoreCase(dotString)) {
                return f.get(obj);
            }
        }
        for (final Method m : cls.getMethods()) {
            final String mName = m.getName();
            if (mName.equalsIgnoreCase(dotString)
                    || mName.equalsIgnoreCase(geterString1)
                    || mName.equalsIgnoreCase(geterString2)) {
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
                return m.invoke(obj, new Object[0]);
            }
        }

        return null;
    }
}
