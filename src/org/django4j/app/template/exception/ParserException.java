package org.django4j.app.template.exception;

public class ParserException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParserException() {

    }

    public ParserException(final String msg) {
        super(msg);
    }
}
