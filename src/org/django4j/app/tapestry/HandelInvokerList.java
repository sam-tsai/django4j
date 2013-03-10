package org.django4j.app.tapestry;

import java.util.ArrayList;
import java.util.List;

import org.django4j.api.ArrayValue;

public class HandelInvokerList extends ArrayValue<HandleInvoker> implements
        HandleInvokerContainer {
    public HandelInvokerList() {
    }

    public HandelInvokerList(HandleInvoker theValue) {
        super(theValue);
    }

    private final List<HandleInvoker> list = new ArrayList<HandleInvoker>();

    @Override
    public HandleInvoker match(int argsize) {
        HandleInvoker defhi = null;
        for (HandleInvoker hi : list) {
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
