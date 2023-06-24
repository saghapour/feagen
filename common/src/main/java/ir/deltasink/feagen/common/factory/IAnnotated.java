package ir.deltasink.feagen.common.factory;

/**
 * An interface that defines a common method for objects that are annotated with a specific annotation and can be created and stored by an {@link AnnotatedFactory}.
 * The objects must implement this interface to provide a way to obtain their annotation value, which is used as the key for the factory map.
 */
public interface IAnnotated {

    /**
     * A method that returns the annotation value of the object.
     * The annotation value is a string that identifies the object and is obtained from the annotation that the object has.
     * @return the annotation value of the object
     */
    String getAnnotationValue();
}
