package org.django4j.app.tapestry;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.django4j.api.IRequest;

public class InvokerFactory {

    /**
     * @param args
     */
    public static void create(TaperstryURLMatcher urlMatcher, String className,
            String[] viewNamePrefixs) {
        try {
            final Class<?> pageClass = Class.forName(className);
            if (pageClass.isInterface())
                return;
            int modifiers = pageClass.getModifiers();
            if (Modifier.isAbstract(modifiers)
                    || Modifier.isInterface(modifiers)
                    || !Modifier.isPublic(modifiers)) {
                return;
            }
            final Method[] methods = pageClass.getDeclaredMethods();
            for (Method method : methods) {
                int m_modifiers = pageClass.getModifiers();
                if (!Modifier.isPublic(m_modifiers)) {
                    continue;
                }

                Class<?>[] paramTypes = method.getParameterTypes();
                boolean isFirst = true;
                boolean isOk = true;
                boolean hasRequestParam = false;
                for (Class<?> t : paramTypes) {
                    if (isFirst && IRequest.class.isAssignableFrom(t)) {
                        isFirst = false;
                        hasRequestParam = true;
                        continue;
                    }
                    isFirst = false;
                    if (!t.isAssignableFrom(String.class)) {
                        isOk = false;
                        break;
                    }
                }
                if (isOk) {
                    String mName = method.getName();
                    int argCount = paramTypes.length;
                    HandleInvoker hi = new HandleInvoker(method,
                            hasRequestParam, hasRequestParam ? argCount - 1
                                    : argCount);
                    for (String viewNamePrefix : viewNamePrefixs) {
                        String path = viewNamePrefix + "/" + mName;
                        urlMatcher.addInvoker(path, hi);
                        // if (mName.equalsIgnoreCase("get")) {
                        // urlMatcher.addInvoker(viewNamePrefix, hi);
                        // }
                    }
                }
            }

        } catch (Exception e) {

        }

    }

    public static void main(String[] args) {
        // create("xx.text.Index", "xx.text");
    }
}
