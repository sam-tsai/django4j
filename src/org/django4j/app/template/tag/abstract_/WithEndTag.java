package org.django4j.app.template.tag.abstract_;

import org.django4j.app.template.ITag;

public abstract class WithEndTag implements ITag {
    @Override
    public final String[] getSkipTokens() {
        return null;
    }
}
