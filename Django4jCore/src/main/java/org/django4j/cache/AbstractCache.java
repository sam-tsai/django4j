package org.django4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class AbstractCache<K, V> implements ObjectCache<K, V> {
	private final HashMap<K, SoftReference<V>> cache = new HashMap<K, SoftReference<V>>();

	@Override
	public synchronized void cache(final K k, final V v) {
		cache.put(k, new SoftReference<V>(v));
	}

	@Override
	public synchronized void clear() {
		cache.clear();
	}

	@Override
	public synchronized void remove(final K k) {
		cache.remove(k);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends V> T tryGet(K k) {
		final SoftReference<V> r = cache.get(k);
		if (r != null) {
			final V v = r.get();
			if (v == null) {
				remove(k);
			}
			return ((T) v);
		}
		return null;
	}

}
