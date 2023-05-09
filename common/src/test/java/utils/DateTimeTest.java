package utils;

import ir.deltasink.feagen.common.utils.DateTime;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;


import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {
    @Test
    public void parseTest(){
        String zone = "UTC";
        ZonedDateTime expected = ZonedDateTime.of(2023,1,1,0,0,0,0, ZoneId.of(zone));
        assertAll(
                () -> assertEquals(expected, DateTime.parse("2023-01-01", "UTC")),
                () -> assertEquals(expected, DateTime.parse("2023-01-01T00:00:00", "UTC")),
                () -> assertEquals(expected, DateTime.parse("2023-01-01T00:00:00.000", "UTC")),
                () -> assertEquals(expected, DateTime.parse("2023-01-01T00:00:00+00:00", "UTC")),
                () -> assertNotEquals(expected, DateTime.parse("2023-01-01T00:00:00.123", "UTC")),
                () -> assertNotEquals(expected, DateTime.parse("2023-01-01T00:00:00+03:00", "UTC")),
                () -> assertEquals(expected, DateTime.parse(" 2023-01-01T00:00:00 ", "UTC")),
                () -> assertThrows(java.time.format.DateTimeParseException.class, () -> DateTime.parse("2023-01-01 00:00:00", "UTC"))
        );
    }
}
