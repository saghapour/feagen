package utils;

import ir.deltasink.feagen.common.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilsTest {

    @Test
    void combineDictNullNull() {
        Map<String, Object> map1 = null;
        Map<String, Object> map2 = null;

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void combineDictMap1Null() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        Map<String, Object> map2 = null;

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertEquals(map1, result);
    }

    @Test
    void combineDictNullMap2() {
        Map<String, Object> map1 = null;
        Map<String, Object> map2 = new HashMap<>();
        map2.put("c", 3);
        map2.put("d", 4);

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertEquals(map2, result);
    }

    @Test
    void combineDictMap1Map2NoOverlap() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("c", 3);
        map2.put("d", 4);

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(1, result.get("a"));
        assertEquals(2, result.get("b"));
        assertEquals(3, result.get("c"));
        assertEquals(4, result.get("d"));
    }

    @Test
    void combineDictMap1Map2WithOverlap() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("b", 20);
        map2.put("c", 3);

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get("a"));
        assertEquals(20, result.get("b"));
        assertEquals(3, result.get("c"));
    }

    @Test
    void combineDictMap1Map2WithNestedMaps() {
        Map<String, Object> submap1 = new HashMap<>();
        submap1.put("x", 10);
        submap1.put("y", 20);
        Map<String, Object> submap2 = new HashMap<>();
        submap2.put("y", 200);
        submap2.put("z", 30);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", 1);
        map1.put("b", submap1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("b", submap2);
        map2.put("c", 3);

        Map<String, Object> result = MapUtils.combineDict(map1, map2);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get("a"));
        assertEquals(3, result.get("c"));
        assertTrue(result.get("b") instanceof Map);
        @SuppressWarnings("unchecked")
        Map<String, Object> resultMap = (Map<String, Object>) result.get("b");
        assertEquals(3, resultMap.size());
        assertEquals(10, resultMap.get("x"));
        assertEquals(200, resultMap.get("y"));
        assertEquals(30, resultMap.get("z"));
    }
}
