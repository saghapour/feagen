package ir.deltasink.feagen.config.provider;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import ir.deltasink.feagen.config.constant.Variables;
import ir.deltasink.feagen.config.reader.IConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Paths;

/**
 * A class that extends the BaseProvider class and loads configurations from Consul.
 * Consul is a distributed service mesh that provides service discovery, configuration, and coordination features.
 */
@Slf4j
public class ConsulProvider extends BaseProvider{
    /**
     * The KeyValueClient object that interacts with the Consul key-value store.
     */
    private final KeyValueClient client;

    /**
     * Constructs a new ConsulProvider object with the given IConfig object.
     * The IConfig object should contain the information about the Consul URL, token, HTTPS flag, and base path.
     * @param configs the IConfig object to be used
     */
    public ConsulProvider(IConfig configs){
        super(configs);
        client = Consul.builder()
                .withHttps(configs.getAs(Variables.CONFIG_CONSUL_HTTPS, true))
                .withTokenAuth(configs.getAs(Variables.CONFIG_CONSUL_TOKEN, ""))
                .withUrl(configs.getAs(Variables.CONFIG_CONSUL_URL, ""))
                .build()
                .keyValueClient();

    }

    /**
     * Loads a configuration from Consul with the given name.
     * The name is appended to the base path specified in the IConfig object.
     * @param name the name of the configuration to be loaded
     * @return the configuration content as a string, or an empty string if not found
     */
    @Override
    public String loadFromProvider(String name) {
        log.info("Loading {} configs from Consul.", name);
        val fullPath = Paths.get(configs.getAs(Variables.CONFIG_CONSUL_BASE_PATH, ""), name).toString();
        val value =  client.getValueAsString(fullPath);
        return value.orElse("");
    }
}
