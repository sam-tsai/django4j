package org.django4j.api;

import java.util.Map;

import org.django4j.context.Dictionary;

public class AppendDictionary<T> extends Dictionary<IAppendable<T>> implements
        IAppendDictionary<T> {
    public AppendDictionary() {
        this(false);
    }

    public AppendDictionary(boolean isSorted) {
        super(isSorted);
    }

    public AppendDictionary(boolean isSorted, Map<String, IAppendable<T>> theMap) {
        super(isSorted, theMap);
    }

    @Override
    public void append(String key, T value) {
        if (has(key)) {
            IAppendable<T> a = get(key);
            set(key, a.append(value));
        }
        set(key, new SignalValue<T>(value));
    }

    @Override
    public void setSingle(String key, T value) {
        set(key, new SignalValue<T>(value));

    }

    @Override
    public void setDefaultSingle(String key, T value) {
        if (hasno(key)) {
            set(key, new SignalValue<T>(value));
        }
    }
}
