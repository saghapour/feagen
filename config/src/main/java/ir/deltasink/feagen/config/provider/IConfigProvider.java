package ir.deltasink.feagen.config.provider;

/**
 * An interface for loading configuration from different sources.
 */
public interface IConfigProvider {
    /**
     * Loads the configuration content with the given name from the default source.
     * @param name the name of the configuration to load
     * @return the content of the configuration as a string
     */
    String loadConfig(String name);

    /**
     * Loads the configuration content with the given name from the specified file and deployment environment.
     * @param name the name of the configuration to load
     * @param fileName the name of the file to load from
     * @param deploymentEnv the deployment environment to use
     * @return the content of the configuration as a string
     */
    String loadConfig(String name, String fileName, String deploymentEnv);
}
