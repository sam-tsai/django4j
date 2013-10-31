package org.django4j.api.http;

import java.util.Date;

public interface IResponse extends IHttp{
    void write(String str);

    void write(byte[] bytes);
    
    void write(byte[] bytes,int offset,int len);

    String content();

    void status(int status);

    int status();

    String contentType();

    void contentType(String contentType);

    void setHeader(String header, String value);

    void delHeader(String header);

    String getHeader(String header);

    boolean hasHeader(String header);

    void setCookie(String key, String value, int maxAge, Date expires,
                   String path, String domain, boolean secure, boolean httponly);

    void setSignedCookie(String key, String value, String salt, int maxAge,
                         Date expires, String path, String domain, boolean secure,
                         boolean httponly);

    void deleteCookie(String key, String path, String domain);

    void flush();

    void tell();
}
