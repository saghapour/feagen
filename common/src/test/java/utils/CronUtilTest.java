package utils;

import ir.deltasink.feagen.common.utils.CronUtils;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CronUtilTest {
    @Test
    public void testCronUtil(){
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 1, 1, 1, 0,0,0, ZoneId.of("UTC"));
        String alwaysValue = "always";
        assertAll(
                () -> assertTrue( CronUtils.isMatched(zonedDateTime, "always", alwaysValue)),
                () -> assertTrue(CronUtils.isMatched(zonedDateTime, "0 1 * * *", alwaysValue)),
                () -> assertFalse(CronUtils.isMatched(zonedDateTime, "*", alwaysValue)),
                () -> assertFalse(CronUtils.isMatched(zonedDateTime, "invalid", alwaysValue)),
                () -> assertFalse(CronUtils.isMatched(zonedDateTime, "5 1 * * *", alwaysValue))
        );
    }
}
