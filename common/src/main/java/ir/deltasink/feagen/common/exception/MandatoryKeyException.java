package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that a mandatory key was not specified in a configuration.
 */
public class MandatoryKeyException extends BaseException {
    public MandatoryKeyException(String messagePattern, Object... args){
        super(messagePattern, args);
    }

    /**
     * Constructs a new MandatoryKeyException with a default message using the specified key name and configuration name.
     * @param keyName the name of the mandatory key that was not specified
     * @param configName the name of the configuration that was violated
     */
    public MandatoryKeyException(String keyName, String configName){
        this("{} must be specified in {}", keyName, configName != null ? configName : "config");
    }
}
