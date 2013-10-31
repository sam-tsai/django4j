package org.django4j.app.template;

import java.io.File;
import java.net.URL;

import org.django4j.app.template.ast.RootNode;
import org.django4j.util.DjangoUtils;

public class Template {
    private final File file;

    private final RootNode root;
    private final ITemplateEngine tEngine;

    public Template(final File file, final ITemplateEngine tEngine)
            throws Exception {
        this.file = file;
        this.tEngine = tEngine;
        root = new Parser(file).parse(tEngine);
    }

    public Template(final String filePath, final ITemplateEngine tEngine)
            throws Exception {
        this.tEngine = tEngine;
        final URL url = DjangoUtils.getResource(filePath);
        if (url != null) {
            file = new File(url.getPath());
        } else {
            final File f = new File(filePath);
            if (f.exists()) {
                file = f;
            } else {
                file = null;
            }
        }
        if (file != null) {
            root = new Parser(file).parse(tEngine);
        } else {
            root = null;
        }
    }

    public File getFile() {
        return file;
    }

    public RootNode getRoot() {
        return root;
    }

    public String render(final RenderContext ct) throws Exception {
        return root.render(tEngine, ct);
    }
}
