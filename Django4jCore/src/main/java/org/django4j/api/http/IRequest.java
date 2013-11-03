package org.django4j.api.http;

import org.django4j.api.Dict;

import java.io.InputStream;

public interface IRequest extends IHttp {

    String path();

    HttpMethod action();

    Dict get();

    Dict post();

    Dict request();

    InputStream is();

    String content();
}

