package org.django4j.api;

public interface IParentDictionary<V> extends IDictionary<V> {
    boolean hasParent();

    IDictionary<V> parent();

    void set2Top(final String key, final V value);

    IParentDictionary<V> newChild();

}
