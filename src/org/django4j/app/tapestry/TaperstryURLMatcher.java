package org.django4j.app.tapestry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.django4j.api.IAppendable;
import org.django4j.api.IRequest;
import org.django4j.app.handleroute.AbstractURLMatcher;
import org.django4j.app.handleroute.IHandle;

public class TaperstryURLMatcher extends AbstractURLMatcher {
    private final Map<String, TaperstryURLMatcher> m                = new HashMap<String, TaperstryURLMatcher>();

    private IAppendable<HandleInvoker>             invokerContainer = null;

    public void addInvoker(String relatePath, HandleInvoker invoker) {
        if (relatePath.equals("/")) {
            setInvoker(invoker);
            return;
        }
        add(new StringTokenizer(relatePath, "/"), invoker);
    }

    private void setInvoker(HandleInvoker invoker) {
        invokerContainer = invokerContainer == null ? invoker
                : invokerContainer.append(invoker);
    }

    private void add(StringTokenizer st, HandleInvoker invoker) {
        if (!st.hasMoreTokens()) {
            setInvoker(invoker);
            return;
        }
        String cur = st.nextToken();
        if (!m.containsKey(cur)) {
            m.put(cur, new TaperstryURLMatcher());
        }
        m.get(cur).add(st, invoker);
    }

    private IHandle match(IRequest request, StringBuilder stringBuilder,
            StringTokenizer st, String action, int argCounts) {
        if (!st.hasMoreTokens()) {
            return match(argCounts);
        }
        String cur = st.nextToken();
        if (m.containsKey(cur.toLowerCase())) {
            return m.get(cur).match(request, stringBuilder, st, action,
                    argCounts);
        } else if (m.containsKey(action)) {
//            TaperstryURLMatcher matcher = m.get(action);
            return match(action,argCounts);
        }
        List<String> params = new ArrayList<String>();
        params.add(cur);
        int i = 1;
        request.rest().setSingle("REst_Param_" + (i++), cur);
        while (st.hasMoreTokens()) {
            cur = st.nextToken();
            params.add(cur);
            request.rest().setSingle("REst_Param_" + (i++), cur);
        }
        request.rest().setSingle("Invoker_Path", stringBuilder.toString());
        return match(argCounts + params.size());
    }

    @Override
    public IHandle match(IRequest request) {
        String url = request.path();
        String action = request.action();
        int argCounts = 0;
        if (action.equals("get")) {
            argCounts = request.get().size();
        } else if (action.equals("post")) {
            argCounts = request.post().size();
        }

        if (url.equals("/")) {
            request.rest().setSingle("Invoker_Path", "/");
            return match(action,argCounts);
        }
        return match(request, new StringBuilder("/"), new StringTokenizer(url,
                "/"), action, argCounts);
    }
    private IHandle match(int argCounts) {
        HandleInvoker defhi = null;
        if (invokerContainer == null )
            return null;
        for (HandleInvoker hi :invokerContainer.list()) {
            if (hi.getArgCount() == 0) {
                defhi = hi;
            }
            if (hi.getArgCount() == argCounts) {
                return hi;
            }
        }
        return defhi;
    }
    private IHandle match(String method,int argCounts) {
        if(!m.containsKey(method))
        {
        	return null;
        }
        TaperstryURLMatcher matcher = m.get(method);
        return matcher.match(argCounts);
    }
}
