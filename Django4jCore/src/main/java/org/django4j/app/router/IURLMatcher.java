package org.django4j.app.router;

import org.django4j.api.http.IRequest;
import org.django4j.app.invoker.handle.InvokerHandle;

public interface IURLMatcher {
	InvokerHandle match(IRequest request);

	int priporty();
}
