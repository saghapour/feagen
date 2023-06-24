package ir.deltasink.feagen.common.db;

import ir.deltasink.feagen.common.config.IConfig;
import ir.deltasink.feagen.common.models.DBConfig;
import ir.deltasink.feagen.common.models.DatabaseClient;
import ir.deltasink.feagen.common.utils.StringTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBClientUtil {
    public static DBConfig getDBConfig(IConfig dbConfig){
        return new DBConfig(
                dbConfig.getAs("type"),
                dbConfig.getAs("host"),
                dbConfig.getAs("port"),
                dbConfig.getAs("username"),
                dbConfig.getAs("password").toString(),
                dbConfig.getAs("engine"),
                dbConfig.getAs("database", null),
                dbConfig.getAs("schema", null)
        );
    }

    public static DatabaseClient setJdbcUrl(DatabaseClient dbClient, DBConfig dbConfig){
        StringTemplate stringTemplate = new StringTemplate(dbClient.getJdbcUrl());
        stringTemplate.replace("host", dbConfig.getHost())
                .replace("port", dbConfig.getPort())
                .replace("database", dbConfig.getDatabase())
                .replace("schema", dbConfig.getSchema());
        dbClient.setJdbcUrl(stringTemplate.value());
        return dbClient;
    }
}
