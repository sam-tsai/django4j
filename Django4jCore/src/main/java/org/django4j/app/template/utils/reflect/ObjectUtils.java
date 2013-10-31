package org.django4j.app.template.utils.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {
    public static boolean boolVal(final Object obj) throws Exception {
        if (obj == null) {
            return false;
        } else if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        } else if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            final Object size = getSize(obj);
            if (size instanceof Number) {
                return ((Number) size).intValue() != 0;
            } else if (size instanceof Boolean) {
                return (Boolean) size;
            }
            return true;
        }
    }

    public static Object getSize(final Object obj)
            throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            return 0;
        } else if (obj instanceof String) {
            return ((String) obj).length();
        } else if (obj instanceof Collection) {
            final Collection<?> c = (Collection<?>) obj;
            return c.size();
        } else if (obj instanceof Map) {
            final Map<?, ?> c = (Map<?, ?>) obj;
            return c.size();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        } else if (obj instanceof Class && ((Class<?>) obj).isEnum()) {
            final Class<?> c = (Class<?>) obj;
            return c.getEnumConstants().length;
        }

        final Class<?> cls = obj.getClass();
        for (final Method m : cls.getDeclaredMethods()) {
            // the method need no param
            final Type[] ts = m.getGenericParameterTypes();
            if (ts != null && ts.length != 0) {
                continue;
            }
            // the method name
            final String mName = m.getName();
            if (mName.equalsIgnoreCase("size") || mName.equalsIgnoreCase("len")
                    || mName.equalsIgnoreCase("length")
                    || mName.equalsIgnoreCase("isempty")
                    || mName.equalsIgnoreCase("getlength")
                    || mName.equalsIgnoreCase("getlen")
                    || mName.equalsIgnoreCase("getsize")) {
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
                return m.invoke(obj);
            }
        }
        return 0;
    }
}
