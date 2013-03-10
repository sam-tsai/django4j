package org.django4j.app.handleroute;

import org.django4j.api.IRequest;
import org.django4j.api.IResponse;
import org.django4j.context.AppContext;
import org.django4j.context.Context;

public interface IHandle {
    void exec(IRequest request, IResponse response, AppContext appContext,
            Context cfgContext) throws Exception;
}
