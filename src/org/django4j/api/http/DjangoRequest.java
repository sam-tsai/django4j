package org.django4j.api.http;

import org.django4j.api.QueryDict;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DjangoRequest implements IRequest {
    private String path = null;
    private QueryDict getDict = null;
    private QueryDict postDict = null;
    private QueryDict request = null;
    private QueryDict rest = new QueryDict();
    private CookieContext cookieConext = null;
    private MetaContext metaConext = null;
    private String context = null;
    private final HttpServletRequest hsr;

    public DjangoRequest(HttpServletRequest hsr) {
        this.hsr = hsr;
    }

    @Override
    public String path() {
        if (path == null) {
            path = hsr.getServletPath();
            if (path.endsWith("/") && !path.equals("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }

    @Override
    public QueryDict get() {
        if (getDict == null) {
            getDict = parseGet();
        }
        return getDict;
    }

    @Override
    public QueryDict post() {
        if (postDict == null) {
            postDict = parsePost();
        }
        return postDict;
    }

    @Override
    public QueryDict request() {
        return request;
    }

    @Override
    public QueryDict rest() {
        return rest;
    }

    public void files() {

    }

    private QueryDict parseGet() {
        if (hsr == null)
            return null;
        String queryStr = hsr.getQueryString();
        return ParamStringParser.parseParams(queryStr);
    }

    private QueryDict parsePost() {
        if (hsr == null)
            return null;
        if (hsr.getMethod().equalsIgnoreCase("post")) {
            String contentType = hsr.getContentType();
            if ("application/x-www-form-urlencoded".equals(contentType)) {
                return ParamStringParser.parseParams(content());
            }
        }
        return null;
    }

    @Override
    public CookieContext cookies() {
        if (cookieConext != null)
            return cookieConext;
        Cookie[] cs = hsr.getCookies();
        Map<String, ICookie> m = new HashMap<String, ICookie>();
        for (Cookie c : cs) {
            ICookie cookie = new DjangoCookie(c);
            m.put(cookie.getName(), cookie);
        }
        cookieConext = new CookieContext(m);
        return cookieConext;
    }

    @Override
    public MetaContext meta() {
        if (metaConext != null)
            return metaConext;
        Map<String, Object> m = new HashMap<String, Object>();
        m.put(MetaContext.CONTENT_LENGTH, hsr.getContentLength());
        m.put(MetaContext.CONTENT_TYPE, hsr.getContentType());
        m.put(MetaContext.HTTP_ACCEPT_ENCODING, hsr.getCharacterEncoding());
        m.put(MetaContext.HTTP_ACCEPT_LANGUAGE, hsr.getLocale());
        m.put(MetaContext.QUERY_STRING, hsr.getQueryString());
        m.put(MetaContext.REMOTE_ADDR, hsr.getRemoteAddr());
        m.put(MetaContext.REMOTE_HOST, hsr.getRemoteHost());
        m.put(MetaContext.REQUEST_METHOD, hsr.getMethod());
        m.put(MetaContext.SERVER_NAME, hsr.getServerName());
        m.put(MetaContext.SERVER_PORT, hsr.getServerPort());
        metaConext = new MetaContext(m);
        return metaConext;
    }

    @Override
    public InputStream readStream() {
        try {
            return hsr.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String content() {
        if (context != null)
            return context;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = hsr.getReader();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IllegalStateException e) {

        } catch (IOException e) {
        }
        context = sb.toString();
        return context;
    }

    @Override
    public Action action() {
        return Action.build(hsr.getMethod());
    }
}
