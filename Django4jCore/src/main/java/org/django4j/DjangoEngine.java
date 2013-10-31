package org.django4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletResponse;

import org.django4j.api.http.DjangoResponse;
import org.django4j.api.http.IRequest;
import org.django4j.app.IDjangoApp;
import org.django4j.app.router.IHandle;
import org.django4j.app.router.IHandleRouter;
import org.django4j.app.router.defaulthandle.ReflectUtils;
import org.django4j.app.template.TemplateConst;
import org.django4j.api.AppContext;
import org.django4j.api.Context;
import org.django4j.util.DjangoUtils;
import org.django4j.util.ResScaner;

public class DjangoEngine implements IDjangoApp {
    private final AppContext appContext = new AppContext();
    private final Context cfgContext = new Context();
    private IHandleRouter route = null;

    public DjangoEngine() {
    }

    @Override
    public String[] dependApps() {
        return null;
    }

    public void doAction(final IRequest request,
                         final ServletResponse servletResponse) {
        final String servletPath = request.path();
        final Context ct = Context.local();
        ct.set(TemplateConst.$REQUEST, request);
        ct.set(TemplateConst.$REPONSE, servletResponse);
        ct.set(DjangoConst.$CUR_URL, servletPath);
        final IHandle handle = route.getHandle(request);
        try {
            handle.exec(request, new DjangoResponse(servletResponse, "utf-8"),
                    appContext, cfgContext);
        } catch (final Exception e) {
            // TODO
            e.printStackTrace();
        }
    }

    public <T extends IDjangoApp> T getApp(final String appName) {
        return appContext.get(appName);
    }

    public <T extends Object> T getConfig(final String cfgKey) {
        return cfgContext.get(cfgKey);
    }

    public <T extends Object> T getConfig(final String cfgKey, final T def) {
        return cfgContext.get(cfgKey, def);
    }

    @Override
    public String getName() {
        return DjangoConst.APP_NAME_ENGINE;
    }

    @Override
    public boolean load(final AppContext theAppContext,
                        final Context theCfgContext) {
        appContext.update(theAppContext);
        cfgContext.update(theCfgContext);
        appContext.set(getName(), this);
        final Collection<String> classNameList = ResScaner
                .scanPackage("org.django4j.app");
        final List<IDjangoApp> appList = new ArrayList<IDjangoApp>();
        for (final String resName : classNameList) {
            if (ResScaner.isClass(resName)) {
                final IDjangoApp app = ReflectUtils.newInterfaceInstance(
                        ResScaner.getClassName(resName), IDjangoApp.class);
                if (app != null) {
                    appList.add(app);
                }
            }
        }
        boolean noError = true;
        while (appList.size() != 0 && noError) {
            noError = false;
            for (int i = appList.size() - 1; i >= 0; i--) {
                final IDjangoApp app = appList.get(i);
                if (isAppCanLoad(app.dependApps())) {
                    noError = true;
                    app.load(appContext, cfgContext);
                    appContext.set(app.getName(), app);
                    appList.remove(i);
                }
            }
        }
        if (appList.size() != 0) {
            // TODO has app can't load
        }
        appList.clear();
        route = appContext.get(DjangoConst.APP_NAME_ROUTER);
        return false;
    }

    @Override
    public boolean unload() {

        return false;
    }

    private boolean isAppCanLoad(final String[] dependApps) {
        if (DjangoUtils.isEmpty(dependApps)) {
            return true;
        }
        for (final String appName : dependApps) {
            if (appContext.hasno(appName)) {
                return false;
            }
        }
        return true;
    }
}
