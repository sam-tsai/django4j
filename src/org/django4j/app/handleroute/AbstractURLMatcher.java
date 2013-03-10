package org.django4j.app.handleroute;

public abstract class AbstractURLMatcher implements IURLMatcher {

    @Override
    public int compareTo(IURLMatcher o) {
        return o.priporty() - priporty();
    }

    @Override
    public int priporty() {
        return 0;
    }

}
