package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.User;

/**
 * Manages storage of ProductDatabase data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProductDatabaseStorage productDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserDatabaseStorage userDatabaseStorage;


    public StorageManager(ProductDatabaseStorage productDatabaseStorage, UserPrefsStorage userPrefsStorage,
                          UserDatabaseStorage userDatabaseStorage) {
        super();
        this.productDatabaseStorage = productDatabaseStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userDatabaseStorage = userDatabaseStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ProductDatabase methods ==============================

    @Override
    public Path getProductInfoBookFilePath() {
        return productDatabaseStorage.getProductInfoBookFilePath();
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return productDatabaseStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException {
        saveAddressBook(addressBook, productDatabaseStorage.getProductInfoBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        productDatabaseStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ UserDatabase methods ==============================

    @Override
    public Path getUserDatabaseFilePath() {
        return userDatabaseStorage.getUserDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userDatabaseStorage.readUserDatabase(filePath);
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userDatabaseStorage.saveUserDatabase(userDatabase, filePath);
    }

    @Override
    public void deleteProductDatabase(User user) throws IOException {
        logger.fine("Attempting to delete to data file: " + user.getAddressBookFilePath());
        productDatabaseStorage.deleteProductDatabase(user);
    }

    @Override
    @Subscribe
    public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local users data changed, saving to file"));
        try {
            saveUserDatabase(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleUserDeletedEvent(UserDeletedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "User has been deleted, deleting files"));
        deleteProductDatabase(event.data);
    }

    // ============== Storage updater =====================

    public void update(User user) {
        this.productDatabaseStorage = new XmlProductDatabaseStorage(user.getAddressBookFilePath());
    }

}
