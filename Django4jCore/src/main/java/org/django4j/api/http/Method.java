package org.django4j.api.http;

import java.util.HashMap;
import java.util.Map;

import org.django4j.util.DjangoUtils;

/**
 * Created with IntelliJ IDEA. User: neverend Date: 13-6-7 Time: 下午8:36 To
 * change this template use File | Settings | File Templates.
 */
public class Method {

	public static Method GET = new Method("get");
	public static Method POST = new Method("post");
	private static Map<String, Method> methodMap = new HashMap<String, Method>();

	static {
		methodMap.put(GET.str(), GET);
		methodMap.put(POST.str(), POST);
	}

	private final String _str;

	private Method(String methodStr) {
		_str = methodStr;
	}

	public static Method build(String methodStr) {
		if (DjangoUtils.isEmpty(methodStr)) {
			throw new IllegalArgumentException("empty method");
		}
		String str = methodStr.toLowerCase();
		if (methodStr.contains(str)) {
			return methodMap.get(str);
		} else {
			return methodMap.put(str, new Method(str));
		}
	}

	public String str() {
		return _str;
	}

	public boolean isGet() {
		return "get".equals(_str);
	}

	public boolean isPost() {
		return "post".equals(_str);
	}
}
