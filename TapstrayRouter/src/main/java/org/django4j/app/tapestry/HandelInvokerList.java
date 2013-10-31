package org.django4j.app.tapestry;

import java.util.ArrayList;
import java.util.List;

import org.django4j.api.ArrayValue;

public class HandelInvokerList extends ArrayValue<RouterInvoker> implements
        HandleInvokerContainer {
    public HandelInvokerList() {
    }

    public HandelInvokerList(RouterInvoker theValue) {
        super(theValue);
    }

    private final List<RouterInvoker> list = new ArrayList<RouterInvoker>();

    @Override
    public RouterInvoker match(int argsize) {
        RouterInvoker defhi = null;
        for (RouterInvoker hi : list) {
            if (hi.getArgCount() == 0) {
                defhi = hi;
            }
            if (hi.getArgCount() == argsize) {
                return hi;
            }
        }
        return defhi;
    }

}
