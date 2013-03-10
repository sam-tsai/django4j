package org.django4j.api;

import java.util.ArrayList;
import java.util.List;

public class ArrayValue<T> implements IAppendable<T> {
    private List<T> list = new ArrayList<T>();

    public ArrayValue() {
    }

    public ArrayValue(T theValue) {
        list.add(theValue);
    }

    @Override
    public IAppendable<T> append(T newValue) {
        list.add(newValue);
        return this;
    }

    @Override
    public T value() {
        int size = list.size();
        if (size == 0)
            return null;
        return list.get(size - 1);
    }

    @Override
    public List<T> list() {
        return list;
    }

    @Override
    public T get(int index) {
        if (index < list.size() && index >= 0)
            return list.get(index);
        return null;
    }

    @Override
    public int size() {
        return list.size();
    }
}
