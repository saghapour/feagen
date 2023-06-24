package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that an implementation error occurred.
 */
public class ImplementationException extends BaseException{

    /**
     * Constructs a new ImplementationException with a formatted message using the specified pattern and arguments.
     * @param messagePattern the message pattern to format
     * @param args the arguments to substitute in the message pattern
     */
    public ImplementationException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }

    /**
     * Constructs a new ImplementationException with a default message using the specified exception.
     * @param e the exception that caused the implementation error
     */
    public ImplementationException(Exception e) {
        super(e.getMessage() == null ? "Implementation exception" : e.getMessage());
        e.printStackTrace();
    }
}
