package org.django4j.app.invoker.gen;

import java.lang.reflect.Method;

import org.django4j.app.invoker.handle.InvokerHandle;

public interface IGenerator {

	public abstract InvokerHandle gen(Method method, String className,
			String methodName);

}
