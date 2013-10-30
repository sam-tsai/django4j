package org.django4j.app.handleroute;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.context.AppContext;
import org.django4j.context.Context;

public interface IHandle {
    void exec(IRequest request, IResponse response, AppContext appContext,
              Context cfgContext) throws Exception;
}
