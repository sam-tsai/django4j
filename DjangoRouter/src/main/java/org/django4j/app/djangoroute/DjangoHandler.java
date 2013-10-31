package org.django4j.app.djangoroute;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.router.IRouter;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public class DjangoHandler  implements IRouter {
    @Override
    public void exec(IRequest request, IResponse response,
                     final AppContext appContext, final Context cfgContext)
            throws Exception {
    	
    }

}