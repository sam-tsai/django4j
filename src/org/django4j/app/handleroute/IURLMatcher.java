package org.django4j.app.handleroute;

import org.django4j.api.http.IRequest;

public interface IURLMatcher {
	IHandle match(IRequest request);

	int priporty();
}
