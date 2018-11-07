package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRIBUTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMAINING_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.product.*;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SERIAL_NR, PREFIX_DISTRIBUTOR, PREFIX_PRODUCT_INFO,
                        PREFIX_REMAINING_ITEMS, PREFIX_TAG);
        // The problem is not here becuase the error does not occur....
        if (!arePrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_PRODUCT_INFO, PREFIX_SERIAL_NR,
                PREFIX_DISTRIBUTOR,PREFIX_REMAINING_ITEMS)
                || !argMultimap.getPreamble().isEmpty()) {
            System.out.print("\"Vi fel inne\"");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        SerialNumber serialNumber = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_SERIAL_NR).get());
        DistributorName distname = ParserUtil.parseDistName(argMultimap.getValue(PREFIX_DISTRIBUTOR).get());
        ProductInfo productInfo = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_PRODUCT_INFO).get());
        RemainingItems remainingItems = ParserUtil.
                parseRemainingItems(argMultimap.getValue(PREFIX_REMAINING_ITEMS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));


        DistributorPhone distphone = new DistributorPhone("00000000");

        Product product = new Product(name, serialNumber, distname, productInfo, remainingItems, tagList);
        Distributor distributor = new Distributor(distname, distphone, new HashSet<>());
        System.out.print(product.getRemainingItems());
        return new AddCommand(product, distributor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
