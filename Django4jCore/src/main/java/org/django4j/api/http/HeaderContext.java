package org.django4j.api.http;

import org.django4j.api.Dictionary;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neverend
 * Date: 13-10-30
 * Time: 下午9:20
 * To change this template use File | Settings | File Templates.
 */
public class HeaderContext extends Dictionary<Object> {
    public final static String CONTENT_LENGTH = "CONTENT_LENGTH";
    public final static String CONTENT_TYPE = "CONTENT_TYPE";
    public final static String HTTP_ACCEPT_ENCODING = "HTTP_ACCEPT_ENCODING";
    public final static String HTTP_ACCEPT_LANGUAGE = "HTTP_ACCEPT_LANGUAGE";
    public final static String HTTP_REFERER = "HTTP_REFERER";
    public final static String HTTP_USER_AGENT = "HTTP_USER_AGENT";
    public final static String QUERY_STRING = "QUERY_STRING";
    public final static String REMOTE_ADDR = "REMOTE_ADDR";
    public final static String REMOTE_HOST = "REMOTE_HOST";
    public final static String REQUEST_METHOD = "REQUEST_METHOD";
    public final static String SERVER_NAME = "SERVER_NAME";
    public final static String SERVER_PORT = "SERVER_PORT";

    public HeaderContext(final Map<String, Object> map) {
        super(map);
    }
}
