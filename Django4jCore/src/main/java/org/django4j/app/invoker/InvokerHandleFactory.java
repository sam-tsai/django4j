package org.django4j.app.invoker;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.django4j.app.invoker.gen.IGenerator;
import org.django4j.app.invoker.gen.NamedhandleGen;
import org.django4j.app.invoker.gen.NoarghandleGen;
import org.django4j.app.invoker.handle.InvokerHandle;

public class InvokerHandleFactory {
	private static IGenerator[] geners = new IGenerator[] {
			new NoarghandleGen(), new NamedhandleGen() };

	public static Map<String, InvokerHandle> getHandle(String className) {
		Map<String, InvokerHandle> map = new HashMap<String, InvokerHandle>();
		try {
			final Class<?> pageClass = tryLloadClass(className, map);
			if (pageClass == null) {
				return map;
			}

			final Method[] methods = pageClass.getDeclaredMethods();
			for (Method method : methods) {
				int m_modifiers = method.getModifiers();
				if (!Modifier.isPublic(m_modifiers)
						|| Modifier.isAbstract(m_modifiers)
						|| Modifier.isStatic(m_modifiers)) {
					continue;
				}
				Class<?> rtCls = method.getReturnType();
				if (void.class.equals(rtCls)) {
					continue;
				}
				String methodName = method.getName();
				for (IGenerator gener : geners) {
					InvokerHandle h = gener.gen(method, className, methodName);
					if (h != null) {
						map.put(methodName, h);
						break;
					}
				}
			}

			Object obj = pageClass.newInstance();
			InstanceFactory.instance().cache(className, obj);

		} catch (Exception e) {
		}
		return map;
	}

	private static Class<?> tryLloadClass(String className,
			Map<String, InvokerHandle> map) throws ClassNotFoundException {
		final Class<?> pageClass = Class.forName(className);
		if (pageClass.isInterface())
			return null;
		int modifiers = pageClass.getModifiers();
		if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)
				|| !Modifier.isPublic(modifiers)) {
			return null;
		}
		return pageClass;
	}
}
