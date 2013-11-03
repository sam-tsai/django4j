package org.django4j.cache;

public interface ObjectCache<K extends Object, V extends Object> {
	void cache(K key, V value);

	void clear();

	void remove(K key);

	<T extends V> T tryGet(K key);

}
