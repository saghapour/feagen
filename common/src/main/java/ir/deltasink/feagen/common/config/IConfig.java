package ir.deltasink.feagen.common.config;

import java.util.Map;
import java.util.Set;

public interface IConfig {
    <T> T getAs(String key);

    <T> T getAs(String key, T defaultValue);

    Object get(String key, Object defaultValue);

    IConfig getConfig(String key);

    Set<String> getKeys();

    Boolean containsKey(String key);

    Map<String, Object> getDict();

    IConfig mapToConfig(Map<String, Object> map);

    boolean isNull();

    boolean isEmpty();
}
