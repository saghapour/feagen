package ir.deltasink.feagen.common.sql.parser.projection;

import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * A subclass of BaseColumnProjectionItem that represents a parenthesis projection item.
 * A parenthesis projection item is a select expression item that contains a column inside a parenthesis.
 */
public class ParenthesisProjectionItem extends BaseColumnProjectionItem implements ProjectionItem {
    // The select expression item that contains the parenthesis
    private final SelectExpressionItem expressionItem;

    /**
     * The constructor that takes the select expression item as an argument.
     * @param expressionItem the select expression item that contains the parenthesis
     */
    public ParenthesisProjectionItem(SelectExpressionItem expressionItem) {
        this.expressionItem = expressionItem;
    }

    /**
     * The method to add the parenthesis projection item to a projection object.
     * @param projection the projection object to add the item to
     */
    @Override
    public void addToProjection(Projection projection) {
        // Get the parenthesis from the expression item
        Parenthesis parenthesis = (Parenthesis) expressionItem.getExpression();
        Column column = (Column) parenthesis.getExpression();
        super.add(expressionItem, column, projection);
    }
}
