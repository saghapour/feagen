package ir.deltasink.feagen.config.provider;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.google.common.annotations.VisibleForTesting;
import ir.deltasink.feagen.config.constant.Variables;
import ir.deltasink.feagen.config.reader.IConfig;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

/**
 * A provider that loads configuration from a Vault server.
 */
@Slf4j
public class VaultProvider extends BaseProvider {
    private final Vault vault;
    private final IConfig configs;

    /**
     * Constructs a new VaultProvider with the given configuration.
     *
     * @param configs the configuration to use
     * @throws RuntimeException if the Vault connection cannot be established
     */
    public VaultProvider(IConfig configs) {
        super(configs);
        this.configs = configs;
        final VaultConfig config;
        try {
            config = new VaultConfig()
                    .address(configs.getAs(Variables.CONFIG_VAULT_URL, ""))
                    .token(configs.getAs(Variables.CONFIG_VAULT_TOKEN, ""))
                    .openTimeout(configs.getAs(Variables.CONFIG_VAULT_OPEN_TIMEOUT, 5))
                    .readTimeout(configs.getAs(Variables.CONFIG_VAULT_READ_TIMEOUT, 30))
                    .build();
            vault = new Vault(config);
        } catch (VaultException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the configuration content from a Vault server with the given name.
     *
     * @param name the name of the configuration to load
     * @return the content of the configuration as a string
     * @throws RuntimeException if the Vault server cannot be accessed or the configuration cannot be found
     */
    @Override
    public String loadFromProvider(String name) {
        log.info("Loading {} configs from Vault.", name);
        try {
            return vault
                    .logical()
                    .read(Paths.get(configs.getAs(Variables.CONFIG_VAULT_BASE_PATH, ""), name).toString())
                    .getData()
                    .get(configs.getAs(Variables.CONFIG_VAULT_VARIABLE_NAME, ""));
        } catch (VaultException e) {
            throw new RuntimeException(e);
        }
    }
}
