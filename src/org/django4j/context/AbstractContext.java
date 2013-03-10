package org.django4j.context;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContext<T extends Object> {

    private final Map<String, T>     _map = new HashMap<String, T>(5);
    private final AbstractContext<T> _parent;

    public AbstractContext() {
        this(null, null);
    }

    public AbstractContext(final AbstractContext<T> parent) {
        this(parent, null);
    }

    public AbstractContext(final AbstractContext<T> parent,
            final Map<String, T> map) {
        _parent = parent;
        if (map != null) {
            _map.putAll(map);
        }
    }

    public final void cache(final String key, final T value) {
        _map.put(key, value);
    }

    public final void cacheAll(final AbstractContext<T> context) {
        if (context != null) {
            _map.putAll(context._map);
        }
    }

    public final boolean contains(final String key) {
        if (_map.containsKey(key)) {
            return true;
        }
        if (_parent != null) {
            return _parent.contains(key);
        }
        return false;
    }

    public final boolean notContains(final String key) {
        if (_map.containsKey(key)) {
            return false;
        }
        if (_parent != null) {
            return _parent.notContains(key);
        }
        return true;
    }

    public final boolean hasParent() {
        return _parent != null;
    }

    @SuppressWarnings("unchecked")
    public final <E extends T> E tryGet(final String varName) {
        if (_map.containsKey(varName)) {
            return (E) _map.get(varName);
        }
        if (_parent != null) {
            return _parent.tryGet(varName);
        }
        return null;
    }

    protected final AbstractContext<T> getParent() {
        return _parent;
    }
}
