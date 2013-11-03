package org.django4j.app.invoker;

import org.django4j.cache.AbstractCache;

public class InstanceFactory extends AbstractCache<String, Object> {
	private static InstanceFactory _fatory = new InstanceFactory();

	public static InstanceFactory instance() {
		return _fatory;
	}
}
