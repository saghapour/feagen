package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * A class that represents a column projection item in a select expression.
 */
@Slf4j
public class ColumnProjectionItem extends BaseColumnProjectionItem implements ProjectionItem {
    private final SelectExpressionItem expressionItem;

    /**
     * Constructs a new column projection item with the given select expression item.
     * @param expressionItem the select expression item that contains the column and its alias
     */
    public ColumnProjectionItem(@NonNull SelectExpressionItem expressionItem) {
        this.expressionItem = expressionItem;

    }

    /**
     * Adds the column to the projection.
     * @param projection the projection object that stores the projected columns
     */
    @Override
    public void addToProjection(@NonNull Projection projection) {
        Column column = (Column) expressionItem.getExpression();
        super.add(expressionItem, column, projection);
    }
}
