package org.django4j.app.staticfile;

import org.django4j.DjangoConst;
import org.django4j.app.IDjangoApp;
import org.django4j.app.router.IRouterApp;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public class StaticFile implements IDjangoApp {

    @Override
    public String[] dependApps() {
        return new String[]{DjangoConst.APP_NAME_ROUTER,
                DjangoConst.APP_NAME_TEMPLATE};
    }

    @Override
    public String getName() {
        return "staticfile";
    }

    @Override
    public boolean load(AppContext appContext, Context cfgContext) {
        regHandel(appContext);
        return true;
    }

    private void regHandel(final AppContext appContext) {
        IRouterApp router = appContext.get(DjangoConst.APP_NAME_ROUTER);
        if (router != null)
            router.regRouter(new StaticFileUrlMather());
    }

    @Override
    public boolean unload() {
        return false;
    }
}
