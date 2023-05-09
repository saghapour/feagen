package ir.deltasink.feagen.config.provider;

import ir.deltasink.feagen.config.reader.ConfigType;
import ir.deltasink.feagen.config.reader.IConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * A factory class that creates different types of configuration providers based on the given configuration type and configuration.
 */
@Slf4j
public class ProviderFactory {
    /**
     * Returns a configuration provider that matches the given configuration type and configuration.
     * @param configType the type of the configuration provider to create
     * @param configs the configuration to use for the provider
     * @return an instance of IConfigProvider that can load configuration from the specified source
     */
    public static IConfigProvider getProvider(ConfigType configType, IConfig configs){
        log.info("Using {} config provider.", configType.name());
        switch (configType){
            case CONSUL: return new ConsulProvider(configs);
            case VAULT: return new VaultProvider(configs);
            case FILE: return new FileProvider(configs);
            case EMBEDDED:
            default: return new EmbeddedProvider(configs);
        }
    }
}
