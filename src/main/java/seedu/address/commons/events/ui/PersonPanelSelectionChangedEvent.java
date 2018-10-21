package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.product.Product;

/**
 * Represents a selection change in the Product List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Product newSelection;

    public PersonPanelSelectionChangedEvent(Product newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Product getNewSelection() {
        return newSelection;
    }
}
