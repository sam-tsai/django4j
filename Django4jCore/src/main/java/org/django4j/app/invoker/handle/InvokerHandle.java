package org.django4j.app.invoker.handle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.invoker.InstanceFactory;

public abstract class InvokerHandle {
	private final String className;
	private final String methodName;
	private final Method method;

	public Method method() {
		return method;
	}

	public InvokerHandle(Method method, String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
		this.method = method;
	}

	public String className() {
		return className;
	}

	public String methodName() {
		return methodName;
	}

	protected abstract Object[] getArgs(IRequest req, IResponse rep);

	public Object invoke(IRequest req, IResponse rep) {
		try {
			return method().invoke(
					InstanceFactory.instance().tryGet(className()),
					getArgs(req, rep));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
