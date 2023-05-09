package ir.deltasink.feagen.common.exception;

public class InvalidConfigurationException extends BaseException{
    public InvalidConfigurationException(String messagePattern, Object... params){
        super(messagePattern, params);
    }
}
