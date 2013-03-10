package org.django4j.api;

import java.io.InputStream;
import java.util.Map;

import org.django4j.context.Dictionary;

public interface IRequest {

    String path();

    String action();

    QueryDict get();

    QueryDict post();

    QueryDict request();

    QueryDict rest();

    CookieContext cookies();

    public MetaContext meta();

    InputStream inIO();

    String content();
}

class MetaContext extends Dictionary<Object> {
    public final static String CONTENT_LENGTH       = "CONTENT_LENGTH";
    public final static String CONTENT_TYPE         = "CONTENT_TYPE";
    public final static String HTTP_ACCEPT_ENCODING = "HTTP_ACCEPT_ENCODING";
    public final static String HTTP_ACCEPT_LANGUAGE = "HTTP_ACCEPT_LANGUAGE";
    public final static String HTTP_REFERER         = "HTTP_REFERER";
    public final static String HTTP_USER_AGENT      = "HTTP_USER_AGENT";
    public final static String QUERY_STRING         = "QUERY_STRING";
    public final static String REMOTE_ADDR          = "REMOTE_ADDR";
    public final static String REMOTE_HOST          = "REMOTE_HOST";
    public final static String REQUEST_METHOD       = "REQUEST_METHOD";
    public final static String SERVER_NAME          = "SERVER_NAME";
    public final static String SERVER_PORT          = "SERVER_PORT";

    public MetaContext(final Map<String, Object> map) {
        super(map);
    }
}

class CookieContext extends Dictionary<ICookie> {

    public CookieContext(Map<String, ICookie> m) {

    }
}