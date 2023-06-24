package sql.parser.projection;

import ir.deltasink.feagen.common.sql.parser.projection.ColumnProjectionItem;
import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnProjectionItemTest {

    private final Projection projection = new Projection();
    @BeforeEach
    public void setUp() {
        // Create a select expression item with a column inside a parenthesis
        SelectExpressionItem expressionItem1 = new SelectExpressionItem();
        Column column1 = new Column();
        column1.setColumnName("id");
        column1.setTable(new Table("c"));
        expressionItem1.setExpression(column1);
        expressionItem1.setAlias(new Alias("pid"));
        ColumnProjectionItem item1 = new ColumnProjectionItem(expressionItem1);
        item1.addToProjection(projection);

        SelectExpressionItem expressionItem2 = new SelectExpressionItem();
        Column column2 = new Column();
        column2.setColumnName("fname");
        column2.setTable(new Table("c"));
        expressionItem2.setExpression(column2);
        ColumnProjectionItem item2 = new ColumnProjectionItem(expressionItem2);
        item2.addToProjection(projection);

    }

    @Test
    public void testAddToProjection() {
        assertTrue(projection.containsColumn("pid"));
        assertFalse(projection.containsColumn("id"));
        assertTrue(projection.containsColumn("c.id"));
        assertFalse(projection.containsColumn("c.name"));
        assertEquals("name", projection.getColumnAlias("x.name"));
        assertEquals("fname", projection.getColumnAlias("x.fname"));
        assertEquals("pid", projection.getColumnAlias("c.id"));
        assertEquals("c.id", projection.getFullyQualifiedColumnName("pid"));
        assertEquals("c.fname", projection.getFullyQualifiedColumnName("c.fname"));
        assertEquals("c.fname", projection.getFullyQualifiedColumnName("fname"));
        assertEquals("name", projection.getFullyQualifiedColumnName("name"));
        assertEquals("c.name", projection.getFullyQualifiedColumnName("c.name"));

    }
}
