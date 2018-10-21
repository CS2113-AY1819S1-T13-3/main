package seedu.address.model;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUsersList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */

    Predicate<Distributor> PREDICATE_SHOW_ALL_DISTRIBUTORS = unused -> true;

    Predicate<Product> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getProductInfoBook();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getDistributorInfoBook();

    /**
     * Returns true if a distributor with the same identity as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributor(Distributor distributor);

    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    boolean hasPerson(Product product);

    /**
     * Deletes the given distributor.
     * The distributor must exist in the address book.
     */
    void deleteDistributor(Distributor target);

    /**
     * Deletes the given product.
     * The product must exist in the address book.
     */
    void deletePerson(Product target);

    /**
     * Adds the given distributor.
     * {@code distributor} must not already exist in the address book.
     */
    void addDistributor(Distributor distributor);

    //void getDistributor(Distributor distributor);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the address book.
     */
    void addPerson(Product product);

    /**
     * Replaces the given distributor {@code target} with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor
     * in the Inventarie.
     */
    void updateDistributor(Distributor target, Distributor editedDistributor);


    /** Returns an unmodifiable view of the filtered product list
     * The product identity of {@code editedProduct} must not be the same as another existing
     * product in the address book.
     */
    void updatePerson(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Distributor> getFilteredDistributorList();

    /**
     * Updates the filter of the filtered distributor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDistributorList(Predicate<Distributor> predicate);

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

    /**
     * Adds a transaction to the active shop day.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws DuplicateTransactionException
     * @throws ClosedShopDayException
     */
    void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException, ClosedShopDayException;

    /**
     * Adds a reminder to the active shop day.
     * @param reminder
     * @throws InvalidTimeFormatException
     * @throws DuplicateReminderException
     */

    void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException;

    /**
     * Returns the reminders due on the current active day.
     * @return
     */

    ArrayList<Reminder> getDueRemindersInActiveShopDay();

    /**
     * Returns a given day's transaction history
     */

    String getDaysHistory(String day);

    /**
     * Returns the active day's transaction history
     */
    String getActiveDayHistory();

    /**
     * Returns the latest transaction.
     */
    Transaction getLastTransaction();

    /**
    * Sets the user list
    */
    void setUsersList(UniqueUsersList uniqueUserList);

    /** Returns the UserDatabase */
    ReadOnlyAddressBook getUserDatabase();

    /**
     * Deletes the given user.
     * The user must exist in the user database.
     */
    void deleteUser(User target) throws UserNotFoundException;

    /**
     * Adds the given user.
     * {@code user} must not already exist in the user database.
     */
    void addUser(User person) throws DuplicateUserException;

    boolean checkLoginCredentials(Username username, Password password) throws AuthenticatedException;

    boolean checkCredentials(Username username, Password password) throws AuthenticatedException;

    boolean hasLoggedIn();

    void setLoginStatus(boolean status);

    User getLoggedInUser();

    void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException;

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

}
