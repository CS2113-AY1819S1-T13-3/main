package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.product.Product;

/**
 * Adds a product to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a product to Inventarie PRO. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SERIAL_NR + "SERIAL NUMBER "
            + PREFIX_DISTRIBUTOR + "DISTRIBUTOR "
            + PREFIX_PRODUCT_INFO + "PRODUCT INFO "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Apple "
            + PREFIX_SERIAL_NR + "98765432 "
            + PREFIX_DISTRIBUTOR + "Alfred "
            + PREFIX_PRODUCT_INFO + "Contains chicken etc "
            + PREFIX_TAG + "fruit ";

    public static final String MESSAGE_SUCCESS = "New product added: %1$s";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the Product list";


    private final Product toAdd;
    private final Distributor distToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Product}
     */
    public AddCommand(Product product, Distributor distributor) {
        requireNonNull(product);
        toAdd = product;
        requireNonNull(distributor);
        distToAdd = distributor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.addPerson(toAdd);
        model.addDistributor(distToAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }


}
