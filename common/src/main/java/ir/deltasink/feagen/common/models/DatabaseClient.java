package ir.deltasink.feagen.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DatabaseClient implements Serializable {
    private String jdbcUrl;
    private String driver;
    private boolean supportBulkInsert;
}
