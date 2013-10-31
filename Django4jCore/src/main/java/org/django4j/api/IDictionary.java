package org.django4j.api;

import java.util.Map;

public interface IDictionary<V> {

    boolean isEmpty();

    int size();

    boolean has(String key);

    boolean hasno(String key);

    <E extends V> E get(String key);

    <E extends V> E get(String key, E defaultValue);

    Map<String, V> items();

    V[] values();

    String[] keys();

    void update(IDictionary<V> dict);

    void update(Map<String, V> newmap);

    void set(String key, V value);

    void setDefault(String key, V defaultValue);

    IDictionary<V> copy();

}
