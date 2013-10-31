package org.django4j.api;


import java.util.ArrayList;
import java.util.List;

public class SinglelValue<T> implements IAppendable<T> {
    private T value;

    public SinglelValue() {
        value = null;
    }

    public SinglelValue(T theValue) {
        value = theValue;
    }

    @Override
    public IAppendable<T> append(T newValue) {
        if (value == null) {
            value = newValue;
            return this;
        }
        return new ArrayValue<T>(value).append(newValue);
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public List<T> list() {
        List<T> ls = new ArrayList<T>(1);
        ls.add(value);
        return ls;
    }

    @Override
    public T get(int index) {
        if (index == 0)
            return value;
        return null;
    }

    @Override
    public int size() {
        return 1;
    }
}
