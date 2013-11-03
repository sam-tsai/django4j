package org.django4j.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.django4j.api.Dict;
import org.django4j.api.http.CookieContext;
import org.django4j.api.http.HeaderContext;
import org.django4j.api.http.ICookie;
import org.django4j.api.http.IRequest;
import org.django4j.api.http.HttpMethod;
import org.django4j.api.http.ParamStringParser;

public class DjangoRequest implements IRequest {
	private final HttpServletRequest hsr;
	private String path = null;
	private Dict getDict = null;
	private Dict postDict = null;
	private Dict request = null;
	private CookieContext cookieConext = null;
	private HeaderContext metaConext = null;
	private String context = null;

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
	public Dict get() {
		if (getDict == null) {
			getDict = parseGet();
		}
		return getDict;
	}

	@Override
	public Dict post() {
		if (postDict == null) {
			postDict = parsePost();
		}
		return postDict;
	}

	@Override
	public Dict request() {
		if (request == null) {
			request = new Dict();
			request.update(get());
			request.update(post());
		}
		return request;
	}

	public void files() {

	}

	private Dict parseGet() {
		if (hsr == null)
			return null;
		String queryStr = hsr.getQueryString();
		return ParamStringParser.parseParams(queryStr);
	}

	private Dict parsePost() {
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
		cookieConext = new CookieContext();
		Cookie[] cs = hsr.getCookies();
		for (Cookie c : cs) {
			ICookie cookie = new DjangoCookie(c);
			cookieConext.set(cookie.getName(), cookie);
		}
		return cookieConext;
	}

	@Override
	public HeaderContext header() {
		if (metaConext != null)
			return metaConext;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(HeaderContext.CONTENT_LENGTH, hsr.getContentLength());
		m.put(HeaderContext.CONTENT_TYPE, hsr.getContentType());
		m.put(HeaderContext.HTTP_ACCEPT_ENCODING, hsr.getCharacterEncoding());
		m.put(HeaderContext.HTTP_ACCEPT_LANGUAGE, hsr.getLocale());
		m.put(HeaderContext.QUERY_STRING, hsr.getQueryString());
		m.put(HeaderContext.REMOTE_ADDR, hsr.getRemoteAddr());
		m.put(HeaderContext.REMOTE_HOST, hsr.getRemoteHost());
		m.put(HeaderContext.REQUEST_METHOD, hsr.getMethod());
		m.put(HeaderContext.SERVER_NAME, hsr.getServerName());
		m.put(HeaderContext.SERVER_PORT, hsr.getServerPort());
		metaConext = new HeaderContext(m);
		return metaConext;
	}

	@Override
	public InputStream is() {
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
	public HttpMethod action() {
		return HttpMethod.build(hsr.getMethod());
	}
}
