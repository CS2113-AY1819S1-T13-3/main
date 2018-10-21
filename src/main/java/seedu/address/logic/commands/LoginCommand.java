package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;

/**
 *  Authenticates user login's credentials
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = "Login with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: login u/user p/123456"
            + "\nDefault user login credentials are as follows: USERNAME: user , PASSWORD: pass";

    public static final String MESSAGE_AUTHENTICATION_SUCCESS = "Login successful!";
    public static final String MESSAGE_AUTHENTICATION_FAILURE = "Username or password is incorrect. Please try again.";
    public static final String MESSAGE_AUTHENTICATED = "You are logged in.";

    private final Username username;
    private final Password password;

    /**
     * Creates a LoginCommand to authenticate the specified {@code User}
     */
    public LoginCommand(Username username, Password password) {
        requireNonNull(username);
        requireNonNull(password);

        this.username = username;
        this.password = password;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(username);
        requireNonNull(password);
        try {
            if (model.checkAuthentication(this.username, this.password)) {
                return new CommandResult(MESSAGE_AUTHENTICATION_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_AUTHENTICATION_FAILURE);
            }
        } catch (AuthenticatedException e) {
            throw new CommandException(MESSAGE_AUTHENTICATED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && this.password.equals(((LoginCommand) other).password));

    }
}
