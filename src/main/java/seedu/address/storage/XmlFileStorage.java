package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook, distributorbook and user database data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableProductDatabase addressBook)
            throws IOException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableProductDatabase loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            IOException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableProductDatabase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given distributorbook data to the specified file.
     */
    public static void saveDistributorDataToFile(Path file, XmlSerializableDistributorBook distributorBook)
            throws IOException {
        try {
            XmlUtil.saveDataToFile(file, distributorBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty distributor book
     */
    public static XmlSerializableDistributorBook loadDistributorDataFromSaveFile(Path file)
            throws DataConversionException,
            IOException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableDistributorBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given user database data to the specified file.
     */
    public static void saveUsersToFile(Path file, XmlSerializableUserDatabase userDatabase)
            throws IOException {
        try {
            XmlUtil.saveDataToFile(file, userDatabase);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns user database in the file or an empty address book
     */
    public static XmlSerializableUserDatabase loadUsersFromSaveFile(Path file) throws DataConversionException,
            IOException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableUserDatabase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the {@code SalesHistory} data to the specified file
     */
    public static void saveSalesHistoryToFile(Path file, XmlSerializableSalesHistory salesHistory)
            throws IOException {
        try {
            XmlUtil.saveDataToFile(file, salesHistory);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns {@code XmlSerializableSalesHistory} from the file.
     */
    public static XmlSerializableSalesHistory loadSalesHistoryFromFile(Path file) throws DataConversionException,
            IOException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSalesHistory.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
