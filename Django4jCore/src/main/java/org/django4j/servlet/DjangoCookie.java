package org.django4j.servlet;

import org.django4j.api.http.ICookie;

import javax.servlet.http.Cookie;

public class DjangoCookie extends Cookie implements ICookie {

    public DjangoCookie(Cookie cookie) {
        super(cookie.getName(), cookie.getValue());
        setComment(cookie.getComment());
        setDomain(cookie.getDomain());
        setMaxAge(cookie.getMaxAge());
        setPath(cookie.getPath());
        setSecure(cookie.getSecure());
        setVersion(cookie.getVersion());
    }

    public DjangoCookie(String name, String value) {
        super(name, value);
    }

}
