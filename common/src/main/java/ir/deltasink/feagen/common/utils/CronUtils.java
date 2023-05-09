package ir.deltasink.feagen.common.utils;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

import static com.cronutils.model.CronType.UNIX;

/**
 * This class provides utility methods for working with cron expressions.
 */
@Slf4j
public class CronUtils {
    /**
     * Checks if a given execution date and time matches a cron expression and an always value.
     * The always value is a special string that indicates that the method should always return true.
     * @param executionDateTime the date and time to check
     * @param cronExpression the cron expression to match
     * @param alwaysValue the always value to compare
     * @return true if the execution date and time matches the cron expression or the always value, false otherwise
     */
    public static boolean isMatched(ZonedDateTime executionDateTime, String cronExpression, String alwaysValue){
        log.info("Cron expression is {}", cronExpression);
        if (cronExpression.equals(alwaysValue))
            return true;

        try {
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(UNIX);
            CronParser parser = new CronParser(cronDefinition);
            Cron cron = parser.parse(cronExpression);
            ExecutionTime et = ExecutionTime.forCron(cron);
            return et.isMatch(executionDateTime);
        }
        catch (Exception ex){
            log.error("Cron expression {} is not valid in UNIX expression. Return default as false.", cronExpression);
            return false;
        }
    }
}
