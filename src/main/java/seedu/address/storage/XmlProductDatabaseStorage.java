package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.login.User;

/**
 * A class to access ProductDatabase data stored as an xml file on the hard disk.
 */
public class XmlProductDatabaseStorage implements ProductDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlProductDatabaseStorage.class);

    private Path filePath;

    public XmlProductDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProductInfoBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyProductDatabase> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyProductDatabase> readAddressBook(Path filePath) throws DataConversionException,
                                                                                 IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ProductDatabase file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableProductDatabase xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyProductDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableProductDatabase(addressBook));
    }

    /**
     * Similar to {@link #deleteAddressBook(User)}
     * @param user location of the data. Cannot be null
     */
    public void deleteAddressBook(User user) throws IOException {
        requireNonNull(filePath);
        Files.delete(filePath);
    }

}
