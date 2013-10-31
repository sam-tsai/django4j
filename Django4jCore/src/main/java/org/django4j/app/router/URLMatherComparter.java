package org.django4j.app.router;

import java.util.Comparator;

public class URLMatherComparter implements Comparator<IURLMatcher> {

	@Override
	public int compare(IURLMatcher o1, IURLMatcher o2) {
		return o1.priporty() - o2.priporty();
	}

}
