package org.django4j.app.template.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.django4j.cache.ObjectCache;

public class TemplateCache<File, Template> implements
        ObjectCache<File, Template> {
    private final HashMap<File, SoftReference<Template>> cache = new HashMap<File, SoftReference<Template>>();

    @Override
    public synchronized void cache(final File f, final Template t) {
        cache.put(f, new SoftReference<Template>(t));
    }

    @Override
    public synchronized void clear() {
        cache.clear();
    }

    @Override
    public synchronized void remove(final File f) {
        SoftReference<Template> r = cache.remove(f);
        r.enqueue();
        r = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Template> T tryGet(final File f) {
        final SoftReference<Template> r = cache.get(f);
        if (r != null) {
            final Template t = r.get();
            if (t == null) {
                remove(f);
            }
            return ((T) t);
        }
        return null;
    }
}
