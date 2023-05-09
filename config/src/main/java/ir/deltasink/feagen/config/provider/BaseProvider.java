package ir.deltasink.feagen.config.provider;

import ir.deltasink.feagen.config.reader.IConfig;

/**
 * An abstract class that implements the IConfigProvider interface and provides common methods for loading configurations.
 */
public abstract class BaseProvider implements IConfigProvider {
    /**
     * The IConfig object that holds the configurations.
     */
    protected final IConfig configs;

    /**
     * Constructs a new BaseProvider object with the given configuration for the provider.
     * @param configs the provider configuration
     */
    public BaseProvider(IConfig configs){
        this.configs = configs;
    }

    /**
     * Loads a configuration from the provider with the given configuration name.
     * If the configuration is not found, returns an empty string.
     * @param name the file name of the configuration to be loaded without extension.
     *             <p><b>Note:</b>Extension of files in embedded and file provider must be yml.
     *             But in consul and vault, there is no need to have extension. It's a url.
     *             </p>
     * @return the configuration content as a string, or an empty string if not found
     */
    @Override
    public String loadConfig(String name) {
        String content = this.loadFromProvider(name);
        return content == null ? "" : content;
    }

    /**
     * Loads a configuration from the provider with the given configuration name, file name, deployment environment, and double environment flag.
     * The full file name is constructed by appending the name, file name, and deployment environment with dots as separators.
     * If the configuration is not found, returns an empty string.
     * <p>
     *     The name of configuration means which configuration you want to load. For example,
     *     if you want to load pipeline configurations, the name of configuration is 'pipeline'.
     *     Also, it can be possible that the configuration is placed in a separated file.
     *     In order to reduce complication, pipelines configs can be separated in their own files.
     *     For example, the 'pipeline.orders.yml' file can contain orders pipeline and
     *     the 'pipeline.customers.yml' file can contain customers pipeline. We can call this method
     *     by 'pipeline' name and 'orders' fileName in order to load pipeline.order.yml file.
     * </p>
     * <p>
     *     File naming standard is the following based on the bellow assumptions:
     *     (We want to load orders pipeline which is placed in a separated file
     *     in staging environment)
     *     - Load pipeline.yml file at the first and load all pipelines and variables defined in that
     *     - Load pipeline.staging.yml and overwrite configs of the previous configs
     *     - Load pipeline.orders.yml and overwrite configs of the previous configs
     *     - Load pipeline.orders.staging.yml and overwrite configs of the previous configs
     * </p>
     * @param name the name of the configuration to be loaded
     * @param fileName the file name of the configuration to be loaded
     * @param deploymentEnv the deployment environment of the configuration to be loaded
     * @return the configuration content as a string, or an empty string if not found
     */
    @Override
    public String loadConfig(String name, String fileName, String deploymentEnv) {
        StringBuilder fullFileName = new StringBuilder(name);
        if (fileName != null)
            fullFileName.append(String.format(".%s", fileName));

        if (deploymentEnv != null)
            fullFileName.append(String.format(".%s", deploymentEnv));

        return this.loadConfig(fullFileName.toString());
    }

    public abstract String loadFromProvider(String name);
}
