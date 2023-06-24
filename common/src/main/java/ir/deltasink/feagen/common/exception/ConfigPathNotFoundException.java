package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that a configuration path was not found.
 */
public class ConfigPathNotFoundException extends BaseException{

    /**
     * Constructs a new ConfigPathNotFoundException with a formatted message using the specified pattern and arguments.
     * @param messagePattern the message pattern to format
     * @param args the arguments to substitute in the message pattern
     */
    public ConfigPathNotFoundException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }

    /**
     * Constructs a new ConfigPathNotFoundException with a default message using the specified path.
     * @param path the configuration path that was not found
     */
    public ConfigPathNotFoundException(String path){
        this("Config path `{}` not found.", path);
    }
}
