package org.django4j.util;

public final class ServletPathIter {

    private String servletPath = null;

    public ServletPathIter(final String _servletPath) {
        String tmpServletPath = _servletPath;
        if (_servletPath.length() > 1 && _servletPath.endsWith("/")) {
            tmpServletPath = _servletPath.substring(0,
                    _servletPath.length() - 1);
        }
        servletPath = tmpServletPath;
    }

    public boolean hasNext() {
        return !DjangoUtils.isEmpty(servletPath);
    }

    public String next() {
        String tmp = servletPath;
        final int index = servletPath.lastIndexOf('/');
        servletPath = servletPath.substring(0, index);
        return tmp;
    }
}
