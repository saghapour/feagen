package ir.deltasink.feagen.common.exception;

import java.util.List;

public class IllegalValueException extends BaseException{
    public IllegalValueException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }

    public IllegalValueException(Object value, String configName, List<String> validValues){
        this("Invalid {} value for config {}. It must be on of ", value, configName, validValues);
    }
}
