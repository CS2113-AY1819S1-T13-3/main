package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;

/**
 * Deletes a product identified using it's displayed index from the address book.
 */
public class DeleteDistributorCommand extends Command {

    public static final String COMMAND_WORD = "deleteDistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the distributor identified by the index number used in the displayed distributor list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Distributor: %1$s";

    private final Index targetIndex;

    public DeleteDistributorCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Distributor> lastShownList = model.getFilteredDistributorList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
        }

        Distributor distributorToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDistributor(distributorToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, distributorToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDistributorCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDistributorCommand) other).targetIndex)); // state check
    }
}
