package org.django4j.context;

import org.django4j.api.IDictionary;
import org.django4j.api.IParentDictionary;

import java.util.Map;

public class ParentDictionary<V> extends Dictionary<V> implements
        IParentDictionary<V> {
    private final IDictionary<V> parent;

    public ParentDictionary(IDictionary<V> parent) {
        this.parent = parent;
    }

    public ParentDictionary(IDictionary<V> parent, Map<String, V> theMap) {
        super(false, theMap);
        this.parent = parent;
    }

    public ParentDictionary(IDictionary<V> parent, boolean isSorted) {
        super(isSorted);
        this.parent = parent;
    }

    public ParentDictionary(IDictionary<V> parent, boolean isSorted,
                            Map<String, V> theMap) {
        super(isSorted, theMap);
        this.parent = parent;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public IDictionary<V> parent() {
        return parent;
    }

    @Override
    public final boolean has(final String key) {
        if (super.has(key)) {
            return true;
        }
        if (parent != null) {
            return parent.has(key);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <E extends V> E get(final String key) {
        if (super.has(key)) {
            return (E) super.get(key);
        }
        if (parent != null) {
            return (E)parent.get(key);
        }
        return null;
    }

    @Override
    public final void set2Top(final String key, final V value) {
        if (parent != null) {
            if (parent instanceof IParentDictionary) {
                final IParentDictionary<V> _parent = (IParentDictionary<V>) parent;
                _parent.set2Top(key, value);
                return;
            }
        }
        set(key, value);
    }

    @Override
    public IParentDictionary<V> newChild() {
        return new ParentDictionary<V>(this);
    }

}
