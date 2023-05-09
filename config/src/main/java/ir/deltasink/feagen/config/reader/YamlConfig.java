package ir.deltasink.feagen.config.reader;

import ir.deltasink.feagen.common.exception.IllegalValueException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class that represents a configuration in YAML format.
 */
@Slf4j
public class YamlConfig implements IConfig, IConfigBuilder, Serializable {
    private final Map<String, Object> dict;
    private Map<String, Object> simpleValues;
    private Map<String, YamlConfig> complexValues;

    /**
     * Constructs a new YamlConfig with the given dictionary of key-value pairs.
     * @param dict the dictionary of key-value pairs
     */
    public YamlConfig(Map<String, Object> dict) {
        this.dict = dict;
        if (null != dict)
            this.setConfig();

        if (null == this.simpleValues)
            this.simpleValues = new HashMap<>();
        if (null == this.complexValues)
            this.complexValues = new HashMap<>();
    }

    /**
     * Sets the configuration by separating the simple and complex values from the dictionary.
     */
    @SuppressWarnings("unchecked")
    private void setConfig() {
        for (Map.Entry<String, Object> entry : dict.entrySet()) {
            if (entry.getValue() instanceof HashMap) {
                if (null == complexValues)
                    complexValues = new HashMap<>();
                complexValues.put(entry.getKey(), new YamlConfig((Map<String, Object>) entry.getValue()));
            } else {
                if (null == simpleValues)
                    simpleValues = new HashMap<>();
                simpleValues.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Checks if the configuration contains the given key.
     * @param key the key to check
     * @return true if the key exists, false otherwise
     */
    public Boolean containsKey(String key) {
        return this.dict.containsKey(key);
    }

    /**
     * Gets the value associated with the given key as a generic type.
     * @param key the key to get the value for
     * @return the value as a generic type, or null if the key does not exist
     */
    public <T> T getAs(String key) {
        return getAs(key, null);
    }

    /**
     * Gets the value associated with the given key as a generic type, or returns a default value if the key does not exist.
     * @param key the key to get the value for
     * @param defaultValue the default value to return if the key does not exist
     * @return the value as a generic type, or the default value if the key does not exist
     * @throws IllegalValueException if the value cannot be cast to the generic type
     */
    @SuppressWarnings("unchecked")
    public <T> T getAs(String key, T defaultValue) {
        val value = get(key, defaultValue);
        try {
            if (value != null)
                return (T) value;
            return null;
        }
        catch (ClassCastException ex) {
            throw new IllegalValueException("It seems value {} is not valid for {} with Error {}", value, key, ex);
        }
    }

    /**
     * Gets the value associated with the given key as an object, or returns a default value if the key does not exist.
     * @param key the key to get the value for
     * @param defaultValue the default value to return if the key does not exist
     * @return the value as an object, or the default value if the key does not exist
     */
    @Override
    public Object get(String key, Object defaultValue) {
        if (!simpleValues.containsKey(key))
            log.warn("There is no {} key in config file. It's set to {} as default value.", key, defaultValue);

        return this.simpleValues.getOrDefault(key, defaultValue);
    }

    /**
     * Gets the configuration associated with the given key as an IConfig instance.
     * @param key the key to get the configuration for
     * @return an IConfig instance that represents the configuration, or an empty YamlConfig if the key does not exist
     */
    public IConfig getConfig(String key) {
        return this.complexValues.getOrDefault(key, new YamlConfig(null));
    }

    /**
     * Gets all the keys in this configuration.
     * @return a set of strings that contains all the keys
     */
    public Set<String> getKeys(){
        return this.dict.keySet();
    }

    /**
     * Gets the dictionary of key-value pairs in this configuration.
     * @return a map of strings and objects that represents the dictionary
     */
    public Map<String, Object> getDict() {
        return this.dict;
    }

    /**
     * Maps a given dictionary of key-value pairs to a YamlConfig instance.
     * @param map the dictionary of key-value pairs
     * @return a YamlConfig instance that represents the dictionary
     */
    public IConfig mapToConfig(Map<String, Object> map){
        return new YamlConfig(map);
    }

    /**
     * Checks if this configuration is null.
     * @return true if the dictionary of this configuration is null, false otherwise
     */
    public boolean isNull(){
        return this.dict == null;
    }

    /**
     * Returns an empty YamlConfig instance.
     * @return a YamlConfig instance with an empty dictionary
     */
    public static IConfig empty(){
        return new YamlConfig(new HashMap<>());
    }

    /**
     * Returns a new YamlConfig builder instance.
     * @return an IConfigBuilder instance that can create a YamlConfig
     */
    public static IConfigBuilder builder() {
        return new YamlConfig(new HashMap<>());
    }

    /**
     * Sets a key-value pair in this configuration.
     * @param key the key to set
     * @param value the value to set
     * @return this configuration builder instance
     */
    @Override
    public IConfigBuilder set(String key, Object value) {
        this.dict.put(key, value);
        return this;
    }

    /**
     * Returns this configuration instance.
     * @return this configuration instance
     */
    @Override
    public IConfig getOrCreate() {
        return this;
    }
}
