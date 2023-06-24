package utils;

import ir.deltasink.feagen.common.exception.IllegalValueException;
import ir.deltasink.feagen.common.utils.TimeConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeConfigTest {
    @Test
    public void testParseToHourWithSuffix() {
        assertEquals(24, TimeConfig.parseToHour("1d"));
        assertEquals(8760, TimeConfig.parseToHour("1y"));
        assertEquals(720, TimeConfig.parseToHour("1M"));
        assertEquals(1, TimeConfig.parseToHour("1h"));
        assertEquals(0, TimeConfig.parseToHour("1m"));
        assertEquals(0, TimeConfig.parseToHour("1s"));
        assertEquals(2, TimeConfig.parseToHour("2"));
    }

    @Test
    public void testParseToHourWithMath() {
        assertEquals(51, TimeConfig.parseToHour("2*24+3"));
        assertEquals(32, TimeConfig.parseToHour("(365+30)/12"));
        assertEquals(0, TimeConfig.parseToHour("1/24"));
        assertEquals(3, TimeConfig.parseToHour("Math.pow(3,1)"));
        assertEquals(4, TimeConfig.parseToHour("2+2"));
    }

    @Test
    public void testParseToHourWithInvalid() {
        assertThrows(IllegalValueException.class, () -> TimeConfig.parseToHour("abc"));
        assertThrows(IllegalValueException.class, () -> TimeConfig.parseToHour("1x"));
        assertThrows(IllegalValueException.class, () -> TimeConfig.parseToHour("2/0"));
        assertThrows(IllegalValueException.class, () -> TimeConfig.parseToHour(""));
        assertThrows(IllegalValueException.class, () -> TimeConfig.parseToHour(null));
    }
}
