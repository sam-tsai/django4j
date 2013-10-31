package org.django4j.app.template;

public interface IVariable {
    Object value(ITemplateEngine tEngine, RenderContext ct) throws Exception;
}
