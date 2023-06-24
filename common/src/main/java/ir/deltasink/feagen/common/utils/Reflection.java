package ir.deltasink.feagen.common.utils;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import static org.reflections.scanners.Scanners.*;

/**
 * A utility class that provides a method to search for classes annotated with a given annotation type.
 * It uses the Reflections library to scan the classpath and find the annotated classes.
 */
public class Reflection {

    /**
     * Returns a set of classes annotated with the given annotation type in the given package name.
     * @param type the annotation type to search for
     * @param packageName the package name to search in
     * @return a set of classes annotated with the type in the package name
     */
    public static <T extends Annotation> Set<Class<?>> searchAnnotatedClasses(Class<T> type, String packageName){
        // Create a configuration builder with the URLs for the package name
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setUrls(ClasspathHelper.forPackage(packageName));
        configBuilder.setScanners(TypesAnnotated);

        // Create a reflections object with the configuration builder
        Reflections reflections = new Reflections(configBuilder);

        // Return the set of classes annotated with the type
        return reflections.getTypesAnnotatedWith(type);
    }

    public static <T extends Annotation> Set<Method> searchAnnotatedMethods(Class<T> type, String packageName){
        // Create a configuration builder with the URLs for the package name
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setUrls(ClasspathHelper.forPackage(packageName));
        configBuilder.setScanners(MethodsAnnotated);

        // Create a reflections object with the configuration builder
        Reflections reflections = new Reflections(configBuilder);

        // Return the set of classes annotated with the type
        return reflections.getMethodsAnnotatedWith(type);
    }

    public static <T> Set<Class<? extends T>> searchSubTypes(Class<T> type, String packageName){
        // Create a configuration builder with the URLs for the package name
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setUrls(ClasspathHelper.forPackage(packageName));
        configBuilder.setScanners(SubTypes);

        // Create a reflections object with the configuration builder
        Reflections reflections = new Reflections(configBuilder);

        // Return the set of classes annotated with the type
        return reflections.getSubTypesOf(type);
    }
}
