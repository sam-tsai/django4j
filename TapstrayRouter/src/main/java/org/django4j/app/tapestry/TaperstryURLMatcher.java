package org.django4j.app.tapestry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.django4j.api.IAppendable;
import org.django4j.api.http.Method;
import org.django4j.api.http.IRequest;
import org.django4j.app.router.IRouter;
import org.django4j.app.router.IURLMatcher;

public class TaperstryURLMatcher implements IURLMatcher {
	private final Map<String, TaperstryURLMatcher> m = new HashMap<String, TaperstryURLMatcher>();
	private IAppendable<RouterInvoker> invokerContainer = null;

	public void addInvoker(String relatePath, RouterInvoker invoker) {
		if (relatePath.equals("/")) {
			setInvoker(invoker);
			return;
		}
		add(new StringTokenizer(relatePath, "/"), invoker);
	}

	private void setInvoker(RouterInvoker invoker) {
		invokerContainer = invokerContainer == null ? invoker
				: invokerContainer.append(invoker);
	}

	private void add(StringTokenizer st, RouterInvoker invoker) {
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

	private IRouter match(IRequest request, StringBuilder stringBuilder,
			StringTokenizer st, Method action, int argCounts) {
		if (!st.hasMoreTokens()) {
			return match(argCounts);
		}
		String cur = st.nextToken();
		if (m.containsKey(cur.toLowerCase())) {
			return m.get(cur).match(request, stringBuilder, st, action,
					argCounts);
		} else if (m.containsKey(action)) {
			// TaperstryURLMatcher matcher = m.get(action);
			return match(action, argCounts);
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
	public IRouter match(IRequest request) {
		String url = request.path();
        Method action = request.action();
		int argCounts = 0;
		if (action.isGet()) {
			argCounts = request.get().size();
		} else if (action.isPost()) {
			argCounts = request.post().size();
		}

		if (url.equals("/")) {
			request.rest().setSingle("Invoker_Path", "/");
			return match(action, argCounts);
		}
		return match(request, new StringBuilder("/"), new StringTokenizer(url,
				"/"), action, argCounts);
	}

	private IRouter match(int argCounts) {
		RouterInvoker defhi = null;
		if (invokerContainer == null)
			return null;
		for (RouterInvoker hi : invokerContainer.list()) {
			if (hi.getArgCount() == 0) {
				defhi = hi;
			}
			if (hi.getArgCount() == argCounts) {
				return hi;
			}
		}
		return defhi;
	}

	private IRouter match(Method action, int argCounts) {
		if (!m.containsKey(action.str())) {
			return null;
		}
		TaperstryURLMatcher matcher = m.get(action.str());
		return matcher.match(argCounts);
	}

	@Override
	public int priporty() {
		return 50;
	}
}
