package ir.deltasink.feagen.common.exception;

public class NotImplementedException extends BaseException{
    /**
     * Constructs a new BaseException with a formatted message using the specified pattern and arguments.
     *
     * @param messagePattern the message pattern to format
     * @param args           the arguments to substitute in the message pattern
     */
    public NotImplementedException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }
}
