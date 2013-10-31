package org.django4j.app.template;

import java.util.Map;

import org.django4j.api.Dictionary;
import org.django4j.api.ParentDictionary;

public class RenderContext extends ParentDictionary<Object> {

    public static RenderContext getChild(final Dictionary<Object> parent) {
        return new RenderContext(parent);
    }

    public static RenderContext getChild(final Dictionary<Object> parent,
                                         final Map<String, Object> map) {
        return new RenderContext(parent, map);
    }

    public RenderContext() {
        super(null);
    }

    public RenderContext(final Dictionary<Object> parent) {
        super(parent, null);
    }

    public RenderContext(final Dictionary<Object> parent,
                         final Map<String, Object> map) {
        super(parent, map);
    }

}
