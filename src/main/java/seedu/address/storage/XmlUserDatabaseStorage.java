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
import seedu.address.model.ReadOnlyUserDatabase;

/**
 * A class to access UserDatabase data stored as an xml file on the hard disk.
 */
public class XmlUserDatabaseStorage implements UserDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserDatabaseStorage.class);

    private Path filePath;

    public XmlUserDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(filePath);
    }

    /**
     * Similar to {@link #readUserDatabase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath) throws DataConversionException,
            IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Users file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUserDatabase xmlUsers = XmlFileStorage.loadUsersFromSaveFile(filePath);
        try {
            return Optional.of(xmlUsers.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, filePath);
    }

    /**
     * Similar to {@link #saveUserDatabase (ReadOnlyUserDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {
        requireNonNull(userDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveUsersToFile(filePath, new XmlSerializableUserDatabase(userDatabase));
    }
}
