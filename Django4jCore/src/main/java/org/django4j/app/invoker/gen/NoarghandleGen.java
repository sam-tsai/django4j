package org.django4j.app.invoker.gen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.invoker.handle.InvokerHandle;
import org.django4j.app.invoker.handle.NoargInvokerHandle;

public class NoarghandleGen implements IGenerator {
    @Override
    public InvokerHandle gen(Method method, String className,
            String methodName) {
        Class<?>[] paramTypes = method.getParameterTypes();
        List<String> ArgName = new ArrayList<String>();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> t = paramTypes[i];
            if (IRequest.class.isAssignableFrom(t)) {
                ArgName.add("REQ");
                continue;
            }
            if (IResponse.class.isAssignableFrom(t)) {
                ArgName.add("REP");
                continue;
            }
            return null;
        }
        return new NoargInvokerHandle(method, className, methodName,
                ArgName);
    }
}