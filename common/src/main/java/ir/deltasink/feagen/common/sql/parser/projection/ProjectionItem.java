package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.NonNull;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface for projection items that can be added to a projection object.
 */
public interface ProjectionItem {

    /**
     * A factory method to create a projection item from a select expression item.
     * @param expressionItem the select expression item to convert
     * @return a projection item that corresponds to the expression type
     * @throws IllegalArgumentException if the expression type is not supported
     */
    static ProjectionItem from(SelectExpressionItem expressionItem) {
        Expression expression = expressionItem.getExpression();
        if (expression instanceof Function) {
            return new FunctionProjectionItem(expressionItem);
        } else if (expression instanceof Column) {
            return new ColumnProjectionItem(expressionItem);
        } else if (expression instanceof Parenthesis) {
            return new ParenthesisProjectionItem(expressionItem);
        } else if (expression instanceof RowConstructor) {
            List<ProjectionItem> items = new ArrayList<>();
            RowConstructor rowConstructor = (RowConstructor) expression;
            for (Expression expr : rowConstructor.getExprList().getExpressions()) {
                items.add(from(new SelectExpressionItem(expr)));
            }
            return new CompositeProjectionItem(items);
        } else {
            throw new IllegalArgumentException("Unsupported expression type: " + expression.getClass());
        }
    }

    /**
     * A method to add the projection item to a projection object.
     * @param projection the projection object to add the item to
     */
    void addToProjection(@NonNull Projection projection);
}
