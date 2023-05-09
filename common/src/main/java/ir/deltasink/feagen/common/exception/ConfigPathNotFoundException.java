package ir.deltasink.feagen.common.exception;

public class ConfigPathNotFoundException extends BaseException{

    public ConfigPathNotFoundException(String messagePattern, Object... args) {
        super(messagePattern, args);
    }

    public ConfigPathNotFoundException(String path){
        this("Config path `{}` not found.", path);
    }
}
