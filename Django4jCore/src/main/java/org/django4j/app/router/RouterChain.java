//package org.django4j.app.router;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.ServletResponse;
//
//import org.django4j.DjangoConst;
//import org.django4j.api.http.IRequest;
//import org.django4j.api.http.IResponse;
//import org.django4j.app.template.TemplateConst;
//import org.django4j.api.AppContext;
//import org.django4j.api.Context;
//
//public final class RouterChain implements IRouter {
//    private IRouter exceptionHandle = new ExceptionRouter();
//    private final List<IRouter> handles = new ArrayList<IRouter>();
//
//    public RouterChain addHandel(final IRouter handle) {
//        handles.add(handle);
//        return this;
//    }
//
//    @Override
//    public void exec(IRequest request, IResponse response,
//                     final AppContext appContext, final Context cfgContext) {
//        final Iterator<IRouter> iter = handles.iterator();
//        try {
//            while (iter.hasNext()) {
//                final IRouter h = iter.next();
//                h.exec(request, response, appContext, cfgContext);
//            }
//        } catch (final Exception e) {
//            cfgContext.set(DjangoConst.$PRE_RESULT, e);
//            try {
//                exceptionHandle.exec(request, response, appContext, cfgContext);
//            } catch (final Exception e1) {
//            }
//        }
//    }
//
//    public void setExceptionHandel(final IRouter handle) {
//        exceptionHandle = handle;
//    }
//}
//
