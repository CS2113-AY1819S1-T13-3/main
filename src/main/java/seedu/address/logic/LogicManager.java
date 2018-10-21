package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ChangeUserPasswordCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteUserCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RegisterUserCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.User;
import seedu.address.model.product.Product;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            CommandResult result;
            if (model.hasLoggedIn()) {
                result = command.execute(model, history);
            } else {
                logger.info("User attempts to use a command without logging in first.");
                result = executeUnauthenticatedCommands(commandText, command);
                history.clear();
            }
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Distributor> getFilteredDistributorList() {
        return model.getFilteredDistributorList();
    }

    @Override
    public ObservableList<Product> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public CommandResult executeUnauthenticatedCommands (String commandText, Command command) throws CommandException {
        CommandResult result;
        try {
            if (commandText.split(" ")[0].equals(LoginCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(HelpCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ExitCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(RegisterUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(DeleteUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ChangeUserPasswordCommand.COMMAND_WORD)) {
                result = command.execute(model, history);
            } else {
                result = null;
            }
        } finally { }

        return result;
    }

    @Override
    public boolean hasLoggedIn() {
        return model.hasLoggedIn();
    }

    @Override
    public User getLoggedInUser() {
        return model.getLoggedInUser();
    }

}
