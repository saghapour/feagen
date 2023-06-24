package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that an invalid configuration was provided.
 */
public class InvalidConfigurationException extends BaseException{
    /**
     * Constructs a new InvalidConfigurationException with a formatted message using the specified pattern and parameters.
     * @param messagePattern the message pattern to format
     * @param params the parameters to substitute in the message pattern
     */
    public InvalidConfigurationException(String messagePattern, Object... params){
        super(messagePattern, params);
    }
}
