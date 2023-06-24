package sql.parser.projection;

import ir.deltasink.feagen.common.sql.parser.projection.ColumnProjectionItem;
import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import ir.deltasink.feagen.common.sql.parser.projection.ProjectionItem;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompositeProjectionItemTest {

    @Mock
    Projection projection;
    List<ProjectionItem> items = new ArrayList<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SelectExpressionItem expressionItem1 = mock(SelectExpressionItem.class);

        Column column1 = mock(Column.class);
        Alias alias1 = mock(Alias.class);
        when(alias1.getName()).thenReturn("pid");
        when(column1.getColumnName()).thenReturn("id");
        when(expressionItem1.getExpression()).thenReturn(column1);
        when(expressionItem1.getAlias()).thenReturn(alias1);
        try(MockedConstruction<ColumnProjectionItem> mockedConstruction = mockConstruction(ColumnProjectionItem.class,  withSettings().useConstructor(expressionItem1))){
            ColumnProjectionItem columnProjectionItem = new ColumnProjectionItem(expressionItem1);
            assertEquals(1, mockedConstruction.constructed().size());
            items.add(columnProjectionItem);
        }
//
//
//        SelectExpressionItem expressionItem2 = new SelectExpressionItem();
//        Column column2 = new Column();
//        column2.setColumnName("fname");
//        column2.setTable(new Table("c"));
//        expressionItem2.setExpression(column2);
//        ColumnProjectionItem item2 = new ColumnProjectionItem(expressionItem2);
//
//        items.add(item1);
//        items.add(item2);
//        CompositeProjectionItem composite = new CompositeProjectionItem(items);
//        composite.addToProjection(projection);
    }

    @Test
    void testAddToProjection() {
//        List<ProjectionItem> items = new ArrayList<>();
////        items.add(new ProjectionItemA());
////        items.add(new ProjectionItemB());
////        items.add(new ProjectionItemC());
//// Create a composite projection item with the list
//        CompositeProjectionItem composite = new CompositeProjectionItem(items);
//// Create a mock projection object
//        Projection projection = mock(Projection.class);
//// Call the addToProjection method of the composite projection item with the mock projection object
//        composite.addToProjection(projection);
//// Verify that the addToProjection method of each projection item in the list was called once with the same mock projection object
//        for (ProjectionItem item : items) {
//            verify(item, times(1)).addToProjection(projection);
//        }
    }
}
