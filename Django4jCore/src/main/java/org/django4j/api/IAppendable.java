package org.django4j.api;

import java.util.List;

public interface IAppendable<T> {
    IAppendable<T> append(T value);

    T value();

    T get(int i);

    int size();

    List<T> list();
}
