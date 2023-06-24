package ir.deltasink.feagen.common.exception;

public class NetworkException extends BaseException{
    public NetworkException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }
}
