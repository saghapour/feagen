package ir.deltasink.feagen.common.sql.parser;

import ir.deltasink.feagen.common.constant.Variables;
import ir.deltasink.feagen.common.exception.InvalidSqlStatementException;
import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import ir.deltasink.feagen.common.sql.parser.projection.ProjectionItem;
import ir.deltasink.feagen.common.sql.parser.projection.ProjectionType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.validation.Validation;
import net.sf.jsqlparser.util.validation.ValidationError;
import net.sf.jsqlparser.util.validation.feature.FeaturesAllowed;

import java.io.StringReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * A class that parses SQL queries and extracts information such as table name, condition, columns, etc.
 */
@Slf4j
public class QueryParser {
    private static final FeaturesAllowed SELECT_FEATURE = FeaturesAllowed.SELECT;
    private static final FeaturesAllowed INSERT_FEATURE = FeaturesAllowed.INSERT;
    private final String query;
    private final ConditionParser conditionParser;
    private final CCJSqlParserManager parserManager;
    private final CommandSpec commandSpec = new CommandSpec();
    private String condition;
    private String subQuery;

    /**
     * Constructs a new QueryParser with the given query.
     *
     * @param query the SQL query to parse
     * @throws InvalidSqlStatementException if the query is null
     */
    public QueryParser(String query) {
        if (query == null)
            throw new InvalidSqlStatementException("Sql query is null");

        this.query = query;
        conditionParser = new ConditionParser();
        parserManager = new CCJSqlParserManager();
    }

    /**
     * Validates the query as a SELECT command.
     *
     * @return true if the query is a valid SELECT command, false otherwise
     */
    public ValidationResult validateSelectCommand() {
        // this validation includes not allowing select into too.
        return this.validateCommand(SELECT_FEATURE);
    }

    /**
     * Validates the query as an INSERT command.
     *
     * @return true if the query is a valid INSERT command, false otherwise
     */
    public ValidationResult validateInsertCommand() {
        return this.validateCommand(INSERT_FEATURE);
    }

    /**
     * Validates the query with the given feature.
     *
     * @param feature the feature to allow in the validation
     * @return true if the query is valid with the given feature, false otherwise
     */
    private ValidationResult validateCommand(FeaturesAllowed feature) {
        ValidationResult result = new ValidationResult();
        Validation validation = new Validation(Collections.singletonList(feature), query);
        List<ValidationError> errors = validation.validate();
        for (ValidationError error :
                errors) {
            result.setValid(false);
            result.setMessage(error.toString());
            log.error("Query is not a valid {} command. error: {}, query: {}", feature, error.toString(), this.query);
        }

        return result;
    }

    /**
     * Parses the query and extracts information such as table name, condition, columns, etc.
     *
     * @return QueryParser instance to use in fluent manner
     * @throws InvalidSqlStatementException if the query is not valid or not supported
     */
    public QueryParser parse() {
        Statement statement = null;
        try {
            statement = parserManager.parse(new StringReader(query));
        } catch (JSQLParserException e) {
            e.printStackTrace();
            throw new InvalidSqlStatementException("Sql query is not valid. error: {}, sql: {}", e.getMessage(), query);
        }

        commandSpec.setQueryType(QueryType.valueOf(statement.getClass().getSimpleName().toUpperCase(Locale.ROOT)));
        Map<QueryType, Consumer<Statement>> handlers = Map.of(
                QueryType.SELECT, s -> handleSelectStatement((Select) s),
                QueryType.INSERT, s -> handleInsertStatement((Insert) s)
        );
        Consumer<Statement> handler = handlers.getOrDefault(this.commandSpec.getQueryType(), s -> {
            throw new InvalidSqlStatementException("Query type is not supported. sql: {}", query);
        });
        handler.accept(statement);

        return this;
    }

