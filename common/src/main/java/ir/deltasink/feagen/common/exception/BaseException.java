package ir.deltasink.feagen.common.exception;

import ir.deltasink.feagen.common.utils.FormattedMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * A custom exception class that extends RuntimeException and logs the error message.
 */
@Slf4j
public class BaseException extends RuntimeException{

    /**
     * Constructs a new BaseException with the specified message.
     * @param message the detail message of the exception
     */
    private BaseException(String message){
        super(message);
        log.error(message);
    }

    /**
     * Constructs a new BaseException with a formatted message using the specified pattern and arguments.
     * @param messagePattern the message pattern to format
     * @param args the arguments to substitute in the message pattern
     */
    public BaseException(String messagePattern, Object... args){
        this(new FormattedMessage(messagePattern, args).getFormattedMessage());
    }
}
