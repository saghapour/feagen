package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that a configuration was not provided.
 */
public class ConfigNotProvidedException extends BaseException{

    /**
     * Constructs a new ConfigNotProvidedException with a formatted message using the specified pattern and arguments.
     * @param messagePattern the message pattern to format
     * @param args the arguments to substitute in the message pattern
     */
    public ConfigNotProvidedException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }
}
