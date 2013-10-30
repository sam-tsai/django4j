package org.django4j.app;

import org.django4j.api.INameable;
import org.django4j.context.AppContext;
import org.django4j.context.Context;

public interface IDjangoApp extends INameable {
    String[] dependApps();

    boolean load(AppContext appContext, Context cfgContext);

    boolean unload();
}
