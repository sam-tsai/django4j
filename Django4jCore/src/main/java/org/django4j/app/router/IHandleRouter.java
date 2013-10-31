package org.django4j.app.router;

import org.django4j.api.http.IRequest;
import org.django4j.app.IDjangoApp;

public interface IHandleRouter extends IDjangoApp {

    public abstract IHandle getHandle(IRequest request);

    public abstract void regHandle(final IURLMatcher urlMater);

}