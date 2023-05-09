package ir.deltasink.feagen.config.provider;

import ir.deltasink.feagen.common.exception.ConfigPathNotFoundException;
import ir.deltasink.feagen.config.constant.Defaults;
import ir.deltasink.feagen.config.constant.Variables;
import ir.deltasink.feagen.config.reader.IConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A provider that loads configuration from a file system.
 */
@Slf4j
public class FileProvider extends BaseProvider{
    /**
     * Constructs a new FileProvider with the given configuration.
     * @param configs the configuration to use
     */
    public FileProvider(IConfig configs){
        super(configs);
    }

    /**
     * Loads the configuration content from a file with the given name.
     * @param name the name of the file to load without extension.
     *             Extension of given name must be .yml.
     * @return the content of the file as a string
     * @throws ConfigPathNotFoundException if the file does not exist or cannot be read
     */
    @Override
    public String loadFromProvider(String name) {
        log.info("Loading {} configs from FileProvider.", name);
        val path = Paths.get(configs.getAs(Variables.CONFIG_FILE_PATH, Defaults.CONFIG_FILE_PATH), String.format("%s.yml", name));
        try {
            return Files.readString(path);
        }
        catch (IOException e) {
            throw new ConfigPathNotFoundException(path.toString());
        }
    }
}
