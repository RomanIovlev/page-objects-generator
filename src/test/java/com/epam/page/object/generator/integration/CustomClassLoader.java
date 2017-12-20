package com.epam.page.object.generator.integration;

import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is our own classloader. Strictly speaking, we can use URLClassLoader implementation
 * to generate all classes except classes that represent site which we are parsing
 * (creating it's page objects). It's happening because of PageObjectGenerator class
 * implementation: for all sites POG object generates the same Site.java class with the same full name
 * for each site (test.site.Site). So after first run (for the first site) JVM already has class
 * named test.site.Site and it won't upload class with the same name one more time, although it's
 * completely different class for other site. We use our implementation to force this uploading
 */
public class CustomClassLoader extends ClassLoader {
    private static final String CLASS_EXTENSION = ".class";
    private static final String TEST_PACKAGE = "test";
    private static final String MANUAL_PACKAGE = "manual";

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith(TEST_PACKAGE) || name.startsWith(MANUAL_PACKAGE)) {
            try (InputStream is = getParent()
                    .getResourceAsStream(name.replace('.', '/') + CLASS_EXTENSION)
            ) {
                byte[] buf = IOUtils.toByteArray(is);
                return defineClass(name, buf, 0, buf.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name + " class wasn't found", e);
            }
        }
        return getParent().loadClass(name);
    }
}
