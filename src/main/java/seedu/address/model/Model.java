package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Product;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Product> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getProductInfoBook();

    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    boolean hasPerson(Product product);

    /**
     * Deletes the given product.
     * The product must exist in the address book.
     */
    void deletePerson(Product target);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the address book.
     */
    void addPerson(Product product);

    /**
     * Replaces the given product {@code target} with {@code editedProduct}.
     * {@code target} must exist in the address book.
     * The product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    void updatePerson(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredPersonList();

    /**
     * Updates the filter of the filtered product list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Product> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}
