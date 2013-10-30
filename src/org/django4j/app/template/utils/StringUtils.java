package org.django4j.app.template.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static String convertString(final String str,
                                       final Map<Character, String> trasnMap) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : str.toCharArray()) {
            if (trasnMap.containsKey(c)) {
                sb.append(trasnMap.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isBooleanString(final String str) {
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (c < '0' && c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitalString(final String str) {
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static List<String> splitString2List(final String content,
                                                final String splitReg) {
        final String[] contentSplit = content.split(splitReg);
        final List<String> dotStringList = new ArrayList<String>();
        for (final String _context_ : contentSplit) {
            if (_context_.isEmpty()) {
                continue;
            }
            dotStringList.add(_context_);
        }
        return dotStringList;
    }
}
