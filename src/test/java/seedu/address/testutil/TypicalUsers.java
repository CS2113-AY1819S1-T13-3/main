package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User DEFAULT_USER = new User(new Username("user"), new Password("pas21"));
    public static final User AMY = new User(new Username("amy"), new Password("pass1"));
    public static final User BOB = new User(new Username("bob"), new Password("pass1"));


    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UserDatabase} with all the typical users.
     */
    public static UserDatabase getTypicalUserDatabase() {
        UserDatabase ud = new UserDatabase();
        for (User user: getTypicalUsers()) {
            ud.addUser(user);
        }
        return ud;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(DEFAULT_USER, AMY, BOB));
    }

}
