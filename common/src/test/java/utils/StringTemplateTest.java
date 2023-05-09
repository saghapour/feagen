package utils;

import ir.deltasink.feagen.common.utils.StringTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SystemStubsExtension.class)
public class StringTemplateTest {
    @SystemStub
    private EnvironmentVariables environmentVariables =
            new EnvironmentVariables("USER", "test");
    @Test
    public void testReplaceWithValidKeyAndValue() {
        String template = "Hello {name}!";
        StringTemplate stringTemplate = new StringTemplate(template);

        stringTemplate.replace("name", "test");

        assertEquals("Hello test!", stringTemplate.value());
    }

    @Test
    public void testReplaceWithNullKeyAndValue() {
        String template = "Hello {name}!";
        StringTemplate stringTemplate = new StringTemplate(template);

        stringTemplate.replace(null, null);

        assertEquals(template, stringTemplate.value());
    }

    @Test
    public void testClear() {
        String template = "Hello {name}!";
        StringTemplate stringTemplate = new StringTemplate(template);

        stringTemplate.replace("name", "test");
        stringTemplate.clear();

        assertEquals(template, stringTemplate.value());
    }

    @Test
    public void testExtractEnvsWithValidEnvVariable() {
        String template = "Hello ${USER:default}!";

        Map<String, String> envs = StringTemplate.extractEnvs(template);

        assertEquals(1, envs.size());
        assertEquals("test", envs.get("USER"));
    }

    @Test
    public void testExtractEnvsWithInvalidEnvVariable() {
        String template = "Hello ${USER_NOT_EXIST}!";

        Map<String, String> envs = StringTemplate.extractEnvs(template);

        assertEquals(0, envs.size());
    }
}
