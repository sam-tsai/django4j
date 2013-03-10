package org.django4j.app.handleroute;

import org.django4j.api.IRequest;

public interface IURLMatcher extends Comparable<IURLMatcher> {
    IHandle match(IRequest request);

    int priporty();
}
