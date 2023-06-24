package ir.deltasink.feagen.common.factory;

import ir.deltasink.feagen.common.utils.Reflection;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * A subclass of ReflectionFactory that uses annotation values as keys.
 * The class scans a given package for classes that are annotated with a given annotation and creates and stores an instance of each class.
 * The class uses the annotation value as the key for each object.
 * The subclasses of this class must specify the annotation class and the logic for extracting the annotation value from their objects.
 * @param <T> the type of the objects that the factory creates and stores
 * @param <A> the type of the annotation that the objects must have
 */
public abstract class AnnotatedFactory<T, A extends Annotation> extends ReflectionFactory<T> {

    /**
     * A protected constructor that calls the superclass constructor with the given type and package name.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for annotated classes
     */
    protected AnnotatedFactory(Class<T> superType, String packageName) {
        super(superType, packageName);
    }

    /**
     * A protected method that returns a set of classes that are annotated with a given annotation and are located in a given package.
     * The method uses the Reflection.searchAnnotatedClasses method to find the classes using reflection.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for annotated classes
     * @return a set of classes that are annotated with the given annotation and are located in the given package
     */
    @Override
    protected Set<Class<?>> search(Class<T> superType, String packageName) {
        return Reflection.searchAnnotatedClasses(getAnnotationClass(), packageName);
    }

    /**
     * A protected method that returns the key of a given object.
     * The method uses the getAnnotationValue method to get the key from the object's annotation value.
     * @param object the object to get its key
     * @return the key of the object
     */
    protected String getKey(T object){
        return getAnnotationValue(object);
    }

    /**
     * An abstract method that returns the annotation class that the objects must have.
     * The subclasses of this class must implement this method to provide the annotation class for their objects.
     * @return the annotation class that the objects must have
     */
    protected abstract Class<A> getAnnotationClass();

    /**
     * An abstract method that returns the annotation value of a given object.
     * The subclasses of this class must implement this method to provide the logic for extracting the annotation value from their objects.
     * @param object the object to get the annotation value from
     * @return the annotation value of the object
     */
    protected abstract String getAnnotationValue(T object);
}
