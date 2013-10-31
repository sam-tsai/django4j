package org.django4j.app.template;

import org.django4j.api.INameable;

public interface IFilter extends INameable {
    Object doFilter(Object obj, String param) throws Exception;
}
