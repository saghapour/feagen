package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.NonNull;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * A class that represents a base column projection item in a select expression.
 */
public abstract class BaseColumnProjectionItem{
    /**
     * Adds a column to the projection with the given expression item, column and projection objects.
     * @param expressionItem the select expression item that contains the column and its alias
     * @param column the column object that contains the column name and table name
     * @param projection the projection object that stores the projected columns
     */
    protected void add(@NonNull SelectExpressionItem expressionItem, @NonNull Column column, @NonNull Projection projection){
        String columnName = column.getColumnName();
        String tableName = column.getTable() != null ? column.getTable().getName() : "";
        String fullyQualifiedColumnName = column.getFullyQualifiedName();
        String alias = expressionItem.getAlias() != null ? expressionItem.getAlias().getName() : columnName;
        projection.add(tableName, columnName, fullyQualifiedColumnName, alias, ProjectionType.Column);
    }
}
