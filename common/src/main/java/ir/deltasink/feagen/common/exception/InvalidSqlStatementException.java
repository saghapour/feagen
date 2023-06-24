package ir.deltasink.feagen.common.exception;

/**
 * A custom exception class that extends BaseException and indicates that an invalid statement was provided.
 */
public class InvalidSqlStatementException extends BaseException{
    /**
     * Constructs a new InvalidStatementException with a formatted message using the specified pattern and parameters.
     * @param messagePattern the message pattern to format
     * @param params the parameters to substitute in the message pattern
     */
    public InvalidSqlStatementException(String messagePattern, Object... params){
        super(messagePattern, params);
    }
}