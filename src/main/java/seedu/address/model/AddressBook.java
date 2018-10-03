package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Product;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
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
     * Adds a product to the address book.
     * The product must not already exist in the address book.
     */
    public void addPerson(Product p) {
        persons.add(p);
    }

    /**
     * Replaces the given product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the address book.
     * The product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    public void updatePerson(Product target, Product editedProduct) {
        requireNonNull(editedProduct);

        persons.setPerson(target, editedProduct);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Product key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " products";
        // TODO: refine later
    }

    @Override
    public ObservableList<Product> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
