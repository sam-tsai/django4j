package org.django4j.app.template;

import java.io.File;

import org.django4j.app.IDjangoApp;

public interface ITemplateEngine extends IDjangoApp {

    String getCharSet();

    IFilter getFilter(final String filterName);

    void regFilter(final String filterName, IFilter filter);

    void regTag(final String tagName, ITag tag);

    ITag getTag(final String tagName);

    Template loadTemplate(final File file) throws Exception;

    void regTempl(final String urlPath, final File file);

    Template getTemp(final String urlPath);

}