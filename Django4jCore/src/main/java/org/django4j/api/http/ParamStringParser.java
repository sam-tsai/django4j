package org.django4j.api.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.django4j.api.Dict;

public class ParamStringParser {

	public static Dict parseParams(String string) {
		Dict qd = new Dict();
		if (string == null)
			return qd;
		int mode = 0, keystartpos = 0, keylastpos = 0, valstartpos = 0;
		char[] cs = string.toCharArray();
		int i = 0;
		for (; i < cs.length; i++) {
			char c = cs[i];
			if (c == '&') {
				put(mode, keystartpos, keylastpos, valstartpos, cs, qd, i);
				keylastpos = keystartpos = i + 1;
				mode = 0;
			} else if (mode == 0) {
				if (c == '=') {
					keylastpos = i;
					valstartpos = i + 1;
					mode = 1;
				}
			}
		}
		put(mode, keystartpos, keylastpos, valstartpos, cs, qd, i);
		return qd;
	}

	private static void put(int mode, int keystartpos, int keylastpos,
			int valstartpos, char[] cs, Dict qd, int index) {
		String key = String.valueOf(cs, keystartpos, (mode == 0 ? index
				: keylastpos) - keystartpos);
		String val = mode == 0 ? null : String.valueOf(cs, valstartpos, index
				- valstartpos);
		if (key != null && key.trim().length() != 0)
			qd.append(decode(key), decode(val));
	}

	private static String decode(String str) {
		if (str == null)
			return null;
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
}
