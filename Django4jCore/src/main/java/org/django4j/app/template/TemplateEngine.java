package org.django4j.app.template;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.django4j.DjangoConst;
import org.django4j.util.ReflectUtils;
import org.django4j.app.template.cache.TemplateCache;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public class TemplateEngine implements ITemplateEngine {
    private static final String[] DEFAULT_FILTER_PACKAGE = new String[]{"org.django4j.app.template.filter"};
    private static final String[] DEFAULT_TAG_PACKAGE = new String[]{"org.django4j.app.template.tag"};
    private final TemplateCache<File, Template> _cache = new TemplateCache<File, Template>();
    private String charSet = "utf-8";
    private final Map<String, IFilter> filterMap = new HashMap<String, IFilter>();
    private final Map<String, ITag> tagMap = new HashMap<String, ITag>();
    private final Map<String, File> tmplFileMap = new HashMap<String, File>();

    public TemplateEngine() {

    }

    @Override
    public String[] dependApps() {
        return null;
    }

    @Override
    public String getCharSet() {
        return charSet;
    }

    @Override
    public IFilter getFilter(final String filterName) {
        return filterMap.get(filterName);
    }

    @Override
    public void regFilter(final String filterName, IFilter filter) {
        filterMap.put(filterName, filter);
    }

    @Override
    public void regTag(final String tagName, ITag tag) {
        tagMap.put(tagName, tag);
    }

    @Override
    public String getName() {
        return DjangoConst.APP_NAME_TEMPLATE;
    }

    @Override
    public ITag getTag(final String tagName) {
        return tagMap.get(tagName);
    }

    @Override
    public boolean load(AppContext appContext, Context cfgContext) {
        ReflectUtils.scanClass(cfgContext.get(DjangoConst.TEMPLATE_FILTER,
                DEFAULT_FILTER_PACKAGE), IFilter.class, filterMap);
        ReflectUtils.scanClass(
                cfgContext.get(DjangoConst.TEMPLATE_TAG, DEFAULT_TAG_PACKAGE),
                ITag.class, tagMap);
        charSet = cfgContext.get(DjangoConst.TEMPLATE_FILE_CHARSET, charSet);
        return false;
    }

    @Override
    public Template loadTemplate(final File file) throws Exception {
        Template t = _cache.tryGet(file);
        if (t == null) {
            t = new Template(file, this);
            _cache.cache(file, t);
        }
        return t;
    }

    @Override
    public boolean unload() {
        _cache.clear();
        filterMap.clear();
        tagMap.clear();
        return true;
    }

    @Override
    public void regTempl(String urlPath, File file) {
        try {
            loadTemplate(file);
            tmplFileMap.put(urlPath, file);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Template getTemp(String urlPath) {
        if (tmplFileMap.containsKey(urlPath)) {
            try {
                return loadTemplate(tmplFileMap.get(urlPath));
            } catch (Exception e) {
            }
        }
        return null;
    }

}
