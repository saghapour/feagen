package ir.deltasink.feagen.config.reader;

import ir.deltasink.feagen.common.utils.ConfigSubstitutor;
import ir.deltasink.feagen.common.utils.MapUtils;
import ir.deltasink.feagen.common.utils.yaml.YamlUtil;
import ir.deltasink.feagen.config.constant.Variables;
import ir.deltasink.feagen.config.provider.IConfigProvider;
import ir.deltasink.feagen.config.provider.ProviderFactory;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that reads configuration from different sources and merges them according to a naming standard.
 */
public class ConfigReader {
    private final IConfigProvider configProvider;
    private final String environment;
    private final Map<String, Object> variablesToReplace;

    /**
     * Constructs a new ConfigReader with the given configuration type, provider configuration, environment and variables to replace.
     * @param configProvider config provider
     * @param environment specifies the deployment environment
     * @param variablesToReplace contains all the variables that must be replaced in the configurations. It
     *                           includes all placeholders and input arguments. This class automatically
     *                           detects environment variables in the configurations and replaces them with system
     *                           environments. At first, all placeholders with format ${NAME:DEFAULT}
     *                           will be replaced then with format {NAME:DEFAULT}.
     */
    public ConfigReader(@NotNull IConfigProvider configProvider,
                        String environment,
                        @NotNull Map<String, Object> variablesToReplace){
        this.environment = environment;
        this.variablesToReplace = variablesToReplace;
        this.configProvider = configProvider;
    }

    /**
     * Reads the configuration with the given name from the default file name and merges it with other configurations according to the naming standard.
     * @param name the name of the configuration to read
     * @return a YamlConfig instance that represents the merged configuration
     */
    public YamlConfig read(String name){
        return this.read(name, null);
    }

    /**
     * Reads the configuration with the given name and file name and merges it with other configurations according to the naming standard.
     * @param name the name of the configuration to read
     * @param fileName the file name of the configuration to read
     * @return a YamlConfig instance that represents the merged configuration
     */
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public YamlConfig read(String name, String fileName){
        Map<String, Object> configs = new HashMap<>();

        // Step 1
        configs = this.mergeConfig(configs, name, null, null);

        // Step 2
        // Read env file configs. e.g. pipeline.staging.yml, pipeline.production.yml which staging is
        // deployment env. It's based on environment variables.
        configs = this.mergeConfig(configs, name, null, this.environment);

        // Step 3
        // Read pipeline file based configs. We have already read pipeline[.env].yml and now, we're going to read
        // the third part of naming standard which is [.file-name] if -f is specified in cli options.
        // This should be in form of pipeline.file_name.yml
        configs = this.mergeConfig(configs, name, fileName, null);

        // Step 4
        // Here we're going to read the forth part of naming standard.
        // the third part of naming standard which is [.file-name][.env] if -f is specified in cli options.
        // This should be in form of pipeline.file_name.env.yml
        configs = this.mergeConfig(configs, name, fileName, this.environment);


        for (String key: configs.keySet()){
            Object o = configs.get(key);
            if (o instanceof Map && ((Map<String, Object>)o).containsKey(Variables.PARENT)){
                Map<String, Object> childConfig = (Map<String, Object>)configs.get(key);
                Map<String, Object> parentConfig = (Map<String, Object>)configs.get(childConfig.getOrDefault(Variables.PARENT, new HashMap<>()));
                childConfig = MapUtils.combineDict(parentConfig, childConfig);
                configs.put(key, childConfig);
            }
        }

        return new YamlConfig(configs);
    }

    /**
     * Creates a new ConfigReader with the given configuration type, provider configuration, environment and variables to replace.
     * @param configProvider config provider
     * @param environment specifies the deployment environment
     * @param variablesToReplace contains all the variables that must be replaced in the configurations. It
     *                           includes all placeholders and input arguments. This class automatically
     *                           detects environment variables in the configurations and replaces them with system
     *                           environments. At first, all placeholders with format ${NAME:DEFAULT}
     *                           will be replaced then with format {NAME:DEFAULT}.
     */
    public static ConfigReader getInstance(@NotNull IConfigProvider configProvider,
                                           String environment,
                                           @NotNull Map<String, Object> variablesToReplace){
        return new ConfigReader(configProvider, environment, variablesToReplace);
    }

    /**
     * Merges the base configuration with the configuration loaded from the given name, file name and environment.
     * @param baseConfig the base configuration to merge with
     * @param name the name of the configuration to load
     * @param fileName the file name of the configuration to load
     * @param env the deployment environment
     * @return a map of strings and objects that represents the merged configuration
     */
    private Map<String, Object> mergeConfig(Map<String, Object> baseConfig, String name, String fileName,
                                            String env){
        String configsContent = this.configProvider.loadConfig(name, fileName, env);
        configsContent = substituteConfigVariables(configsContent);
        val configs = YamlUtil.toConfigMap(configsContent);
        return MapUtils.combineDict(baseConfig, configs);
    }

    private String substituteConfigVariables(String content){
        return ConfigSubstitutor.replace(content, this.variablesToReplace);
    }
}
