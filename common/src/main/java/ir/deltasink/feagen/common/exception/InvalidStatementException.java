package ir.deltasink.feagen.common.exception;

public class InvalidStatementException extends BaseException{
    public InvalidStatementException(String messagePattern, Object... params){
        super(messagePattern, params);
    }
}
