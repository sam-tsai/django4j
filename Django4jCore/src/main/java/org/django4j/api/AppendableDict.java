package org.django4j.api;

import java.util.Map;
import java.util.Map.Entry;

import org.django4j.app.invoker.handle.InvokerHandle;

public class AppendableDict<T> extends Dictionary<IAppendable<T>> implements
		IAppendableDict<T> {
	public AppendableDict() {
		this(false);
	}

	public AppendableDict(boolean isSorted) {
		super(isSorted);
	}

	public AppendableDict(boolean isSorted, Map<String, IAppendable<T>> theMap) {
		super(isSorted, theMap);
	}

	@Override
	public void append(String key, T value) {
		if (has(key)) {
			IAppendable<T> a = get(key);
			set(key, a.append(value));
		}
		set(key, new SinglelValue<T>(value));
	}

	public void updateSignal(Map<String, T> newmap) {
		for (Entry<String, T> e : newmap.entrySet()) {
			setSingle(e.getKey(), e.getValue());
		}
	}

	@Override
	public void setSingle(String key, T value) {
		set(key, new SinglelValue<T>(value));

	}

	@Override
	public void setDefaultSingle(String key, T value) {
		if (hasno(key)) {
			set(key, new SinglelValue<T>(value));
		}
	}
}
