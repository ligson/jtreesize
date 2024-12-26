package org.ligson.jtreesize.core;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {
    public static Set<Class<?>> getClasses(String packageName) throws Exception {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        File directory = new File(resource.toURI());
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
