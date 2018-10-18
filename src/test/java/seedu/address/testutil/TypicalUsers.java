package seedu.address.testutil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.DuplicateUserException;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User DEFAULT_USER = new User(new Username("username"), new Password("pass"));
    public static final User AMY = new User(new Username("amy"), new Password("pass"),
            Paths.get("data", "addressbook-amy.xml"));
    public static final User BOB = new User(new Username("bob"), new Password("pass"),
            Paths.get("data", "addressbook-bob.xml"));


    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UserDatabase} with all the typical users.
     */
    public static UserDatabase getTypicalUserDatabase() {
        UserDatabase ud = new UserDatabase();
        for (User user: getTypicalUsers()) {
            try {
                ud.addUser(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        return ud;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(DEFAULT_USER, AMY, BOB));
    }

}
