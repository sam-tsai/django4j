package org.django4j.app.staticfile;

import java.util.regex.Pattern;

import org.django4j.api.http.IRequest;
import org.django4j.app.router.IHandle;
import org.django4j.app.router.IURLMatcher;

public class StaticFileUrlMather implements IURLMatcher {
	public final static String STATIC_PRE = "staticfile/";
	private final static Pattern p = Pattern.compile("^/" + STATIC_PRE + ".*$");

	private final IHandle staticHandle = new StaticFileHandle();

	@Override
	public IHandle match(IRequest request) {
		if (p.matcher(request.path()).find())
			return staticHandle;
		return null;
	}

	@Override
	public int priporty() {
		return 100;
	}

}
