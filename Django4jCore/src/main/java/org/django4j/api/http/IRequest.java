package org.django4j.api.http;

import java.io.InputStream;

import org.django4j.api.QueryDict;

public interface IRequest extends IHttp{

    String path();

    Method action();

    QueryDict get();

    QueryDict post();

    QueryDict request();

    QueryDict rest();



    InputStream readStream();

    String content();
}

