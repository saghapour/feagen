package utils;

import ir.deltasink.feagen.common.utils.FormattedMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FormattedMessageTest {
    @Test
    public void testFormattedMessage(){
        String pattern = "It's {} time(s) to count {}";
        assertAll(
                () -> assertEquals("It's {0} time(s) to count {1}", new FormattedMessage(pattern).getFormattedMessage()),
                () -> assertEquals("It's 1 time(s) to count {1}", new FormattedMessage(pattern, 1).getFormattedMessage()),
                () -> assertEquals("It's 1 time(s) to count 2", new FormattedMessage(pattern, 1, 2).getFormattedMessage()),
                () -> assertEquals("Without placeholders", new FormattedMessage("Without placeholders").getFormattedMessage())
        );
    }
}
