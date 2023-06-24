package ir.deltasink.feagen.common.factory;

import ir.deltasink.feagen.common.utils.Reflection;
import java.util.*;

/**
 * A subclass of ReflectionFactory that uses subtypes as keys.
 * The class scans a given package for classes that are subtypes of a given type and creates and stores an instance of each class.
 * The class uses the class name as the key for each object.
 * @param <T> the type of the objects that the factory creates and stores
 */
public final class SubTypeFactory<T> extends ReflectionFactory<T> {
    // A static field that holds the singleton instance
    private static SubTypeFactory<?> instance;

    /**
     * A private constructor that calls the superclass constructor with the given type and package name.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     */
    private SubTypeFactory(Class<T> superType, String packageName){
        super(superType, packageName);
    }

    /**
     * A protected method that returns a set of classes that are subtypes of a given type and are located in a given package.
     * The method uses the Reflection.searchSubTypes method to find the classes using reflection.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     * @return a set of classes that are subtypes of the given type and are located in the given package
     */
    protected Set<Class<?>> search(Class<T> superType, String packageName) {
        Set<Class<? extends T>> classSet = Reflection.searchSubTypes(superType, packageName);
        return new HashSet<>(classSet);
    }

    /**
     * A protected method that returns the key of a given object.
     * The method uses the object's class name as the key.
     * @param object the object to get its key
     * @return the key of the object
     */
    @Override
    protected String getKey(T object) {
        return object.getClass().getName();
    }

    /**
     * A public static method that returns the singleton instance of SubTypeFactory.
     * The method uses double-checked locking to ensure thread safety and lazy initialization.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     * @return the singleton instance of SubTypeFactory
     */
    public static <T> SubTypeFactory<?> getInstance(Class<T> superType, String packageName){
        // Reduce the cost of synchronized if instance has been already initialized
        if (instance == null)
            synchronized (SubTypeFactory.class) {
                if (instance == null)
                    instance = new SubTypeFactory<>(superType, packageName);
            }

        return instance;
    }
}
