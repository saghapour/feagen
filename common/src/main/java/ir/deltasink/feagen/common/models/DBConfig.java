package ir.deltasink.feagen.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DBConfig implements Serializable {
    private String type;
    private String host;
    private int port;
    private String username;
    private String password;
    private String engine;
    private String database;
    private String schema;
}
