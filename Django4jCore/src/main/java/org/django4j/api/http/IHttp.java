package org.django4j.api.http;

/**
 * Created with IntelliJ IDEA.
 * User: neverend
 * Date: 13-10-30
 * Time: 下午9:24
 * To change this template use File | Settings | File Templates.
 */
public interface IHttp {
    /**
     *
     * @return
     */
    HeaderContext header();
    CookieContext cookies();
}
