package org.django4j.app.template.filter;

import java.lang.reflect.InvocationTargetException;

import org.django4j.app.template.IFilter;
import org.django4j.app.template.utils.reflect.ObjectUtils;

public class LengthIsFilter implements IFilter {

    @Override
    public Object doFilter(final Object obj, final String param)
            throws IllegalAccessException, InvocationTargetException {
        final Object size = ObjectUtils.getSize(obj);
        return String.valueOf(size).equals(param);
    }

    @Override
    public String getName() {
        return "lengthis";
    }
}
