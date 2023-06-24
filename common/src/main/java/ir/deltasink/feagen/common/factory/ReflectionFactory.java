package ir.deltasink.feagen.common.factory;

import ir.deltasink.feagen.common.exception.ImplementationException;
import ir.deltasink.feagen.common.exception.InvalidConfigurationException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An abstract class that represents a factory that uses reflection to create and store objects of a given type.
 * The subclasses of this class must implement the search and getKey methods to provide the logic for finding and identifying the objects.
 * The objects are stored in a map with their keys as the map keys.
 * The factory is thread-safe and can be queried by keys or values.
 * @param <T> the type of the objects that the factory creates and stores
 */
public abstract class ReflectionFactory<T> {
    // A map that holds the objects and their keys
    private final Map<String, T> objects = new HashMap<>();

    /**
     * A protected constructor that scans a given package for classes that are subtypes of a given type and creates and stores an instance of each class.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     */
    protected ReflectionFactory(Class<T> superType, String packageName){
        this.init(superType, packageName);
    }

    /**
     * Initialize the class to scan a given package for classes that are subtypes of a given type and create and store an instance of each class.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     */
    private void init(Class<T> superType, String packageName){
        for (Class<?> cls : this.search(superType, packageName)) {
            storeObject(cls);
        }
    }

    /**
     * A method that creates an instance of a given class and stores it in the map with its key.
     * The method uses reflection to invoke the no-argument constructor of the class and calls the getKey method to get its key.
     * The method throws an ImplementationException if any reflection-related exception occurs during the instantiation process.
     * @param cls the class to instantiate and store
     */
    private void storeObject(Class<?> cls) {
        try {
            T object = (T) cls.getConstructor().newInstance();
            objects.put(getKey(object), object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ImplementationException(e);
        }
    }

    /**
     * A public method that returns a set of all keys registered in the factory.
     * @return a set of all keys registered in the factory
     */
    public Set<String> keys(){
        return this.objects.keySet();
    }

    /**
     * A public method that returns a collection of all objects stored in the factory.
     * @return a collection of all objects stored in the factory
     */
    public Collection<T> values(){
        return this.objects.values();
    }

    /**
     * A public method that returns an object from the factory based on its key.
     * The method throws an InvalidConfigurationException if there is no object with the given key in the factory.
     * @param value the key of the object to return
     * @return the object with the given key
     */
    public T get(String value) {
        if (!objects.containsKey(value))
            throw new InvalidConfigurationException("There is no {} in registered factory", value);

        return objects.get(value);
    }

    /**
     * An abstract method that returns a set of classes that are subtypes of a given type and are located in a given package.
     * The subclasses of this class must implement this method to provide the logic for finding the classes using reflection.
     * @param superType the type of the objects that the factory creates and stores
     * @param packageName the name of the package to scan for subtypes
     * @return a set of classes that are subtypes of the given type and are located in the given package
     */
    protected abstract Set<Class<?>> search(Class<T> superType, String packageName);

    /**
     * An abstract method that returns the key of a given object.
     * The subclasses of this class must implement this method to provide the logic for identifying the objects using their properties or annotations.
     * @param object the object to get its key
     * @return the key of the object
     */
    protected abstract String getKey(T object);

}
