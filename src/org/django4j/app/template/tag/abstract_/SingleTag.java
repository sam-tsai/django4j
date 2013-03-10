package org.django4j.app.template.tag.abstract_;

import org.django4j.app.template.ITag;

public abstract class SingleTag implements ITag {
    @Override
    public final String getEndToken() {
        return null;
    }

    @Override
    public final String[] getSkipTokens() {
        return null;
    }
}
