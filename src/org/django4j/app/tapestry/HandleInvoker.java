package org.django4j.app.tapestry;

import java.lang.reflect.Method;
import java.util.Map;

import org.django4j.DjangoConst;
import org.django4j.api.IDictionary;
import org.django4j.api.IRequest;
import org.django4j.api.IResponse;
import org.django4j.api.SignalValue;
import org.django4j.api.string.AbstractString;
import org.django4j.app.handleroute.IHandle;
import org.django4j.app.handleroute.defaulthandle.ReflectUtils;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.app.template.RenderContext;
import org.django4j.context.AppContext;
import org.django4j.context.Context;

public class HandleInvoker extends SignalValue<HandleInvoker> implements
		IHandle {
	private final Method invokerMethod;
	private final int argCount;

	public int getArgCount() {
		return argCount;
	}

	private final boolean hasRequest;

	public HandleInvoker(Method invokerMethod, boolean hasRequest, int argCount) {
		this.invokerMethod = invokerMethod;
		this.argCount = argCount;
		this.hasRequest = hasRequest;
		append(this);
	}

	public HandleInvoker match(int argsize) {
		if (argCount == 0 || argCount == argsize) {
			return this;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void exec(IRequest request, IResponse response,
			AppContext appContext, Context cfgContext) throws Exception {
		final Class<?> pageClass = invokerMethod.getDeclaringClass();
		final Object pageObj = pageClass.newInstance();
		ReflectUtils.assignFieldValue(pageClass, pageObj, request, response);

		Object[] params = new Object[argCount + (hasRequest ? 1 : 0)];
		int index = 0;
		if (hasRequest) {
			params[index++] = request;
		}
		for (int i = 0; i < argCount; i++) {
			params[index++] = request.rest().get("REst_Param_"+(i+1)).value();
		}
		Object rtObj = invokerMethod.invoke(pageObj, params);
		if (rtObj instanceof String || rtObj instanceof AbstractString) {
			response.contentType("text/html; charset=UTF-8");
			response.write(rtObj.toString());
			response.flush();
		} else if (rtObj instanceof IResponse) {
			// TODO
		} else {
			// TODO templeta render
			ITemplateEngine tmplEngine = appContext
					.get(DjangoConst.APP_NAME_TEMPLATE);
			Context ctx = new Context();
			if (rtObj instanceof Map) {
				ctx.update((Map<String, Object>) rtObj);
			} else if (rtObj instanceof IDictionary) {
				ctx.update((IDictionary<Object>) rtObj);
			} else {
				ctx.set("$", rtObj);
			}
			String tempUrl = request.path();
			if (request.rest().has("Invoker_Path")) {
				tempUrl = request.rest().get("Invoker_Path").value();
			}
			String rt = tmplEngine.getTemp(tempUrl).render(
					new RenderContext(ctx));
			response.contentType("text/html; charset=UTF-8");
			response.write(rt);
		}
	}
}
