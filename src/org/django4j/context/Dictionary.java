package org.django4j.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.django4j.api.IDictionary;

public class Dictionary<V> implements IDictionary<V> {

	private final Map<String, V> map;
	private final boolean bSorted;

	public Dictionary() {
		this(false);
	}

	public Dictionary(Map<String, V> theMap) {
		this(false, theMap);
	}

	public Dictionary(boolean isSorted) {
		this.bSorted = isSorted;
		map = bSorted ? new LinkedHashMap<String, V>()
				: new HashMap<String, V>();
	}

	public Dictionary(boolean isSorted, Map<String, V> theMap) {
		this.bSorted = isSorted;
		map = bSorted ? new LinkedHashMap<String, V>()
				: new HashMap<String, V>();
		if (theMap != null) {
			map.putAll(map);
		}
	}

	@Override
	public boolean isEmpty() {
		return map.size() == 0;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public <E extends V> E get(String key) {
		return get(key, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends V> E get(String key, E defaultValue) {
		if (map.containsKey(key)) {
			return (E) map.get(key);
		}
		return defaultValue;
	}

	@Override
	public Map<String, V> items() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V[] values() {
		List<V> tmpList = new ArrayList<V>();
		for (V value : map.values()) {
			tmpList.add(value);
		}
		return (V[]) tmpList.toArray();
	}

	@Override
	public String[] keys() {
		List<String> tmpList = new ArrayList<String>();
		for (String key : map.keySet()) {
			tmpList.add(key);
		}
		return tmpList.toArray(new String[] {});
	}

	@Override
	public void update(IDictionary<V> dict) {
		if (dict == null) {
			return;
		}
		for (String key : dict.keys()) {
			V value = dict.get(key);
			map.put(key, value);
		}
	}

	@Override
	public void set(String key, V value) {
		map.put(key, value);
	}

	@Override
	public void setDefault(String key, V defaultValue) {
		if (!map.containsKey(key)) {
			map.put(key, defaultValue);
		}
	}

	@Override
	public IDictionary<V> copy() {
		IDictionary<V> dict = new Dictionary<V>(bSorted);
		dict.update(this);
		return dict;
	}

	@Override
	public boolean has(String key) {
		return map.containsKey(key);
	}

	@Override
	public boolean hasno(String key) {
		return !has(key);
	}

	@Override
	public void update(Map<String, V> newmap) {
		map.putAll(newmap);
	}

}
