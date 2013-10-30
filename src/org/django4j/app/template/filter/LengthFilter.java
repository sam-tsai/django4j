package org.django4j.app.template.filter;

import org.django4j.app.template.IFilter;
import org.django4j.app.template.utils.reflect.ObjectUtils;

import java.lang.reflect.InvocationTargetException;

public class LengthFilter implements IFilter {

    @Override
    public Object doFilter(final Object obj, final String param)
            throws IllegalAccessException, InvocationTargetException {
        return ObjectUtils.getSize(obj);
    }

    @Override
    public String getName() {
        return "length";
    }

}
