package org.django4j.app.djangoroute;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.django4j.api.http.IRequest;
import org.django4j.app.invoker.handle.InvokerHandle;
import org.django4j.app.router.IURLMatcher;

import com.google.code.regexp.MatchResult;
import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

public class DjangoUrlMather implements IURLMatcher {

	Map<Pattern, InvokerHandle> map = new HashMap<Pattern, InvokerHandle>();

	public void addMap(String patternStr, InvokerHandle handle) {
		Pattern p = Pattern.compile(patternStr);
		map.put(p, handle);
	}

	@Override
	public InvokerHandle match(IRequest request) {
		String path = request.path();
		for (Entry<Pattern, InvokerHandle> e : map.entrySet()) {
			Matcher m = e.getKey().matcher(path);
			if (m.matches()) {
				MatchResult s = m.toMatchResult();
				request.request().updateSignal(s.namedGroups());
				return e.getValue();
			}
		}
		return null;
	}

	@Override
	public int priporty() {
		return 50;
	}

}
