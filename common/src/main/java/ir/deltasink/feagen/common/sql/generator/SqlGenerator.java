package ir.deltasink.feagen.common.sql.generator;


import ir.deltasink.feagen.common.config.IConfig;
import ir.deltasink.feagen.common.constant.Variables;
import ir.deltasink.feagen.common.exception.IllegalValueException;
import ir.deltasink.feagen.common.exception.InvalidConfigurationException;
import ir.deltasink.feagen.common.exception.InvalidSqlStatementException;
import ir.deltasink.feagen.common.exception.MandatoryKeyException;
import ir.deltasink.feagen.common.models.DBConfig;
import ir.deltasink.feagen.common.sql.parser.QueryParser;
import ir.deltasink.feagen.common.sql.parser.CommandSpec;
import ir.deltasink.feagen.common.sql.parser.ValidationResult;

import java.util.Collections;
import java.util.List;

public class SqlGenerator {
    private final String sql;
    private final String tableName;
    private final List<String> columns;
    private final String condition;
    private final Integer limit;
    private final Boolean distinct;
    private final String query;
    private CommandSpec selectCommandSpec;

    public static Builder builder(IConfig config, DBConfig dbConfig){
        return new Builder(config, dbConfig);
    }

    // Builder is used to decouple business logics such as is in backfill mode or not.
    public static class Builder {
        private final IConfig config;
        private final DBConfig dbConfig;
        private String tableName;
        private String sql;
        private List<String> columns;
        private String condition;
        private Integer limit;
        private Boolean distinct;

        public Builder(IConfig config, DBConfig dbConfig) {
            this.config = config;
            this.dbConfig = dbConfig;
        }

        public Builder withSql(String sql){
            this.sql = sql;
            return this;
        }

        public Builder withTableName(String tableName){
            this.tableName = tableName;
            return this;
        }

        public Builder withColumns(List<String> columns){
            this.columns = columns;
            return this;
        }

        public Builder withCondition(String condition){
            this.condition = condition;
            return this;
        }

        public Builder withDistinct(Boolean distinct){
            this.distinct = distinct;
            return this;
        }

        public Builder withLimit(Integer limit){
            this.limit = limit;
            return this;
        }

        // Validate the input config and set the default values if not specified
        public SqlGenerator build() throws MandatoryKeyException {

            if (tableName == null)
                tableName = config.getAs(Variables.TABLE_NAME, "");

            if (sql == null)
                sql = config.getAs(Variables.SOURCE_SQL, "");

            if (tableName.isBlank() && sql.isBlank())
                throw new MandatoryKeyException("{} or {} is required in config for SqlGeneration. config: {}", Variables.SOURCE_SQL, Variables.TABLE_NAME, config.getDict());

            // Already checked.
            // If sql is blank, so tableName must be filled. otherwise, it's already raised an exception
            if (sql.isBlank())
                tableName = getFullTableName();

            if (columns == null)
                columns = config.getAs(Variables.SQL_COLUMNS, Collections.singletonList(Variables.SQL_ASTERISK));

            if (condition == null)
                condition = config.getAs(Variables.SQL_WHERE, null);

            if (limit == null)
                limit = config.getAs(Variables.SQL_LIMIT, null);

            if (distinct == null)
                distinct = config.getAs(Variables.SQL_DISTINCT, false);

            return new SqlGenerator(this);
        }

        private String getFullTableName(){
            StringBuilder sb = new StringBuilder();
            String dbName = config.getAs(Variables.DB_NAME);

            String schemaName = dbConfig != null ? dbConfig.getSchema() : null;
            if (schemaName != null)
                sb.append(schemaName).append(".").append(tableName);

            if (dbName != null)
                sb.insert(0, ".").insert(0, dbName);

            return sb.toString();
        }
    }

    private SqlGenerator(Builder builder) {
        this.tableName = builder.tableName;
        this.columns = builder.columns;
        this.condition = builder.condition;
        this.limit = builder.limit;
        this.distinct = builder.distinct;
        this.sql = builder.sql;
        this.query = createQuery();
    }

    private String createQuery(){
        String finalQuery;
        if (!sql.isBlank())
            finalQuery = sql;
        else {
            // Generating query is placed here to avoid using `into` command in columns.
            // For example, columns may be set as follow to inject `into` command:
            // columns: ["* into another_table"]
            StringBuilder sb = new StringBuilder(String.format("select %s %s from %s",
                    distinct != null && distinct ? "distinct" : "",
                    String.join(",", columns),
                    tableName));
            if (condition != null && !condition.isBlank())
                sb.append(String.format(" where %s", condition));
            // TODO: It may not exist limit command in some engines such as sql server
            if (limit != null)
                sb.append(String.format(" limit %s", limit));

            finalQuery = sb.toString();
        }

        QueryParser parser = createParser(finalQuery);
        this.selectCommandSpec = parser.getCommandSpec();
        return parser.getQuery();
    }

    public String generateSqlQuery() {
        return this.query;
    }

    public String generateBoundaryQuery(String partitionColumn) {
        if (partitionColumn == null || partitionColumn.isBlank())
            throw new IllegalValueException("Partition column must be specified to generate boundary query.");

        if (partitionColumn.equals(Variables.SQL_ASTERISK))
            throw new InvalidConfigurationException("Partition Column could not be asterisk character.");

        if (this.selectCommandSpec.isCTECommand())
            throw new InvalidSqlStatementException("Boundary query does not support CTE commands");

        if (this.selectCommandSpec.isGroupBy())
            throw new InvalidSqlStatementException("Boundary query does not support Group By");

        if (this.selectCommandSpec.getProjection().isFunction(partitionColumn))
            throw new InvalidConfigurationException("Partition column could not be function. Partition column: {}", partitionColumn);

        if (!this.selectCommandSpec.getProjection().containsColumn(partitionColumn))
            throw new InvalidConfigurationException("Partition column {} is not present in query columns. Partition column must be fully qualified or alias of column.", partitionColumn);

        String columnName = this.selectCommandSpec.getProjection().getFullyQualifiedColumnName(partitionColumn);
        return this.query.replace(this.selectCommandSpec.getProjectionString(), String.format(" min(%s) as min_value, max(%s) as max_value ", columnName, columnName));
    }

    public String getAlias(String columnName){
        return this.selectCommandSpec.getProjection().getColumnAlias(columnName);
    }

    private QueryParser createParser(String sql){
        QueryParser parser = new QueryParser(sql);
        ValidationResult validationResult = parser.validateSelectCommand();
        if (!parser.validateSelectCommand().isValid())
            throw new InvalidSqlStatementException("{}. Sql: {}", validationResult.getMessage(), sql);

        return parser.parse();
    }
}
