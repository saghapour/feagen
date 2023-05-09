package ir.deltasink.feagen.common.utils;

import ir.deltasink.feagen.common.exception.IllegalValueException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * This class provides methods for parsing time configurations.
 * The time configurations can have a suffix indicating the unit of time, such as "d" for days, "y" for years, "M" for months, "h" for hours, "m" for minutes, or "s" for seconds.
 * If no suffix is provided, the default unit is hours.
 * The time configurations can also be mathematical expressions, such as "2*24" for two days.
 */
public class TimeConfig {
    /**
     * Parses a string representing a time configuration and returns the equivalent number of hours.
     * The string can be either a suffix-based configuration or a mathematical expression.
     * @param config the string to be parsed
     * @return the number of hours corresponding to the config
     * @throws IllegalValueException if the config is not valid
     */
    public static Integer parseToHour(String config) throws IllegalValueException{
        if (config == null || config.isEmpty())
            throw new IllegalValueException("Value is blank for TimeConfig.");

        Integer result = parseWithSuffix(config);
        if (result == null)
            result = parseWithMathExpression(config);

        if (result == null)
            throw new IllegalValueException("Value `{}` is invalid for TimeConfig.", config);

        return result;
    }

    /**
     * Parses a string representing a mathematical expression and returns the equivalent number of hours.
     * The string is evaluated by JavaScript engine and converted to an integer value.
     * @param config the string to be parsed
     * @return the number of hours corresponding to the config, or null if the config is not a valid expression
     */
    private static Integer parseWithMathExpression(String config){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            double value = Double.parseDouble(engine.eval(config).toString());
            if (value < 0 || value == Double.POSITIVE_INFINITY)
                return null;

            return (int) value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses a string representing a suffix-based configuration and returns the equivalent number of hours.
     * The string can have a suffix indicating the unit of time, such as "d" for days, "y" for years, "M" for months, "h" for hours, "m" for minutes, or "s" for seconds.
     * If no suffix is provided, the default unit is hours.
     * @param config the string to be parsed
     * @return the number of hours corresponding to the config, or null if the config is not valid
     */
    private static Integer parseWithSuffix(String config){
        String configPart = config.substring(config.length()-1);
        boolean hasConfigPart = true;
        Integer multiply;

        switch (configPart){
            case "d": multiply = 24; break;
            case "y": multiply = 365*24; break;
            case "M": multiply = 30*24; break;
            case "h": multiply = 1; break;
            case "m":
            case "s": multiply = 0; break;
            // default is hour
            default: multiply = 1; hasConfigPart = false; break;
        }

        String numberPart = hasConfigPart ?
                config.substring(0, config.indexOf(configPart)) :
                config;

        if (!NumberUtils.isParsable(numberPart))
            return null;

        return NumberUtils.createInteger(numberPart) * multiply;
    }
}
