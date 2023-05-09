package ir.deltasink.feagen.config.provider;

import ir.deltasink.feagen.common.utils.ResourceUtil;
import ir.deltasink.feagen.config.reader.IConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * A provider that loads configuration from embedded resources.
 */
@Slf4j
public class EmbeddedProvider extends BaseProvider {
    /**
     * Constructs a new EmbeddedProvider with the given configuration.
     * @param config the configuration to use
     */
    public EmbeddedProvider(IConfig config) {
        super(config);
    }

    /**
     * Loads the configuration content from an embedded resource with the given name.
     * @param name the name of the resource to load without extension.
     *             Extension of given name must be .yml.
     * @return the content of the resource as a string
     */
    @Override
    public String loadFromProvider(String name) {
        log.info("Loading {} configs from Embedded.", name);
        return ResourceUtil.loadResourceContent(String.format("%s.yml", name));
    }
}
