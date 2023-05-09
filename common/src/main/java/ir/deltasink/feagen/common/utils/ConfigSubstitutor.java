package ir.deltasink.feagen.common.utils;

import lombok.val;
import lombok.NonNull;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;


/**
 * This class replace all config placeholders, arguments and environment variables.
 * If there exists any variable with default value, it will replace its default value if that value is not determined
 * in inputs.
 * Default value separator is `:` and Escape character is `\`.
 */
public class ConfigSubstitutor {

    /**
     * Replaces only environment variables in string template. It detects environment variables in input
     * with format ${env-name:default-value} and replace them with system environment variables.
     *
     * @param template the string template to be replaced
     * @return the replaced string
     */
    public static String replace(String template){
        return replace(template, new HashMap<>());
    }

    /**
     * Replaces input params and environment variables in string template.
     * @param template the string template to be replaced
     * @param params parameters to replace
     * @return the replaced string
     */
    public static String replace(String template, final Map<String, Object> params){

        val parameters = getParam(template, params != null ? params : new HashMap<>());
        return substitute(template, parameters);
    }

    private static Map<String, Object> getParam(String template, @NonNull Map<String, Object> params){
        Map<String, Object> parameters = new HashMap<>(params);
        parameters.putAll(StringTemplate.extractEnvs(template));
        return parameters;
    }

    private static String substitute(String template, Map<String, Object> params){
        if (template == null)
            return null;
        // NOTE: order of operations is matter. Placeholders can not be replaced first, because it will replace arguments and env vars too.
        // `${` prefix is for arguments and env vars
        StringSubstitutor substitutor = new StringSubstitutor(params, "${", "}", '\\', ":");
        String result = substitutor.replace(template);

        // `{` prefix is for placeholders
        substitutor.setVariablePrefix("{");
        result = substitutor.replace(result);
        return result;
    }
}
