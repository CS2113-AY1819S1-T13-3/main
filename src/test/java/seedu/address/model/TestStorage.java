
package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.login.User;
import seedu.address.storage.Storage;

/**
 * Acts as a dummy StorageManager
 */
public class TestStorage extends ComponentManager implements Storage {

    public TestStorage() {
    }

    @Override
    public Path getUserPrefsFilePath() {
        return null;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
    }

    @Override
    public Path getProductInfoBookFilePath() {
        return null;
    }

    public Path getAddressBookFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readAddressBook() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readAddressBook(Path filePath) {
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException {
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException {
    }

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Override
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
    }


    @Override
    public Path getUserDatabaseFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath)
            throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {
    }

    public void update(User user) {
    }

    @Override
    public void deleteProductDatabase(User user) {
    }


    @Override
    public void handleUserDeletedEvent(UserDeletedEvent event) {
    }


    @Override
    public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent abce) {
    }
}
