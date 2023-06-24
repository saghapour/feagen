package ir.deltasink.feagen.common.sql.parser.projection;

import lombok.NonNull;

import java.util.List;

/**
 * A projection item that represents a composition of other projection items.
 * This class allows to combine multiple projection items into a single one,
 * and add them to a projection object in a single call.
 * Example: select distinct(id,name) from table
 */
public class CompositeProjectionItem implements ProjectionItem {
    // The list of projection items that compose this item
    private final List<ProjectionItem> items;

    /**
     * The constructor that takes the list of projection items.
     * @param items the list of projection items to compose this item
     * @throws NullPointerException if items is null
     */
    public CompositeProjectionItem(@NonNull List<ProjectionItem> items) {
        this.items = items;
    }

    /**
     * The method to add the composite projection item to a projection object.
     * This method calls the addToProjection method of each projection item in the list,
     * and adds them to the given projection object.
     * @param projection the projection object to add the composite projection item to
     * @throws NullPointerException if projection is null
     */
    @Override
    public void addToProjection(@NonNull Projection projection) {
        // For each projection item in the list, call its addToProjection method
        for (ProjectionItem item : items) {
            item.addToProjection(projection);
        }
    }
}

