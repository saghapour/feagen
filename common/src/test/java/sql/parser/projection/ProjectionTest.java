package sql.parser.projection;

import ir.deltasink.feagen.common.sql.parser.projection.Projection;
import ir.deltasink.feagen.common.sql.parser.projection.ProjectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectionTest {

    // Declare a Projection object as a field
    private Projection projection;

    // Initialize the Projection object before each test
    @BeforeEach
    void setUp() {
        projection = new Projection();
        projection.add("users", "id", "users.id", "user_id", ProjectionType.Column);
        projection.add("users", "name", "users.name", "user_name", ProjectionType.Column);
        projection.add("orders", "*", "orders.*", "", ProjectionType.AllTableColumns);
        projection.add("", "count(*)", "count(*)", "total_count", ProjectionType.Function);
    }

    // Test the containsColumn method with various column names
    @Test
    void testContainsColumn() {
        assertTrue(projection.containsColumn("users.id"));
        assertTrue(projection.containsColumn("user_id"));
        assertTrue(projection.containsColumn("orders.price"));
        assertTrue(projection.containsColumn("total_count"));
        assertFalse(projection.containsColumn("user_email"));
        assertFalse(projection.containsColumn("order_id"));
    }

    // Test the getColumnName method with various column names
    @Test
    void testGetColumnName() {
        assertEquals("user_id", projection.getColumnAlias("users.id"));
        assertEquals("user_id", projection.getColumnAlias("user_id"));
        assertEquals("price", projection.getColumnAlias("orders.price"));
        assertEquals("name", projection.getColumnAlias("products.name"));
        assertEquals("total_count", projection.getColumnAlias("count(*)"));
        assertEquals("email", projection.getColumnAlias("users.email"));
    }

    // Test the getFullyQualifiedColumnName method with various column names
    @Test
    void testGetFullyQualifiedColumnName() {
        assertEquals("users.id", projection.getFullyQualifiedColumnName("users.id"));
        assertEquals("users.id", projection.getFullyQualifiedColumnName("user_id"));
        assertEquals("orders.price", projection.getFullyQualifiedColumnName("orders.price"));
        assertEquals("products.name", projection.getFullyQualifiedColumnName("products.name"));
        assertEquals("count(*)", projection.getFullyQualifiedColumnName("count(*)"));
        assertEquals("count(*)", projection.getFullyQualifiedColumnName("total_count"));
        assertEquals("users.email", projection.getFullyQualifiedColumnName("users.email"));
    }
}