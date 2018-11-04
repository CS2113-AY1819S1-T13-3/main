package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;

//@@author Amirul
public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_loginAcceptedByModel_logoutSuccessful() {
        LogoutCommandTest.ModelStubAcceptingLogout modelStub = new LogoutCommandTest.ModelStubAcceptingLogout();
        CommandResult commandResult = getLogoutCommand(modelStub).execute(modelStub, new CommandHistory());

        assertEquals(LogoutCommand.MESSAGE_LOGOUT_SUCCESS, commandResult.feedbackToUser);
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private LogoutCommand getLogoutCommand(Model model) {
        LogoutCommand command = new LogoutCommand();
        command.execute(model, new CommandHistory());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDistributorBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorBook() {
            fail("This method should not be called.");
            return null;
        }


        @Override
        public boolean hasLoggedIn() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkAuthentication(Username username, Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean checkCredentials(Username username, Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getProductInfoBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean hasPerson(Product product) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void deleteDistributor(Distributor distributor) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(Product product) {
            fail("This method should not be called.");
        }

        @Override
        public void addPerson(Product product) {
            fail("This method should not be called.");
        }

        @Override
        public void addDistributor(Distributor distributor) {
            fail("This method should not be called.");
        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Product target, Product editedProduct) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Distributor> getFilteredDistributorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredDistributorList(Predicate<Distributor> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            fail("This method should not be called.");
        }

        public boolean canUndoAddressBook() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canUndoDistributorBook() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canRedoAddressBook() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canRedoDistributorBook() {
            fail("This method should not be called.");
            return false;
        }

        public void undoAddressBook() {
            fail("This method should not be called.");
        }

        public void undoDistributorBook() {
            fail("This method should not be called.");
        }

        public void redoAddressBook() {
            fail("This method should not be called.");
        }

        public void redoDistributorBook() {
            fail("This method should not be called.");
        }

        public void commitAddressBook() {
            fail("This method should not be called.");
        }

        public void commitDistributorBook() {
            fail("This method should not be called.");
        }

        public void addTransaction(Transaction transaction) {
            fail("This method should not be called.");
        }

        public void addReminder(Reminder reminder) {
            fail("This method should not be called.");
        }

        public void removeReminder(String reminderTime) {
            fail("This method should not be called.");
        }

        public ArrayList<Reminder> getOverdueReminders() {
            fail("This method should not be called.");
            return null;
        }

        public String getDaysTransactions(String day) {
            fail("This method should not be called.");
            return null;
        }

        public String getDaysTransactions() {
            fail("This method should not be called.");
            return null;
        }

        public Transaction getLastTransaction() {
            fail("This method should not be called.");
            return null;
        }

    }

    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingLogout extends ModelStub {

        private boolean loginStatus = false;

        @Override
        public ArrayList<Reminder> getAllReminders() {
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            return null;
        }

        @Override
        public boolean checkAuthentication(Username username, Password password) {
            requireNonNull(username);
            requireNonNull(password);
            setLoginStatus(true);
            return true;
        }

        @Override
        public void setLoginStatus(boolean status) {
            this.loginStatus = true;
        }

        @Override
        public boolean hasLoggedIn() {
            return this.loginStatus;
        }
    }
}