    /**
     * Handles a SELECT statement and extracts information from it.
     *
     * @param select the SELECT statement to handle
     */
    private void handleSelectStatement(Select select) {
        commandSpec.setCTECommand(select.isUsingWithBrackets());
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        commandSpec.setSelectInto(plainSelect.getIntoTables() != null && !plainSelect.getIntoTables().isEmpty());
        commandSpec.setGroupBy(plainSelect.getGroupBy() != null);
        commandSpec.setDistinct(plainSelect.getDistinct() != null);
        Expression whereExpression = plainSelect.getWhere();
        Optional.ofNullable(plainSelect.getFromItem())
                .map(item -> item instanceof Table ? (Table) item : null)
                .ifPresent(this::setTable);

        Optional.ofNullable(plainSelect.getFromItem())
                .map(item -> item instanceof SubSelect ? (SubSelect) item : null)
                .ifPresent(subSelect -> {
                    commandSpec.setHasSubQuery(true);
                    subQuery = subSelect.getSelectBody().toString();
                    commandSpec.setTableAlias(subSelect.getAlias().getName());
                });

        String lastProjection = plainSelect
                .getSelectItems()
                .stream()
                .reduce((first, second) -> second)
                .map(Object::toString)
                .map(s -> s.replace(" ", ""))
                .map(s -> s.toLowerCase(Locale.ROOT))
                .orElseThrow();
        String trimmedQuery = this.query.trim().toLowerCase(Locale.ROOT);
        int firstIndex = trimmedQuery.indexOf("select") + 6;
        int lastIndex = IntStream.range(trimmedQuery.indexOf(lastProjection) + lastProjection.length(), trimmedQuery.length() - 4)
                .filter(i -> trimmedQuery.startsWith("from", i))
                .findFirst()
                .orElse(trimmedQuery.indexOf(lastProjection));

        commandSpec.setProjectionString(trimmedQuery.substring(firstIndex, lastIndex));

        Projection projection = new Projection();
        ProjectionType projectionType;
        for (SelectItem item :
                plainSelect.getSelectItems()) {
            if (item instanceof SelectExpressionItem) {
                SelectExpressionItem expressionItem = (SelectExpressionItem) item;
                processSelectProjection(projection, expressionItem);
            } else if (item instanceof AllTableColumns) {
                AllTableColumns expressionItem = (AllTableColumns) item;
                projectionType = ProjectionType.AllTableColumns;
                projection.add(expressionItem.getTable().getName(),
                        Variables.SQL_ASTERISK,
                        expressionItem.toString(),
                        Variables.SQL_ASTERISK,
                        projectionType);
            } else if (item instanceof AllColumns) {
                projectionType = ProjectionType.AllColumns;
                projection.add("", Variables.SQL_ASTERISK, Variables.SQL_ASTERISK, Variables.SQL_ASTERISK, projectionType);
            }
        }
        commandSpec.setProjection(projection);
        if (whereExpression != null) {
            this.condition = whereExpression.toString();
            whereExpression.accept(conditionParser);
        }

        commandSpec.setHasJoin(plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0);
    }

    private void processSelectProjection(Projection projection, SelectExpressionItem expressionItem){
        // Create a ProjectionItem object from the expression item
        ProjectionItem projectionItem = ProjectionItem.from(expressionItem);
        // Call the addToProjection method on the projection item
        projectionItem.addToProjection(projection);
    }

    /**
     * Handles an INSERT statement and extracts information from it.
     *
     * @param insert the INSERT statement to handle
     */
    private void handleInsertStatement(Insert insert) {
        setTable(insert.getTable());
    }

    /**
     * Sets the table information from the given table.
     *
     * @param table the table to set the information from
     */
    private void setTable(Table table) {
        this.commandSpec.setTableName(table.getName());
        this.commandSpec.setFullTableName(table.getFullyQualifiedName());
        this.commandSpec.setSchemaName(table.getSchemaName());
        val db = table.getDatabase();
        if (db != null) {
            this.commandSpec.setDatabaseName(db.getDatabaseName());
        }

        this.commandSpec.setTableAlias(table.getAlias() != null ? table.getAlias().getName() : null);
    }

    /**
     * Returns the set of columns in the condition of the query.
     *
     * @return the set of columns in the condition
     */
    public Set<String> getConditionColumns() {
        return conditionParser.getColumns();
    }

    /**
     * Returns the condition of the query.
     *
     * @return the condition of the query
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns the subquery of the query, or null if there is no subquery.
     *
     * @return the subquery of the query, or null if there is no subquery
     */
    public String getSubQuery() {
        return subQuery;
    }

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

    public String getQuery() {
        return query;
    }
}