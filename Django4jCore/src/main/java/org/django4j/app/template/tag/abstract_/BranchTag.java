package org.django4j.app.template.tag.abstract_;

import org.django4j.app.template.ITag;

public abstract class BranchTag implements ITag {
    @Override
    public final String getEndToken() {
        return null;
    }
}
