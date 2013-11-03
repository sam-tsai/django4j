package org.django4j.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.django4j.DjangoConst;
import org.django4j.DjangoEngine;
import org.django4j.api.Context;

/**
 * This is the servlet filter implements of Django4j. It is configer in the
 * web.xml like this
 * <p/>
 * <pre>
 *  <filter>
 *     <filter-name>DjangoFilter</filter-name>
 *     <filter-class>org.django4j.servlet.DjangoFilter</filter-class>
 *     <init-param>
 *        <param-name>app_page_package</param-name>
 *        <param-value>org.django4j.example.hello</param-value>
 *     </init-param>
 *
 * </filter>
 *  <filter-mapping>
 *     <filter-name>DjangoFilter</filter-name>
 *     <url-pattern>/*</url-pattern>
 *  </filter-mapping>
 * </pre>
 *
 * @author cxzh9888 at hotmail.com
 */
public class DjangoFilter implements Filter {
    private final DjangoEngine engine = new DjangoEngine();

    @Override
    public void destroy() {
        engine.unload();
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {
        Context.local();
        engine.doAction(new DjangoRequest((HttpServletRequest) servletRequest),
                servletResponse);
        Context.clean();
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        final Context ct = new Context();
        ct.set(DjangoConst.APP_PACKAGE,
                filterConfig.getInitParameter(DjangoConst.APP_PACKAGE));
        engine.load(null, ct);
    }
}
