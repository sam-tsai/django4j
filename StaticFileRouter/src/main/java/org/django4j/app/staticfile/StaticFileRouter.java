package org.django4j.app.staticfile;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;

import org.django4j.DjangoConst;
import org.django4j.api.http.IRequest;
import org.django4j.api.http.IResponse;
import org.django4j.app.router.IRouter;
import org.django4j.app.template.TemplateConst;
import org.django4j.api.AppContext;
import org.django4j.api.Context;
import org.django4j.util.DjangoUtils;

public class StaticFileRouter implements IRouter {
    @Override
    public void exec(IRequest request, IResponse response1,
                     final AppContext appContext, final Context cfgContext)
            throws Exception {
        final String filePath = cfgContext.get(DjangoConst.$CUR_URL);
        final URL url = DjangoUtils.getResource(filePath);
        if (url == null) {
            return;
        }
        final File file = new File(url.getPath());
        final String fileName = file.getName();
        final String mimeType = StaticFileUtil.getMimeType(fileName);
        final ServletResponse response = Context.local().get(
                TemplateConst.$REPONSE);
        final boolean isTextMime = StaticFileUtil.isTextMime(mimeType);
        response.setContentType(mimeType);
        if (isTextMime) {
            response.setCharacterEncoding(cfgContext.get(
                    DjangoConst.TEMPLATE_FILE_CHARSET, "utf-8"));
        }
        DjangoUtils.loadFileTo(file, response.getOutputStream());
        return;
    }
}

class StaticFileUtil {
    private static final String MIME_DEFAULT = "application/octet-stream";
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<String, String>();

    static {
        MIME_TYPE_MAP.put("css", "text/css");
        MIME_TYPE_MAP.put("htm", "text/html");
        MIME_TYPE_MAP.put("html", "text/html");
        MIME_TYPE_MAP.put("xml", "text/xml");
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("asc", "text/plain");
        MIME_TYPE_MAP.put("gif", "image/gif");
        MIME_TYPE_MAP.put("jpg", "image/jpeg");
        MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        MIME_TYPE_MAP.put("png", "image/png");
        MIME_TYPE_MAP.put("mp3", "audio/mpeg");
        MIME_TYPE_MAP.put("m3u", "audio/mpeg-url");
        MIME_TYPE_MAP.put("mp4", "video/mp4");
        MIME_TYPE_MAP.put("ogv", "video/ogg");
        MIME_TYPE_MAP.put("flv", "video/x-flv");
        MIME_TYPE_MAP.put("mov", "video/quicktime");
        MIME_TYPE_MAP.put("swf", "application/x-shockwave-flash");
        MIME_TYPE_MAP.put("js", "application/javascript");
        MIME_TYPE_MAP.put("pdf", "application/pdf");
        MIME_TYPE_MAP.put("doc", "application/msword");
        MIME_TYPE_MAP.put("ogg", "application/x-ogg");
        MIME_TYPE_MAP.put("zip", "application/octet-stream");
        MIME_TYPE_MAP.put("exe", "application/octet-stream");
        MIME_TYPE_MAP.put("class", "application/octet-stream");
        MIME_TYPE_MAP.put("", "application/octet-stream");
    }

    public static String getMimeType(final String fileName) {
        final String ext = getFileExt(fileName);
        if (MIME_TYPE_MAP.containsKey(ext)) {
            return MIME_TYPE_MAP.get(ext);
        } else {
            return MIME_DEFAULT;
        }
    }

    public static boolean isTextMime(final String mimeType) {
        if (mimeType != null && mimeType.startsWith("text/")) {
            return true;
        }
        return false;
    }

    private static String getFileExt(final String fileName) {
        final int index = fileName.lastIndexOf('.');
        if (index > -1) {
            return fileName.substring(index + 1).toLowerCase();
        }
        return "";
    }
}
