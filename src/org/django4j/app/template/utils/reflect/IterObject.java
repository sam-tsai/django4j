package org.django4j.app.template.utils.reflect;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

public class IterObject {
    private final Iterator<?> iter;

    public IterObject(final Object obj) {
        if (obj == null) {
            iter = null;
            return;
        } else if (obj instanceof Iterator<?>) {
            iter = (Iterator<?>) obj;
        } else if (obj instanceof Iterable<?>) {
            iter = ((Iterable<?>) obj).iterator();
        } else if (obj.getClass().isArray()) {
            iter = new ArrayIter(obj);
        } else if (obj instanceof Class && ((Class<?>) obj).isEnum()) {
            final Class<?> c = (Class<?>) obj;
            iter = new ArrayIter(c.getEnumConstants());
        } else if (obj instanceof Map<?, ?>) {
            iter = ((Map<?, ?>) obj).entrySet().iterator();
        } else {
            iter = null;
        }
    }

    public boolean hasNext() {
        return iter.hasNext();
    }

    public Object next() {
        return iter.next();
    }

}

class ArrayIter implements Iterator<Object> {
    private final Object array;

    private int          index = 0;

    private final int    len;

    public ArrayIter(final Object obj) {
        if (obj == null || !(obj.getClass().isArray())) {
            array = null;
            len = 0;
            return;
        }
        array = obj;
        len = Array.getLength(array);
    }

    @Override
    public boolean hasNext() {
        return index < len;
    }

    @Override
    public Object next() {
        return Array.get(array, index++);
    }

    @Override
    public void remove() {

    }
}
