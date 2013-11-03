package org.django4j.app.invoker.handle;

import java.lang.reflect.Method;
import java.util.List;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;

public class NamedInvokerHandle extends InvokerHandle {
	private String[] argNames;
	private int argCount;

	public NamedInvokerHandle(Method method, String className,
			String methodName, List<String> argNameList) {
		super(method, className, methodName);
		argCount = argNameList.size();
		argNames = argNameList.toArray(new String[argCount]);
	}

	@Override
	public Object[] getArgs(IRequest req, IResponse rep) {
		Object[] argInput = new Object[argCount];
		for (int i = 0; i < argCount; i++) {
			argInput[i] = req.request().get(argNames[i]);
		}
		return argInput;
	}
}
