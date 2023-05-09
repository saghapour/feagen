package ir.deltasink.feagen.common.exception;

public class MandatoryKeyException extends BaseException {

    public MandatoryKeyException(String keyName){
        this(keyName, null);
    }

    public MandatoryKeyException(String keyName, String configName){
        super("{} must be specified in {}", keyName, configName != null ? configName : "config");
    }
}
