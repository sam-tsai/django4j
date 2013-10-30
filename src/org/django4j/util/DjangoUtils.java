package org.django4j.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

public class DjangoUtils {

    private static final int BYTE_BUFFER_SIZE = 1024;

    public static void close(final Closeable handel) {
        if (handel != null) {
            try {
                handel.close();
            } catch (final IOException e) {
            }
        }
    }

    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static URL getResource(final String fileName) {
        return getContextClassLoader().getResource(fileName);
    }

    public static Enumeration<URL> getResources(final String fileName) {
        try {
            return getContextClassLoader().getResources(fileName);
        } catch (IOException e) {
        }
        return null;
    }

    public static boolean isEmpty(final Collection<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(final String str) {
        return str == null || str.length() == 0;
    }

    public static <T> boolean isEmpty(final T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static String loadFile(final File file, final String charset) {
        if (file == null) {
            return "file is null";
        }
        final StringBuilder buff = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), Charset.forName(charset)));
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append('\n').append(line);
            }
        } catch (final IOException e) {
        } finally {
            close(reader);
        }
        if (buff.length() != 0) {
            buff.deleteCharAt(0);
        }
        return buff.toString();
    }

    public static void loadFileTo(final File file, final OutputStream os) {
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            final byte[] buf = new byte[BYTE_BUFFER_SIZE];
            int len = fis.read(buf);
            while (len != -1) {
                os.write(buf, 0, len);
                len = fis.read(buf);
            }
        } catch (final IOException e) {
        } finally {
            close(fis);
        }
    }
}
