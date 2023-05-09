package utils.yaml;

import ir.deltasink.feagen.common.exception.InvalidConfigurationException;
import ir.deltasink.feagen.common.utils.yaml.YamlUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class YamlUtilTest {
    @Test
    public void testToConfigMapWithValidYaml() {
        String yamlString = "source: postgres\nlimit: 1000\ncolumns: [id, customer_id]\non: id\nswitch: on";
        Map<String, Object> map = YamlUtil.toConfigMap(yamlString);
        assertEquals("postgres", map.get("source"));
        assertEquals(1000, map.get("limit"));
        assertTrue(map.get("columns") instanceof List);
        List<String> hobbies = (List<String>) map.get("columns");
        assertEquals(2, hobbies.size());
        assertEquals("id", hobbies.get(0));
        assertEquals("customer_id", hobbies.get(1));
        assertEquals("id", map.get("on"));
        assertEquals("on", map.get("switch"));
    }

    @Test
    public void testToConfigMapWithInvalidYaml() {
        String yamlString = "source: postgres\nlimit: 1000\ncolumns: [id, customer_id\nswitch: on";
        assertThrows(InvalidConfigurationException.class, () -> YamlUtil.toConfigMap(yamlString));
    }

    @Test
    public void testToConfigMapWithNullOrEmpty() {
        Map<String, Object> map1 = YamlUtil.toConfigMap(null);
        assertTrue(map1.isEmpty());
        Map<String, Object> map2 = YamlUtil.toConfigMap("");
        assertTrue(map2.isEmpty());
    }
}
