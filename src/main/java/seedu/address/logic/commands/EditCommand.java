package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Product;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing product in the address book.
 */

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the product identified "
            + "by the index number used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SERIAL_NR + "Serial Number] "
            + "[" + PREFIX_DISTRIBUTOR + "Distributor] "
            + "[" + PREFIX_PRODUCT_INFO + "PRODUCT INFO] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SERIAL_NR + "91234567 "
            + PREFIX_DISTRIBUTOR + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Product: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the product list.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the product in the filtered product list to edit
     * @param editPersonDescriptor details to edit the product with
     */

    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Product productToEdit = lastShownList.get(index.getZeroBased());
        Product editedProduct = createEditedPerson(productToEdit, editPersonDescriptor);

        if (!productToEdit.isSamePerson(editedProduct) && model.hasPerson(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.updatePerson(productToEdit, editedProduct);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedProduct));
    }

    /**
     * Creates and returns a {@code Product} with the details of {@code productToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Product createEditedPerson(Product productToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert productToEdit != null;


        Name updatedName = editPersonDescriptor.getName().orElse(productToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getSerialNumber().orElse(productToEdit.getSerialNumber());
        DistributorName updatedDistName = editPersonDescriptor.getDistributor().orElse(productToEdit.getDistributor());
        Address updatedAddress = editPersonDescriptor.getProductInfo().orElse(productToEdit.getProductInfo());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(productToEdit.getTags());

        return new Product(updatedName, updatedPhone, updatedDistName, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */

    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private DistributorName distname;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
        */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.distname);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
        */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, distname, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getSerialNumber() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(DistributorName distname) {
            this.distname = distname;
        }

        public Optional<DistributorName> getDistributor() {
            return Optional.ofNullable(distname);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getProductInfo() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
        */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
          */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getSerialNumber().equals(e.getSerialNumber())
                    && getDistributor().equals(e.getDistributor())
                    && getProductInfo().equals(e.getProductInfo())
                    && getTags().equals(e.getTags());
        }
    }
}
