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
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.login.User;

/**
 * A class to access DistributorBook data stored as an xml file on the hard disk.
 */
public class XmlDistributorBookStorage implements DistributorBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDistributorBookStorage.class);

    private Path filePath;

    public XmlDistributorBookStorage(Path filePath) {
        this.filePath = filePath;
    }


    public Path getDistributorBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException, IOException {
        return readDistributorBook(filePath);
    }

    /**
     * Similar to {@link #readDistributorBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDistributorBook> readDistributorBook(Path filePath) throws DataConversionException,
                                                                                 IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AddressBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableDistributorBook xmlDistributorBook = XmlFileStorage.loadDistributorDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlDistributorBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException {
        saveDistributorBook(distributorBook, filePath);
    }

    /**
     * Similar to {@link #saveDistributorBook(ReadOnlyDistributorBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDistributorBook(ReadOnlyDistributorBook distributorBook, Path filePath) throws IOException {
        requireNonNull(distributorBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDistributorDataToFile(filePath, new XmlSerializableDistributorBook(distributorBook));
    }

    /**
     * Similar to {@link #deleteDistributorBook(User)}
     * @param user location of the data. Cannot be null
     */
    public void deleteDistributorBook(User user) throws IOException {
        requireNonNull(filePath);
        Files.delete(filePath);
    }

}
