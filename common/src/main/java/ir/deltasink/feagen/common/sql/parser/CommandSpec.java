package ir.deltasink.feagen.common.sql.parser;

import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import lombok.Data;

@Data
public class CommandSpec {
    private QueryType queryType;
    private boolean isCTECommand;
    private boolean isSelectInto;
    private boolean hasSubQuery;
    private boolean hasJoin;
    private boolean isGroupBy;
    private boolean isDistinct;
    private Projection projection;
    private String projectionString;
    private String tableName;
    private String tableAlias;
    private String schemaName;
    private String databaseName;
    private String fullTableName;
}
