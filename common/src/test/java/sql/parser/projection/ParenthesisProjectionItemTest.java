package sql.parser.projection;

import ir.deltasink.feagen.common.sql.parser.projection.ParenthesisProjectionItem;
import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ParenthesisProjectionItemTest {

    private ParenthesisProjectionItem item;

    @BeforeEach
    public void setUp() {
        // Create a select expression item with a column inside a parenthesis
        SelectExpressionItem expressionItem = new SelectExpressionItem();
        Column column = new Column();
        column.setColumnName("id");
        Parenthesis parenthesis = new Parenthesis();
        parenthesis.setExpression(column);
        expressionItem.setExpression(parenthesis);

        item = new ParenthesisProjectionItem(expressionItem);
    }

    @Test
    public void testAddToProjection() {
        Projection projection = new Projection();

        item.addToProjection(projection);

        assertTrue(projection.containsColumn("id")); // Check that the projection has one item
        assertEquals("id", projection.getColumnAlias("id")); // Check that the item has the correct column name
    }
}
