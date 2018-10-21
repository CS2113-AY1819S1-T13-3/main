package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DISTRIBUTORS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;

/**
 * Edits the details of an existing distributor in the address book.
 */
public class EditDistributorCommand extends Command {

    public static final String COMMAND_WORD = "editDistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the distributor identified "
            + "by the index number used in the displayed distributor list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DIST_NAME + "DISTRIBUTOR NAME "
            + "[" + PREFIX_DIST_PHONE + "DISTRIBUTOR PHONE "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DIST_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_DISTRIBUTOR_SUCCESS = "Edited Distributor: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR = "This distributor already exists in the address book.";
    private final Index index;
    private final EditDistributorDescriptor editDistributorDescriptor;
    /**
     * @param index of the product in the filtered distributor list to edit
     * @param editDistributorDescriptor details to edit the distributor with
     */
    public EditDistributorCommand(Index index, EditDistributorDescriptor editDistributorDescriptor) {
        requireNonNull(index);
        requireNonNull(editDistributorDescriptor);

        this.index = index;
        this.editDistributorDescriptor = new EditDistributorDescriptor(editDistributorDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Distributor> lastShownList = model.getFilteredDistributorList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
        }

        Distributor distributorToEdit = lastShownList.get(index.getZeroBased());
        Distributor editedDistributor = createEditedDistributor(distributorToEdit, editDistributorDescriptor);

        if (!distributorToEdit.isSameDistributor(editedDistributor) && model.hasDistributor(editedDistributor)) {
            throw new CommandException(MESSAGE_DUPLICATE_DISTRIBUTOR);
        }

        model.updateDistributor(distributorToEdit, editedDistributor);
        model.updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_DISTRIBUTOR_SUCCESS, editedDistributor));
    }

    /**
     * Creates and returns a {@code Distributor} with the details of {@code distributorToEdit}
     * edited with {@code editDistributorDescriptor}.
     */
    private static Distributor createEditedDistributor(Distributor distributorToEdit,
                                                       EditDistributorDescriptor editDistributorDescriptor) {
        assert distributorToEdit != null;

        DistributorName updatedName = editDistributorDescriptor.getDistName().orElse(distributorToEdit.getDistName());
        DistributorPhone updatedPhone =
                editDistributorDescriptor.getDistPhone().orElse(distributorToEdit.getDistPhone());

        return new Distributor(updatedName, updatedPhone);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDistributorCommand)) {
            return false;
        }

        // state check
        EditDistributorCommand e = (EditDistributorCommand) other;
        return index.equals(e.index)
                && editDistributorDescriptor.equals(e.editDistributorDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */
    public static class EditDistributorDescriptor {
        private DistributorName name;
        private DistributorPhone phone;

        public EditDistributorDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDistributorDescriptor(EditDistributorDescriptor toCopy) {
            setDistName(toCopy.name);
            setDistPhone(toCopy.phone);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone);
        }

        public void setDistName(DistributorName name) {
            this.name = name;
        }

        public Optional<DistributorName> getDistName() {
            return Optional.ofNullable(name);
        }

        public void setDistPhone(DistributorPhone phone) {
            this.phone = phone;
        }

        public Optional<DistributorPhone> getDistPhone() {
            return Optional.ofNullable(phone);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDistributorDescriptor)) {
                return false;
            }

            // state check
            EditDistributorDescriptor e = (EditDistributorDescriptor) other;

            return getDistName().equals(e.getDistName())
                    && getDistPhone().equals(e.getDistPhone());
        }
    }
}
