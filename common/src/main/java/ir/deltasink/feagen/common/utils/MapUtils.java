package ir.deltasink.feagen.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides utility methods for working with maps.
 */
public class MapUtils {

    /**
     * This method combines two maps into one map by merging the values of
     * the common keys. If the values are also maps, then they are recursively
     * combined. If the values are lists, then they are not combined yet (TODO).
     * If the values are any other type, then the value from the second map
     * overwrites the value from the first map.
     *
     * @param map1 The first map to be combined. Can be null or empty.
     * @param map2 The second map to be combined. Can be null or empty.
     * @return A new map that contains the combined entries of map1 and map2.
     *         If both maps are null or empty, returns an empty map.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> combineDict(Map<String, Object> map1, Map<String, Object> map2) {
        if (map1 == null && map2 == null)
            return new HashMap<>();

        if (map2 == null || map2.keySet().size() == 0)
            return map1;

        if (map1 == null || map1.keySet().size() == 0)
            return map2;

        Map<String, Object> map = new HashMap<>(map1);
        for (Map.Entry<String, Object> entry : map2.entrySet()) {
            if (map.containsKey(entry.getKey())) {
                Object o = map.get(entry.getKey());
                if (entry.getValue() instanceof Map && o instanceof Map) {
                    Map<String, Object> temp = combineDict((Map<String, Object>) o, (Map<String, Object>) entry.getValue());
                    map.put(entry.getKey(), temp);
                }
                else if (o instanceof List) {
//                     TODO: Support list combination
//                    List<Map<String, Object>> o1 = (List<Map<String, Object>>) o;
//                    for (int i = 0; i < o1.size(); i++) {
//                        o1.add(combineDict(o1.get(i), (Map<String, Object>) entry.getValue()));
//                        o1.remove(i + 1);
//                    }
                }
                else
                    map.put(entry.getKey(), entry.getValue());
            } else
                map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }
}
