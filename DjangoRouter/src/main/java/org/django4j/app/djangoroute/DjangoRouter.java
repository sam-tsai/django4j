package org.django4j.app.djangoroute;

import org.django4j.DjangoConst;
import org.django4j.app.IDjangoApp;
import org.django4j.app.router.IHandleRouter;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public class DjangoRouter implements IDjangoApp {

	@Override
	public String[] dependApps() {
		return new String[] { DjangoConst.APP_NAME_ROUTER };
	}

	@Override
	public boolean load(AppContext appContext, Context cfgContext) {
		IHandleRouter router = appContext.get(DjangoConst.APP_NAME_ROUTER);
		if (router != null)
			router.regHandle(new DjangoUrlMather());
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public String getName() {
		return "DjangoRouter";
	}

}
