package org.django4j.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class ResScaner {
    private static final Pattern CLASS_NAME_PATTERN = Pattern
            .compile(
                    "^\\p{javaJavaIdentifierStart}[\\p{javaJavaIdentifierPart}&&[^\\$]]*\\.class$",
                    2);

    private static final Pattern FOLDER_NAME_PATTERN = Pattern
            .compile(
                    "^\\p{javaJavaIdentifierStart}[\\p{javaJavaIdentifierPart}]*$",
                    2);

    public static String getClassName(final String resName) {
        return resName.substring(6);
    }

    public static String getHtmlName(final String resName) {
        return resName.substring(5);
    }

    public static String getRelatePath(final String resName,
                                       final String packageName) {
        return resName.substring(packageName.length());
    }

    public static boolean isClass(final String resName) {
        return resName != null && resName.startsWith("CLASS:");
    }

    public static boolean isTempl(final String resName) {
        return resName != null && resName.startsWith("HTML:");
    }

    public static Collection<String> scanPackage(final String packageName) {
        return scanPackage(new String[]{packageName});
    }

    public static Collection<String> scanPackage(final String[] packageNames) {
        final List<String> packageNameList = new ArrayList<String>();
        for (final String packageName : packageNames) {
            String packagePath = packageName.replace('.', '/') + "/";
            packagePath = packagePath.replaceAll("//", "/");
            packageNameList.add(packagePath);
        }
        try {
            return scanPath(packageNameList.toArray(new String[]{}));
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Collection<String> scanPath(final String[] paths)
            throws IOException {
        final Set<String> result = new HashSet<String>(50);
        for (final String path : paths) {
            final Enumeration<URL> urls = DjangoUtils.getResources(path);
            while (urls != null && urls.hasMoreElements()) {
                scanURL(path, result, urls.nextElement());
            }
        }
        return result;
    }

    private static JarFile getJarFile(final URL url) throws IOException {
        final String urlFile = url.getFile();
        int separatorIndex = urlFile.indexOf("!/");
        if (separatorIndex == -1) {
            separatorIndex = urlFile.indexOf(33);
        }
        if (separatorIndex != -1) {
            String jarFileUrl = urlFile.substring(0, separatorIndex);
            if (jarFileUrl.startsWith("file:")) {
                jarFileUrl = jarFileUrl.substring("file:".length());
            }
            return new JarFile(jarFileUrl);
        }
        return null;
    }

    private static boolean isClassName(final String fileName) {
        return ((fileName.endsWith(".class"))
                && (!(fileName.equals("package-info.class"))) && (!(fileName
                .contains("$"))));
    }

    private static boolean isHTMLName(final String fileName) {
        return fileName.toLowerCase().endsWith(".html");
    }

    private static void scanDir(final String packageName, final File dir,
                                final Collection<String> componentClassNames) {
        if ((!(dir.exists())) || (!(dir.isDirectory()))) {
            return;
        }
        for (final File file : dir.listFiles()) {
            final String fileName = file.getName();
            if (file.isDirectory()) {
                scanDir(packageName + "." + fileName, file, componentClassNames);
            } else {
                if ((isClassName(fileName))) {
                    final String className = packageName
                            + "."
                            + fileName.substring(0, fileName.length()
                            - ".class".length());
                    componentClassNames.add("CLASS:" + className);
                } else if ((isHTMLName(fileName))) {
                    componentClassNames.add("HTML:"
                            + packageName.replace('.', '/') + "/" + fileName);
                }
            }
        }
    }

    private static void scanDirStream(final String packagePath,
                                      final URL packageURL, final Collection<String> componentClassNames,
                                      final List<Queued> queue) throws IOException {
        InputStream is;
        try {
            is = new BufferedInputStream(packageURL.openStream());
        } catch (final FileNotFoundException ex) {
            return;
        }

        final Reader reader = new InputStreamReader(is);
        LineNumberReader lineReader = new LineNumberReader(reader);

        String packageName = null;
        try {
            while (true) {
                final String line = lineReader.readLine();

                if (line == null) {
                    break;
                }
                if (packageName == null) {
                    packageName = packagePath.replace('/', '.');
                }
                if (CLASS_NAME_PATTERN.matcher(line).matches()) {

                    final String fileName = line.substring(0, line.length()
                            - ".class".length());
                    if (fileName.equals("package-info")) {
                        continue;
                    }
                    final String fullClassName = packageName + fileName;
                    componentClassNames.add("CLASS:" + fullClassName);
                } else if (FOLDER_NAME_PATTERN.matcher(line).matches()) {
                    final URL newURL = new URL(packageURL.toExternalForm()
                            + line + "/");
                    final String newPackagePath = packagePath + line + "/";
                    queue.add(new Queued(newURL, newPackagePath));
                } else if (line.toLowerCase().endsWith(".html")) {
                    final String fileName = line;
                    final String fullClassName = packageName.replace('.', '/')
                            + fileName;
                    componentClassNames.add("HTML:" + fullClassName);
                }
            }

            lineReader.close();
            lineReader = null;
        } finally {
            if (lineReader == null) {
                return;
            }
            try {
                lineReader.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            lineReader = null;
        }
    }

    private static void scanJarFile(final String packagePath,
                                    final Collection<String> result, final JarFile jarFile) {
        final Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            final String name = (e.nextElement()).getName();
            if (!(name.startsWith(packagePath))) {
                continue;
            }
            final int lastSlashx = name.lastIndexOf(47);
            final String fileName = name.substring(lastSlashx + 1);
            if (isClassName(fileName)) {
                final String className = name.substring(0, lastSlashx + 1)
                        .replace('/', '.')
                        + fileName.substring(0,
                        fileName.length() - ".class".length());
                result.add(className);
            }
        }
    }

    private static void scanURL(final String parentPath,
                                final Collection<String> componentClassNames, final URL url)
            throws IOException {
        final URLConnection connection = url.openConnection();
        JarFile jarFile;
        if (connection instanceof JarURLConnection) {
            jarFile = ((JarURLConnection) connection).getJarFile();
        } else {
            jarFile = getJarFile(url);
        }
        if (jarFile != null) {
            scanJarFile(parentPath, componentClassNames, jarFile);
        } else if (supportsDirStream(url)) {
            final List<Queued> queue = new ArrayList<Queued>();
            queue.add(new Queued(url, parentPath));
            while (!(queue.isEmpty())) {
                final Queued queued = queue.remove(0);
                scanDirStream(queued.getPackagePath(), queued.getPackageURL(),
                        componentClassNames, queue);
            }
        } else {
            String packageName = parentPath.replace("/", ".");
            if (packageName.endsWith(".")) {
                packageName = packageName
                        .substring(0, packageName.length() - 1);
            }
            scanDir(packageName, new File(url.getFile()), componentClassNames);
        }
    }

    private static boolean supportsDirStream(final URL packageURL) {
        InputStream is = null;
        try {
            is = packageURL.openStream();
            return true;
        } catch (final FileNotFoundException ex) {
            return false;
        } catch (final IOException e) {
            return false;
        } finally {
            DjangoUtils.close(is);
        }
    }
}

class Queued {
    private final String packagePath;

    private final URL packageURL;

    public Queued(final URL thePackageURL, final String thePackagePath) {
        this.packageURL = thePackageURL;
        this.packagePath = thePackagePath;
    }

    String getPackagePath() {
        return packagePath;
    }

    URL getPackageURL() {
        return packageURL;
    }
}
