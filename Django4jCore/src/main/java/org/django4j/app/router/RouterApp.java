package org.django4j.app.router;

import java.util.TreeSet;

import org.django4j.DjangoConst;
import org.django4j.api.http.IRequest;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public class RouterApp implements IRouterApp {

	private final TreeSet<IURLMatcher> pathHandleMap = new TreeSet<IURLMatcher>(
			new URLMatherComparter());

	@Override
	public String[] dependApps() {
		return null;
	}

	@Override
	public IRouter getRouter(IRequest request) {
		for (IURLMatcher matcher : pathHandleMap) {
			IRouter handle = matcher.match(request);
			if (handle != null) {
				return handle;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return DjangoConst.APP_NAME_ROUTER;
	}

	@Override
	public boolean load(AppContext appContext, Context cfgContext) {
		return true;
	}

	@Override
	public void regRouter(final IURLMatcher urlMater) {
		pathHandleMap.add(urlMater);
	}

	@Override
	public boolean unload() {
		return false;
	}

}
