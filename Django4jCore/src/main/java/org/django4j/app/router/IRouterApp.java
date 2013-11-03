package org.django4j.app.router;

import org.django4j.api.http.IRequest;
import org.django4j.app.IDjangoApp;
import org.django4j.app.invoker.handle.InvokerHandle;

public interface IRouterApp extends IDjangoApp {

	public abstract InvokerHandle getRouter(IRequest request);

	public abstract void regRouter(final IURLMatcher urlMater);

}