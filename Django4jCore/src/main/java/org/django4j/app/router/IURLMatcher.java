package org.django4j.app.router;

import org.django4j.api.http.IRequest;

public interface IURLMatcher {
	IRouter match(IRequest request);

	int priporty();
}
