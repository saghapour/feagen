package ir.deltasink.feagen.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a string template that can replace placeholders
 * with values. The placeholders are enclosed in curly braces, such as {name}.
 * The template can also contain environment variables, such as ${ENV:default-value},
 * which are replaced by their values from the system environment.
 */
public class StringTemplate {
    private final String template;
    private String value;
    private static final String envPattern = "\\$\\{(.+?)\\}";
    private static final Pattern envRegex = Pattern.compile(envPattern);

    /**
     * Creates a new string template with the given template string.
     * @param template The template string that contains placeholders and environment variables.
     */
    public StringTemplate(String template){
        this.template = template;
        this.value = this.template;
    }

    /**
     * Replaces a placeholder with a value in the template string.
     * @param key The name of the placeholder without curly braces, such as name.
     * @param value The value to replace the placeholder with. Can be any object that has a toString() method.
     * @return The same string template object, for method chaining.
     */
    public <T> StringTemplate replace(String key, T value){
        if (key != null && value != null)
            this.value = this.value.replace(String.format("{%s}", key), value.toString());

        return this;
    }

    /**
     * Clears the template string and restores it to its original state.
     */
    public void clear(){
        this.value = this.template;
    }

    /**
     * Returns the current value of the template string after applying replacements.
     * @return The current value of the template string.
     */
    public String value(){
        return this.value;
    }

    /**
     * Extracts the environment variables from a given template string and returns a map of their names and values.
     * @param template The template string that contains environment variables, such as ${env:USER}.
     * @return A map of environment variable names and values. If the template is null or empty, returns an empty map.
     */
    public static Map<String, String> extractEnvs(String template){
        Map<String, String> envs = new HashMap<>();
        if (template == null || template.isEmpty())
            return envs;

        Matcher matcher = envRegex.matcher(template);
        while (matcher.find()) {
            String envVar = matcher.group(1).toUpperCase().split(":")[0];
            String envValue = Environment.getEnv(envVar);
            if (envValue != null)
                envs.put(envVar, envValue);
        }

        return envs;
    }
}

