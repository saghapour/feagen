package ir.deltasink.feagen.common.sql.parser;

import lombok.Data;

@Data
public class ValidationResult {
    private boolean isValid = true;
    private String message;
}
