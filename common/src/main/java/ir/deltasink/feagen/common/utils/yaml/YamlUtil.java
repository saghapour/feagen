package ir.deltasink.feagen.common.utils.yaml;

import ir.deltasink.feagen.common.exception.InvalidConfigurationException;
import ir.deltasink.feagen.common.utils.StringTemplate;
import lombok.val;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility methods for working with YAML files.
 */
public class YamlUtil {
    /**
     * Converts a string representing a YAML content to a map of key-value pairs.
     * If the content is null or empty, returns an empty map.
     * Uses a custom resolver to avoid resolving values like "on" or "off" as booleans.
     * @param content the string to be converted
     * @return a map of key-value pairs corresponding to the content
     */
    public static Map<String, Object> toConfigMap(String content) throws InvalidConfigurationException{
        if (content == null || content.length() == 0)
            return new HashMap<>();

        // TODO: make sure that all methods that assume env replacement will be occurred here, will refine their strategy
//        StringTemplate template = new StringTemplate(content);
//        template.replaceEnvironmentVariables();
//        content = template.value();

        val yaml = new Yaml(
                new SafeConstructor(new LoaderOptions()),
                new Representer(new DumperOptions()), new DumperOptions(),
                new CustomResolver());
        try {
            return yaml.load(content);
        }
        catch (YAMLException ex){
            throw new InvalidConfigurationException("Input is not a valid yaml config. Error: {}, Config: {}", ex.getMessage(), content);
        }
    }
}
