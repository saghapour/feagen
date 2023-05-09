package ir.deltasink.feagen.common.utils;


import ir.deltasink.feagen.common.exception.MandatoryKeyException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * This class provides methods to access and manipulate environment variables.
 * @see System#getenv()
 * @see System#getenv(String)
 */
@Slf4j
public class Environment {
    public static Map<String, String> getAllEnvs(){
        return System.getenv();
    }

    /**
     * Returns the value of an environment variable with the given key.
     * @param key the name of the environment variable
     * @return the value of the environment variable, or null if not found and not mandatory
     */
    public static String getEnv(String key) {
        return getEnv(key, false);
    }

    /**
     * Returns the value of an environment variable with the given key and a flag indicating whether it is mandatory or not.
     * If the key is not found and the flag is true, throws a MandatoryKeyException.
     * If the key is not found and the flag is false, returns null.
     * @param key the name of the environment variable
     * @param isMandatory a boolean flag indicating whether the environment variable is mandatory or not
     * @return the value of the environment variable, or null if not found and not mandatory
     * @throws MandatoryKeyException if the key is not found and the flag is true
     */
    public static String getEnv(String key, boolean isMandatory) {
        String value = getAllEnvs().get(key);
        if (value == null && isMandatory)
            throw new MandatoryKeyException(key, "Environment Variables");

        if (value == null)
            log.warn("Environment variable `{}` not found", key);

        return value;
    }
}
