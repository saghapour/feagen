package ir.deltasink.feagen.common.exception;

import java.util.List;

/**
 * A custom exception class that extends BaseException and indicates that an illegal value was provided for a configuration.
 */
public class IllegalValueException extends BaseException{

    /**
     * Constructs a new IllegalValueException with a formatted message using the specified pattern and arguments.
     * @param messagePattern the message pattern to format
     * @param args the arguments to substitute in the message pattern
     */
    public IllegalValueException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }

    /**
     * Constructs a new IllegalValueException with a default message using the specified value, configuration name and valid values.
     * @param value the illegal value that was provided
     * @param configName the name of the configuration that was violated
     * @param validValues the list of valid values for the configuration
     */
    public IllegalValueException(Object value, String configName, List<String> validValues){
        this("Invalid {} value for config {}. It must be on of ", value, configName, validValues);
    }
}
