package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a projection of columns in a SQL query.
 */
public class Projection {

    /**
     * A static inner class that represents an item in the projection.
     */
    @Data
    @AllArgsConstructor
    public static class Item{
        /**
         * The name of the table that contains the column.
         */
        private String tableName;

        /**
         * The name of the column.
         */
        private String columnName;

        /**
         * The fully qualified name of the column, including the table name and a dot separator.
         */
        private String fullyQualifiedColumnName;

        /**
         * The alias of the column, if any.
         */
        private String alias;

        /**
         * The type of the projection item, such as AllColumns, AllTableColumns, Column, Function, or Unknown.
         */
        private ProjectionType type;

    }

    /**
     * A list of items in the projection.
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Adds a new item to the projection with the given parameters.
     * @param tableName The name of the table that contains the column.
     * @param columnName The name of the column.
     * @param fullColumnName The fully qualified name of the column, including the table name and a dot separator.
     * @param alias The alias of the column, if any.
     * @param type The type of the projection item, such as AllColumns, AllTableColumns, Column, Function, or Unknown.
     * @return The projection itself for method chaining.
     */
    public Projection add(String tableName, String columnName, String fullColumnName, String alias, ProjectionType type){
        items.add(new Item(tableName, columnName, fullColumnName, alias, type));
        return this;
    }

    /**
     * Checks if the projection contains a given column name.
     * @param column The column name to check. It can be either fully qualified or just the column name or alias.
     * @return True if the projection contains the column name, false otherwise.
     */
    public boolean containsColumn(String column){
        for (Item item : this.items) {
            if (isAllColumns(item) || isAllTableColumns(item, column) || isColumn(item, column) || isFunction(item, column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the projection item is of type Function and matches the alias of a given column name.
     * @param column The column name to match. It can be either fully qualified or just the column name or alias.
     * @return True if the projection item is of type Function and matches the alias of the column name, false otherwise.
     */
    public boolean isFunction(String column){
        for (Item item : this.items) {
            if (isFunction(item, column))
                return true;
        }

        return false;
    }

    /**
     * Gets the column name or alias for a given column name in the projection.
     * @param column The column name to look for. It can be either fully qualified or just the column name or alias.
     * @return The column name or alias if found in the projection. If not found or ambiguous, returns the original column name parameter.
     */
    public String getColumnAlias(String column){
        for (Item item : this.items) {
            if (isAllTableColumns(item, column)) {
                return getColumnPart(column);
            }
            if (isColumn(item, column)) {
                return getColumnAlias(item, column);
            }
            if (isAllColumns(item)) {
                return getColumnPart(column);
            }
            if (isFunction(item, column)) {
                return item.getAlias();
            }
        }
        return getColumnPart(column);
    }

    /**
     * Gets the fully qualified column name for a given column name in the projection.
     * @param column The column name to look for. It can be either fully qualified or just the column name or alias.
     * @return The fully qualified column name if found in the projection. If not found or ambiguous, returns the original column name parameter.
     */
    public String getFullyQualifiedColumnName(String column){
        for (Item item : this.items) {
            if (isAllTableColumns(item, column)) {
                return column;
            }
            if (isColumn(item, column)) {
                return item.getFullyQualifiedColumnName();
            }
            if (isAllColumns(item)) {
                return column;
            }
            if (isFunction(item, column)) {
                return item.getFullyQualifiedColumnName();
            }
        }
        return column;
    }

    /**
     * Checks if the projection item is of type AllColumns.
     * @param item The projection item to check.
     * @return True if the projection item is of type AllColumns, false otherwise.
     */
    private boolean isAllColumns(Item item) {
        return item.getType() == ProjectionType.AllColumns;
    }

    /**
     * Checks if the projection item is of type AllTableColumns and matches the table name of a given column name.
     * @param item The projection item to check.
     * @param column The column name to match. It can be either fully qualified or just the column name or alias.
     * @return True if the projection item is of type AllTableColumns and matches the table name of the column name, false otherwise.
     */
    private boolean isAllTableColumns(Item item, String column) {
        return item.getType() == ProjectionType.AllTableColumns && item.getTableName().equalsIgnoreCase(getTablePart(column));
    }

    /**
     * Checks if the projection item is of type Column and matches the fully qualified name or alias of a given column name.
     * @param item The projection item to check.
     * @param column The column name to match. It can be either fully qualified or just the column name or alias.
     * @return True if the projection item is of type Column and matches the fully qualified name or alias of the column name, false otherwise.
     */
    private boolean isColumn(Item item, String column) {
        return item.getType() == ProjectionType.Column && (item.getFullyQualifiedColumnName().equalsIgnoreCase(column) || item.getAlias().equalsIgnoreCase(column));
    }

    /**
     * Checks if the projection item is of type Function and matches the alias of a given column name.
     * @param item The projection item to check.
     * @param column The column name to match. It can be either fully qualified or just the column name or alias.
     * @return True if the projection item is of type Function and matches the alias of the column name, false otherwise.
     */
    private boolean isFunction(Item item, String column) {
        return item.getType() == ProjectionType.Function && (item.fullyQualifiedColumnName.equalsIgnoreCase(column) || item.getAlias().equalsIgnoreCase(column));
    }

    /**
     * Gets the alias of the projection item for a given column name, if it matches the table name and column name or the alias of the item.
     * @param item The projection item to get the alias from.
     * @param column The column name to match. It can be either fully qualified or just the column name or alias.
     * @return The alias of the projection item if it matches the column name, otherwise an empty string.
     */
    private String getColumnAlias(Item item, String column) {
        return item.getTableName().equalsIgnoreCase(getTablePart(column)) && item.getColumnName().equalsIgnoreCase(getColumnPart(column)) ? item.getAlias() :
                item.getAlias().equalsIgnoreCase(getColumnPart(column)) ? getColumnPart(column) : "";
    }

    /**
     * Gets the table name part of a given column name, if it is fully qualified.
     * @param column The column name to get the table name from. It can be either fully qualified or just the column name or alias.
     * @return The table name part of the column name, or an empty string if the column name is not fully qualified.
     */
    private String getTablePart(String column) {
        String[] columnParts = column.split("\\.");
        return columnParts.length == 2 ? columnParts[0] : "";
    }

    /**
     * Gets the column name part of a given column name, regardless of whether it is fully qualified or not.
     * @param column The column name to get the column name part from. It can be either fully qualified or just the column name or alias.
     * @return The column name part of the column name.
     */
    private String getColumnPart(String column) {
        String[] columnParts = column.split("\\.");
        return columnParts.length == 1 ? columnParts[0] : columnParts[1];
    }
}