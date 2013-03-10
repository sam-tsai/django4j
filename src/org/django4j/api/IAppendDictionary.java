package org.django4j.api;

public interface IAppendDictionary<T> extends IDictionary<IAppendable<T>> {
    void append(String key, T value);

    void setSingle(String key, T value);

    void setDefaultSingle(String key, T value);
}
