package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUsersList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.person.Product;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Product validProduct = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validProduct).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validProduct), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validProduct), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Product validProduct = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validProduct);
        ModelStub modelStub = new ModelStubWithPerson(validProduct);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PRODUCT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Product chocolate = new PersonBuilder().withName("Chocolate").build();
        Product candy = new PersonBuilder().withName("Candy").build();
        AddCommand addChocolateCommand = new AddCommand(chocolate);
        AddCommand addCandyCommand = new AddCommand(candy);

        // same object -> returns true
        assertTrue(addChocolateCommand.equals(addChocolateCommand));

        // same values -> returns true
        AddCommand addChocolateCommandCopy = new AddCommand(chocolate);
        assertTrue(addChocolateCommand.equals(addChocolateCommandCopy));

        // different types -> returns false
        assertFalse(addChocolateCommand.equals(1));

        // null -> returns false
        assertFalse(addChocolateCommand.equals(null));

        // different product -> returns false
        assertFalse(addChocolateCommand.equals(addCandyCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {

        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getProductInfoBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getDistributorInfoBook() {
            return null;
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            return false;
        }

        @Override
        public boolean hasPerson(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDistributor(Distributor target) {

        }

        @Override
        public void deletePerson(Product target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDistributor(Distributor distributor) {

        }

        @Override
        public void updatePerson(Product target, Product editedProduct) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Distributor> getFilteredDistributorList() {
            return null;
        }

        @Override
        public void updateFilteredDistributorList(Predicate<Distributor> predicate) {

        }

        @Override
        public void updateFilteredPersonList(Predicate<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {

        }

        @Override
        public String getDaysHistory(String day) {
            return null;
        }

        @Override
        public String getActiveDayHistory() {
            return null;
        }

        @Override
        public Transaction getLastTransaction() {
            return null;
        }

        @Override
        public boolean hasLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoginStatus(boolean status) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getLoggedInUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public void addUser(User person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteUser(User target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUsersList uniqueUserList) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single product.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Product product;

        ModelStubWithPerson(Product product) {
            requireNonNull(product);
            this.product = product;
        }

        @Override
        public boolean hasPerson(Product product) {
            requireNonNull(product);
            return this.product.isSamePerson(product);
        }
    }

    /**
     * A Model stub that always accept the product being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Product> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Product product) {
            requireNonNull(product);
            return personsAdded.stream().anyMatch(product::isSamePerson);
        }

        @Override
        public void addPerson(Product product) {
            requireNonNull(product);
            personsAdded.add(product);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getProductInfoBook() {
            return new AddressBook();
        }
    }

}
