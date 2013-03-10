package org.django4j.app.staticfile;

import java.util.regex.Pattern;

import org.django4j.api.IRequest;
import org.django4j.app.handleroute.AbstractURLMatcher;
import org.django4j.app.handleroute.IHandle;

public class StaticFileUrlMather extends AbstractURLMatcher {
    private final static Pattern p            = Pattern
                                                      .compile("^/staticfile/.*$");

    private final IHandle        staticHandle = new StaticFileHandle();

    @Override
    public IHandle match(IRequest request) {
        if (p.matcher(request.path()).find())
            return staticHandle;
        return null;
    }

    @Override
    public int priporty() {
        return 100;
    }

}
