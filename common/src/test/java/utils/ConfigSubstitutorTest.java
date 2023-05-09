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
    void replace_null_null() {
        String template = null;
        Map<String, Object> params = null;
        assertNull(ConfigSubstitutor.replace(template, params));
    }

    @Test
    void replace_empty_empty() {
        String template = "";
        Map<String, Object> params = new HashMap<>();
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("", result);
    }

    @Test
    void replace_template_empty() {
        String template = "Hello {name}";
        Map<String, Object> params = new HashMap<>();
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("Hello {name}", result);
    }

    @Test
    void replace_template_envs() {
        String template = "Hello ${USER}";
        String result = ConfigSubstitutor.replace(template);
        assertEquals("Hello Sajjad", result);
    }

    @Test
    void replace_template_envs_with_default() {
        String template = "Hello ${USER_NOT_EXIST:Guest}";
        String result = ConfigSubstitutor.replace(template);
        assertEquals("Hello Guest", result);
    }

    @Test
    void replace_template_params_and_envs() {
        String template = "Hello {name}, your home directory is ${HOME:/sa} and your user is ${USER}";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Sajjad");
        String result = ConfigSubstitutor.replace(template, params);
        assertEquals("Hello Sajjad, your home directory is /home/sajjad and your user is Sajjad", result);
    }
}
