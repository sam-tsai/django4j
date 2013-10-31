package org.django4j.app.router;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletResponse;

import org.django4j.DjangoConst;
import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.template.TemplateConst;
import org.django4j.api.AppContext;
import org.django4j.api.Context;

public final class HandleChain implements IHandle {
    private IHandle exceptionHandle = new ExceptionHandle();
    private final List<IHandle> handles = new ArrayList<IHandle>();

    public HandleChain addHandel(final IHandle handle) {
        handles.add(handle);
        return this;
    }

    @Override
    public void exec(IRequest request, IResponse response,
                     final AppContext appContext, final Context cfgContext) {
        final Iterator<IHandle> iter = handles.iterator();
        try {
            while (iter.hasNext()) {
                final IHandle h = iter.next();
                h.exec(request, response, appContext, cfgContext);
            }
        } catch (final Exception e) {
            cfgContext.set(DjangoConst.$PRE_RESULT, e);
            try {
                exceptionHandle.exec(request, response, appContext, cfgContext);
            } catch (final Exception e1) {
            }
        }
    }

    public void setExceptionHandel(final IHandle handle) {
        exceptionHandle = handle;
    }
}

class ExceptionHandle implements IHandle {
    @Override
    public void exec(IRequest request, IResponse response,
                     final AppContext appContext, final Context cfgContext)
            throws Exception {
        final Throwable throwable = cfgContext.get(DjangoConst.$PRE_RESULT);
        final ServletResponse servletResponse = Context.local().get(
                TemplateConst.$REPONSE);
        servletResponse.setContentType("text/plain");
        servletResponse.setCharacterEncoding(cfgContext.get(
                DjangoConst.TEMPLATE_FILE_CHARSET, "utf-8"));
        throwable.printStackTrace(servletResponse.getWriter());
    }
}
