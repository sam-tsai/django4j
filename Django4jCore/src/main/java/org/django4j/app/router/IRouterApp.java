package org.django4j.app.router;

import org.django4j.api.http.IRequest;
import org.django4j.app.IDjangoApp;

public interface IRouterApp extends IDjangoApp {

    public abstract IRouter getRouter(IRequest request);

    public abstract void regRouter(final IURLMatcher urlMater);

}