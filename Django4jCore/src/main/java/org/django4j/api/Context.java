package org.django4j.api;

import java.util.Map;

public final class Context extends Dictionary<Object> {

    private static final ThreadLocal<Context> tl = new ThreadLocal<Context>();

    public static void clean() {
        tl.remove();
    }

    public static Context local() {
        Context ct = tl.get();
        if (ct == null) {
            ct = new Context();
            tl.set(ct);
        }
        return ct;
    }

    public Context() {
        super();
    }

    public Context(final Map<String, Object> map) {
        super(false, map);
    }
}
