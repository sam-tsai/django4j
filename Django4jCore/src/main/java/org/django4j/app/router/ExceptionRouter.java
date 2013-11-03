//package org.django4j.app.router;
//
//import org.django4j.DjangoConst;
//import org.django4j.api.AppContext;
//import org.django4j.api.Context;
//import org.django4j.api.http.IRequest;
//import org.django4j.api.http.IResponse;
//import org.django4j.app.template.TemplateConst;
//
//import javax.servlet.ServletResponse;
//
///**
// * Created with IntelliJ IDEA.
// * User: neverend
// * Date: 13-10-31
// * Time: 下午10:11
// * To change this template use File | Settings | File Templates.
// */
//public class ExceptionRouter implements IRouter {
//    @Override
//    public void exec(IRequest request, IResponse response,
//                     final AppContext appContext, final Context cfgContext)
//            throws Exception {
//        final Throwable throwable = cfgContext.get(DjangoConst.$PRE_RESULT);
//        final ServletResponse servletResponse = Context.local().get(
//                TemplateConst.$REPONSE);
//        servletResponse.setContentType("text/plain");
//        servletResponse.setCharacterEncoding(cfgContext.get(
//                DjangoConst.TEMPLATE_FILE_CHARSET, "utf-8"));
//        throwable.printStackTrace(servletResponse.getWriter());
//    }
//}
