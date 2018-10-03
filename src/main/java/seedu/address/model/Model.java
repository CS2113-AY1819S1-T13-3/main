package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.distributor.Distributor;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Distributor> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /*
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.

    boolean hasPerson(Person person);
    */

    /**
     * Returns true if a distributor with the same identity as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributor(Distributor distributor);

    /*
    /**
     * Deletes the given person.
     * The person must exist in the address book.

    void deletePerson(Person target);
    */

    /**
     * Deletes the given distributor.
     * The distributor must exist in the address book.
     */
    void deleteDistributor(Distributor target);

    /*
    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.

    void addPerson(Person person);
    */

    /**
     * Adds the given distributor.
     * {@code distributor} must not already exist in the address book.
     */
    void addDistributor(Distributor distributor);

    /*
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.

    void updatePerson(Person target, Person editedPerson);
    */

    /**
     * Replaces the given distributor {@code target} with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor in the Inventarie.
     */
    void updateDistributor(Distributor target, Distributor editedDistributor);

    /*
    /** Returns an unmodifiable view of the filtered person list
    ObservableList<Person> getFilteredPersonList();
    */

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Distributor> getFilteredDistributorList();

    /*
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.

    void updateFilteredPersonList(Predicate<Person> predicate);
    */

    /**
     * Updates the filter of the filtered distributor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDistributorList(Predicate<Distributor> predicate);

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
