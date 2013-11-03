package org.django4j.app.djangoroute;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.django4j.DjangoConst;
import org.django4j.api.AppContext;
import org.django4j.api.Context;
import org.django4j.app.IDjangoApp;
import org.django4j.app.invoker.InvokerHandleFactory;
import org.django4j.app.invoker.handle.InvokerHandle;
import org.django4j.app.router.IRouterApp;
import org.django4j.util.DjangoUtils;
import org.django4j.util.ResScaner;

public class DjangoRouter implements IDjangoApp {
	private DjangoUrlMather urlMatch = new DjangoUrlMather();

	@Override
	public String[] dependApps() {
		return new String[] { DjangoConst.APP_NAME_ROUTER };
	}

	@Override
	public boolean load(AppContext appContext, Context cfgContext) {
		IRouterApp router = appContext.get(DjangoConst.APP_NAME_ROUTER);
		if (router != null)
			router.regRouter(urlMatch);
		scanHandle(appContext, cfgContext);
		return true;
	}

	private void scanHandle(AppContext appContext, Context cfgContext) {
		final String pagePakageName = cfgContext.get(DjangoConst.APP_PACKAGE,
				"") + ".views";
		final Collection<String> resNameList = ResScaner
				.scanPackage(pagePakageName);
		for (final String resName : resNameList) {
			if (ResScaner.isClass(resName)) {
				final String className = ResScaner.getClassName(resName);
				Map<String, InvokerHandle> map = InvokerHandleFactory
						.getHandle(className);
				if (DjangoUtils.isEmpty(map)) {
					continue;
				}

				for (Entry<String, InvokerHandle> e : map.entrySet()) {
					InvokerHandle handle = e.getValue();
					Method m = handle.method();
					UrlMap um = m.getAnnotation(UrlMap.class);
					if (um != null) {
						urlMatch.addMap(um.value(), handle);
					}

				}
			}
		}

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
