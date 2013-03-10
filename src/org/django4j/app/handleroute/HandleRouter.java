package org.django4j.app.handleroute;

import java.util.TreeSet;

import org.django4j.DjangoConst;
import org.django4j.api.IRequest;
import org.django4j.context.AppContext;
import org.django4j.context.Context;

public class HandleRouter implements IHandleRouter {

    private final TreeSet<IURLMatcher> pathHandleMap = new TreeSet<IURLMatcher>();

    @Override
    public String[] dependApps() {
        return null;
    }

    @Override
    public IHandle getHandle(IRequest request) {
        for (IURLMatcher matcher : pathHandleMap) {
            IHandle handle = matcher.match(request);
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
    public void regHandle(final IURLMatcher urlMater) {
        pathHandleMap.add(urlMater);
    }

    @Override
    public boolean unload() {
        return false;
    }

}
