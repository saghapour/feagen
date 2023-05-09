package ir.deltasink.feagen.common.utils;

import lombok.val;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * This class provides utility methods for loading resources from the classpath.
 */
public class ResourceUtil {

    /**
     * Loads a resource as an input stream from the classpath.
     * @param path The path of the resource relative to the classpath root, such as "configs/pipeline.yml".
     * @return An input stream for reading the resource, or null if the resource is not found.
     */
    public static InputStream loadResourceStream(String path) {
        return ResourceUtil.class
                .getClassLoader()
                .getResourceAsStream(path);
    }

    /**
     * Loads a resource as a string from the classpath.
     * @param path The path of the resource relative to the classpath root, such as "configs.yml".
     * @return A string containing the content of the resource, or null if the resource is not found.
     */
    public static String loadResourceContent(String path) {
        val inputStream = ResourceUtil.loadResourceStream(path);
        if (inputStream == null)
            return null;

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
    }
}
