package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.UniqueDistributorList;

import seedu.address.model.product.Product;
import seedu.address.model.product.UniquePersonList;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;
import seedu.address.model.timeidentifiedclass.shopday.ShopDay;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final SalesHistory salesHistory;
    private Transaction lastTransaction;
    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        persons = new UniquePersonList();
        salesHistory = new SalesHistory();
        lastTransaction = null;
    }

    private final UniqueDistributorList distributors;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        distributors = new UniqueDistributorList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    /**
     * Replaces the contents of the product list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setPersons(List<Product> products) {
        this.persons.setPersons(products);
    }


    /**
     * Replaces the contents of the product list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDistributors(List<Distributor> distributors) {
        this.distributors.setDistributors(distributors);
    }


    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
    */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setDistributors(newData.getDistributorList());
    }

    //// product-level operations


    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    public boolean hasPerson(Product product) {
        requireNonNull(product);
        return persons.contains(product);
    }


    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.contains(distributor);
    }


    /**
     * Adds a product to the address book.
     * The product must not already exist in the address book.
     */
    public void addPerson(Product p) {
        persons.add(p);
    }


    /**
     * Adds a distributor to the address book.
     * The distributor must not already exist in the address book.
     */
    public void addDistributor(Distributor d) {
        distributors.add(d);
    }



    /**
     * Replaces the given product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the address book.
 product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    public void updatePerson(Product target, Product editedProduct) {
        requireNonNull(editedProduct);
        persons.setPerson(target, editedProduct);
    }


    /**
     * Replaces the given distrbutor {@code target} in the list with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor
     * in the Inventarie.
     */
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireNonNull(editedDistributor);

        distributors.setDistributor(target, editedDistributor);
    }


    /**
     * Adds a transaction to the active shopday.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws ClosedShopDayException
     * @throws DuplicateTransactionException
     */

    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            ClosedShopDayException, DuplicateTransactionException {
        try {
            salesHistory.addTransaction(transaction);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (ClosedShopDayException e) {
            throw e;
        } catch (DuplicateTransactionException e) {
            throw e;
        }
        lastTransaction = transaction;
    }

    public String getDaysHistory(String day) {
        ShopDay requiredDay;
        try {
            requiredDay = salesHistory.getDaysHistory(day);
        } catch (NoSuchElementException e) {
            return "Day does not exist\n";
        }
        return requiredDay.getDaysTransactions();
    }

    public String getActiveDayHistory() {
        ShopDay activeDay = salesHistory.getActiveDay();
        return activeDay.getDaysTransactions();
    }

    public Transaction getLastTransaction() {
        return lastTransaction;
    }

    /**
     * This method adds a reminder to the active shop day.
     * @param reminder
     * @throws InvalidTimeFormatException
     */
    public void addReminderToActiveShopDay(Reminder reminder) throws InvalidTimeFormatException,
            DuplicateReminderException {

        if (!Transaction.isValidTransactionTime(reminder.getTime())) {
            throw new InvalidTimeFormatException();
        }

        try {
            salesHistory.getActiveDay().addReminder(reminder);
        } catch (DuplicateReminderException e) {
            throw e;
        }

    }

    /**
     * Returns the reminders which are due in the active day.
     * @return reminder list.
     */
    public ArrayList<Reminder> getDueRemindersInActiveDay() {
        final TreeMap<String, Reminder> reminderRecord = salesHistory.getActiveDay().getReminderRecord();
        ArrayList<Reminder> reminders = new ArrayList<>();

        // To get time since transaction and reminder have the same time formats.
        final String currentTime = (new Transaction()).getTransactionTime();

        // To iterate through the TreeMap.
        Set set = reminderRecord.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String reminderTime = (String) entry.getKey();

            //checks if reminder time is lesser than or equal to the current time.
            if (reminderTime.compareTo(currentTime) <= 0) {
                reminders.add((Reminder) entry.getValue());
            } else {
                break;
            }
        }
        return reminders;
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Product key) {
        persons.remove(key);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDistributor(Distributor key) {
        distributors.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return distributors.asUnmodifiableObservableList().size() + " distributors";
        // TODO: refine later
    }

    @Override
    public ObservableList<Product> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Distributor> getDistributorList() {
        return distributors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && distributors.equals(((AddressBook) other).distributors));
    }

    @Override
    public int hashCode() {
        return distributors.hashCode();
    }
}
