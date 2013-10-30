package org.django4j.app.tapestry;

import org.django4j.DjangoConst;
import org.django4j.app.IDjangoApp;
import org.django4j.app.handleroute.IHandleRouter;
import org.django4j.app.template.ITemplateEngine;
import org.django4j.context.AppContext;
import org.django4j.context.Context;
import org.django4j.util.DjangoUtils;
import org.django4j.util.ResScaner;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaperstryHandle implements IDjangoApp {
    private static final Set<String> INDEX_PAGE_PATTERN = new HashSet<String>();

    static {
        INDEX_PAGE_PATTERN.add("/index");
        INDEX_PAGE_PATTERN.add("/homepage");
        INDEX_PAGE_PATTERN.add("/first");
    }

    @Override
    public String getName() {
        return "TaperStryHandle";
    }

    @Override
    public String[] dependApps() {
        return new String[]{DjangoConst.APP_NAME_ROUTER,
                DjangoConst.APP_NAME_TEMPLATE};
    }

    @Override
    public boolean load(AppContext appContext, Context cfgContext) {
        scanView(appContext, cfgContext);
        return true;
    }

    private void scanView(AppContext appContext, Context cfgContext) {
        final String pagePakageName = cfgContext.get(DjangoConst.APP_PACKAGE,
                "") + ".views";
        final Collection<String> resNameList = ResScaner
                .scanPackage(pagePakageName);
        final int prePackageLength = pagePakageName.length();
        TaperstryURLMatcher urlMatcher = new TaperstryURLMatcher();
        IHandleRouter router = appContext.get(DjangoConst.APP_NAME_ROUTER);
        if (router != null)
            router.regHandle(urlMatcher);
        ITemplateEngine tmplEngine = appContext
                .get(DjangoConst.APP_NAME_TEMPLATE);
        for (final String resName : resNameList) {
            if (ResScaner.isClass(resName)) {
                final String className = ResScaner.getClassName(resName);
                final String relatePath = className.substring(prePackageLength)
                        .replace('.', '/').toLowerCase();
                if (INDEX_PAGE_PATTERN.contains(relatePath)) {
                    InvokerFactory.create(urlMatcher, className, new String[]{
                            relatePath, "/"});
                } else {
                    InvokerFactory.create(urlMatcher, className,
                            new String[]{relatePath});
                }
            } else if (tmplEngine != null && ResScaner.isTempl(resName)) {
                final String tmplName = ResScaner.getHtmlName(resName);
                String relatePath = tmplName.substring(prePackageLength);
                relatePath = relatePath.substring(0, relatePath.length() - 5)
                        .toLowerCase();
                final URL url = DjangoUtils.getResource(tmplName);
                if (url != null) {
                    tmplEngine.regTempl(relatePath, new File(url.getPath()));
                    if (INDEX_PAGE_PATTERN.contains(relatePath)) {
                        tmplEngine.regTempl("/", new File(url.getPath()));
                    }
                }
            }
        }

    }

    @Override
    public boolean unload() {
        return false;
    }
}
