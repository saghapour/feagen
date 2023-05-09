package ir.deltasink.feagen.common.exception;

import ir.deltasink.feagen.common.utils.FormattedMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseException extends RuntimeException{
    private BaseException(String message){
        super(message);
        log.error(message);
    }

    public BaseException(String messagePattern, Object... args){
        this(new FormattedMessage(messagePattern, args).getFormattedMessage());
    }
}
