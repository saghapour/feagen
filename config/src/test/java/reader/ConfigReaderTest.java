package reader;

import ir.deltasink.feagen.config.provider.IConfigProvider;
import ir.deltasink.feagen.config.reader.ConfigReader;
import ir.deltasink.feagen.config.reader.IConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ConfigReaderTest {
    private final String envStaging = "staging";
    private final String envProduction = "production";
    private final String name = "pipeline";
    private final String fileName = "orders";

    @Mock
    private IConfigProvider configProvider;

    private ConfigReader reader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String pipeline = "orders_common: &common\n  output:\n    source: delta\n    delta.upsert.matched.condition: none";
        // TODO: we should be able to define a variable in base file and use it across all other files
//        String fileNamePipeline = "orders:\n  input:\n    source: postgres\n  <<: *common";
        String fileNamePipeline = "orders:\n  parent: orders_common\n  input:\n    source: postgres";

        when(configProvider.loadConfig(name, null, null)).thenReturn(pipeline);
        when(configProvider.loadConfig(name, null, envStaging)).thenReturn("");
        when(configProvider.loadConfig(name, fileName, null)).thenReturn(fileNamePipeline);
        when(configProvider.loadConfig(name, fileName, envStaging)).thenReturn("");



        reader = new ConfigReader(configProvider, envStaging, new HashMap<>());
    }

    @Test
    void read_shouldReturnMergedConfig_whenConfigsAreLoaded() {

        IConfig pipelineConfig = reader.read(name, fileName);

        // TODO: Test is not completed. Steps and environments must be tested.
        assertEquals(2, pipelineConfig.getKeys().size());
        assertEquals(Set.of("parent", "input", "output"), pipelineConfig.getConfig("orders").getKeys());
        assertEquals("delta", pipelineConfig.getConfig("orders").getConfig("output").getAs("source"));
    }
}
