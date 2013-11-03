package org.django4j.app.invoker.gen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.invoker.Q;
import org.django4j.app.invoker.handle.InvokerHandle;
import org.django4j.app.invoker.handle.NamedInvokerHandle;

public class NamedhandleGen implements IGenerator {
    @Override
	public InvokerHandle gen(Method method, String className,
            String methodName) {
        Annotation[][] allParamsAnns = method.getParameterAnnotations();
        Class<?>[] paramTypes = method.getParameterTypes();
        List<String> ArgName = new ArrayList<String>();
        for (int i = 0; i < paramTypes.length; i++) {
            Annotation[] paramAnns = allParamsAnns[i];
            Class<?> t = paramTypes[i];
            if (IRequest.class.isAssignableFrom(t)) {
                ArgName.add("REQ");
                continue;
            }
            if (IResponse.class.isAssignableFrom(t)) {
                ArgName.add("REP");
                continue;
            }
            boolean hasQ = false;
            for (Annotation paramAnn : paramAnns) {
                if (paramAnn.annotationType().equals(Q.class)) {
                    hasQ = true;
                    ArgName.add(((Q) paramAnn).value());
                    break;
                }
            }
            if (!hasQ) {
                return null;
            }
        }
        return new NamedInvokerHandle(method, className, method.getName(),
                ArgName);
    }
}