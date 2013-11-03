package org.django4j.app.invoker.handle;

import java.lang.reflect.Method;
import java.util.List;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;

public class NoargInvokerHandle extends InvokerHandle {
	private final List<String> lsReqRep;
	private final int argCount;

	public NoargInvokerHandle(Method method, String className,
			String methodName, List<String> lsReqRep) {
		super(method, className, methodName);
		this.lsReqRep = lsReqRep;
		argCount = lsReqRep.size();
	}

	@Override
	public Object[] getArgs(IRequest req, IResponse rep) {
		Object[] args = new Object[argCount];
		int i = 0;
		for (String reqrep : lsReqRep) {
			args[i] = reqrep.equals("REQ") ? req : rep;
			i++;
		}
		return args;
	}
}
