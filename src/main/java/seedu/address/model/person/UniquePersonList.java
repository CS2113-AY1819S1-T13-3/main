package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A product is considered unique by comparing using {@code Product#isSamePerson(Product)}. As such, adding and updating of
 * persons uses Product#isSamePerson(Product) for equality so as to ensure that the product being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a product uses Product#equals(Object) so
 * as to ensure that the product with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Product#isSamePerson(Product)
 */
public class UniquePersonList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent product as the given argument.
     */
    public boolean contains(Product toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a product to the list.
     * The product must not already exist in the list.
     */
    public void add(Product toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the list.
     * The product identity of {@code editedProduct} must not be the same as another existing product in the list.
     */
    public void setPerson(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedProduct) && contains(editedProduct)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedProduct);
    }

    /**
     * Removes the equivalent product from the list.
     * The product must exist in the list.
     */
    public void remove(Product toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setPersons(List<Product> products) {
        requireAllNonNull(products);
        if (!personsAreUnique(products)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(products);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Product> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Product> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code products} contains only unique products.
     */
    private boolean personsAreUnique(List<Product> products) {
        for (int i = 0; i < products.size() - 1; i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(i).isSamePerson(products.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
