package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.NonNull;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

public class FunctionProjectionItem implements ProjectionItem {
    // The select expression item that contains the function
    private final SelectExpressionItem expressionItem;

    // The constructor that takes the select expression item
    public FunctionProjectionItem(SelectExpressionItem expressionItem) {
        this.expressionItem = expressionItem;
    }

    // The method to add the function projection item to a projection object
    @Override
    public void addToProjection(Projection projection) {
// Get the function from the expression item
        Function function = (Function) expressionItem.getExpression();
// Get the column name, table name, fully qualified column name, and alias from the function
        String columnName = function.toString();
        String tableName = "";
        String alias = expressionItem.getAlias() != null ? expressionItem.getAlias().getName() : columnName;
// Add the function projection item to the projection object with the function type
        projection.add(tableName, columnName, columnName, alias, ProjectionType.Function);
    }
}
