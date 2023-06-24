package utils;

import ir.deltasink.feagen.common.utils.ConfigSubstitutor;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SystemStubsExtension.class)
public class ConfigSubstitutorTest {
    @SystemStub
    private EnvironmentVariables environmentVariables =
            new EnvironmentVariables("USER", "Sajjad",
                    "HOME", "/home/sajjad");


    @Test
    void replaceNullNull() {
        String template = null;
        Map<String, Object> params = null;
        assertNull(ConfigSubstitutor.replace(template, params));
    }

    @Test
    void replaceEmptyEmpty() {
        String template = "";
        Map<String, Object> params = new HashMap<>();
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("", result);
    }

    @Test
    void replaceTemplateEmpty() {
        String template = "Hello {name}";
        Map<String, Object> params = new HashMap<>();
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("Hello {name}", result);
    }

    @Test
    void replaceTemplateEnvs() {
        String template = "Hello ${USER}";
        String result = ConfigSubstitutor.replace(template);
        assertEquals("Hello Sajjad", result);
    }

    @Test
    void replaceTemplateEnvsWithDefault() {
        String template = "Hello ${USER_NOT_EXIST:Guest}";
        String result = ConfigSubstitutor.replace(template);
        assertEquals("Hello Guest", result);
    }

    @Test
    void replaceTemplateParamsAndEnvs() {
        String template = "Hello {name}, your home directory is ${HOME:/sa} and your user is ${USER}";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Sajjad");
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("Hello Sajjad, your home directory is /home/sajjad and your user is Sajjad", result);
    }
}
