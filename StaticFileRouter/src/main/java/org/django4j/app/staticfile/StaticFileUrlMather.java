package org.django4j.app.staticfile;

import java.util.regex.Pattern;

import org.django4j.api.http.IRequest;
import org.django4j.app.router.IRouter;
import org.django4j.app.router.IURLMatcher;

public class StaticFileUrlMather implements IURLMatcher {
	public final static String STATIC_PRE = "staticfile/";
	private final static Pattern p = Pattern.compile("^/" + STATIC_PRE + ".*$");

	private final IRouter staticHandle = new StaticFileRouter();

	@Override
	public IRouter match(IRequest request) {
		if (p.matcher(request.path()).find())
			return staticHandle;
		return null;
	}

	@Override
	public int priporty() {
		return 100;
	}

}
