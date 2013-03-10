package org.django4j.app.template.filter;

import java.lang.reflect.InvocationTargetException;

import org.django4j.app.template.IFilter;
import org.django4j.app.template.utils.reflect.ObjectUtils;

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
