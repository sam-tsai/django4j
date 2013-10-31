package org.django4j.app.djangoroute;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.django4j.api.http.IRequest;
import org.django4j.app.router.IRouter;
import org.django4j.app.router.IURLMatcher;

public class DjangoUrlMather implements IURLMatcher {
	public static class RoutePattern {
		Pattern p;
		String[] groupName;
		String  handlerClassName;
		String methodName;
	}

	private List<RoutePattern> lsRouteReg = new ArrayList<RoutePattern>();
	private DjangoHandler _handler = new DjangoHandler();
	@Override
	public IRouter match(IRequest request) {
		String path = request.path();
		for (RoutePattern rp : lsRouteReg) {
			Matcher m = rp.p.matcher(path);
			if (m.matches()) {
				MatchResult s= m.toMatchResult();
				//TODO getGroupData and then match the groupName
				//TODO put the data on threadLocalData or in requestData
				return _handler;
			}
		}
		return null;
	}

	@Override
	public int priporty() {
		return 50;
	}

}
