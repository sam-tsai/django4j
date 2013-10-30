package org.django4j.app.handleroute.defaulthandle;

import org.django4j.api.INameable;
import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.template.TemplateConst;
import org.django4j.context.Context;
import org.django4j.util.ResScaner;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class ReflectUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Object> T newInterfaceInstance(
            final String className, final Class<?> clsInterface) {
        try {
            final Class<?> cls = Class.forName(className);
            if (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                return null;
            }
            if (clsInterface.isAssignableFrom(cls)) {
                return (T) cls.newInstance();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends INameable> void scanClass(
            final String[] packageNames, final Class<T> clsInterface,
            final Map<String, T> map) {
        final Collection<String> classNameList = ResScaner
                .scanPackage(packageNames);
        for (final String resName : classNameList) {
            if (ResScaner.isClass(resName)) {
                final T t = newInterfaceInstance(
                        ResScaner.getClassName(resName), clsInterface);
                if (t != null) {
                    map.put(t.getName(), t);
                }
            }
        }
    }

    public static void assignFieldValue(final Class<?> pageClass,
                                        final Object pageObj, IRequest request, IResponse response) {
        if (pageObj == null) {
            return;
        }
        final Context ct = Context.local();
        try {
            final Field[] fields = pageClass.getDeclaredFields();
            for (final Field f : fields) {
                final Class<?> fieldType = f.getType();
                if (ServletRequest.class.isAssignableFrom(fieldType)) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    f.set(pageObj, ct.get(TemplateConst.$REQUEST));
                } else if (ServletResponse.class.isAssignableFrom(fieldType)) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    f.set(pageObj, ct.get(TemplateConst.$REPONSE));
                }
            }
        } catch (final IllegalAccessException e) {
            return;
        }
        return;
    }

}
