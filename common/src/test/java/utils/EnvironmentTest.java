package utils;

import ir.deltasink.feagen.common.exception.MandatoryKeyException;
import ir.deltasink.feagen.common.utils.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;


@ExtendWith(SystemStubsExtension.class)
public class EnvironmentTest {
    private final String envKey = "TEST_VAR";
    private final String envKeyNotExist = "TEST_VAR_NOT_EXIST";
    @SystemStub
    private EnvironmentVariables environmentVariables =
            new EnvironmentVariables(envKey, "10");
    @Test
    public void testEnvVar() {
        environmentVariables.set(envKey, "10");
        Assertions.assertEquals("10", Environment.getEnv(envKey));
        Assertions.assertNull(Environment.getEnv(envKeyNotExist));
        Assertions.assertThrows(MandatoryKeyException.class, () -> Environment.getEnv(envKeyNotExist, true, true));
    }
}
