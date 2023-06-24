package ir.deltasink.feagen.common.exception;

public class ConfigProviderException extends BaseException{
    /**
     * Constructs a new BaseException with a formatted message using the specified pattern and arguments.
     *
     * @param messagePattern the message pattern to format
     * @param args           the arguments to substitute in the message pattern
     */
    public ConfigProviderException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }
}
