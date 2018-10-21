package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorName;
import seedu.address.testutil.ProductBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {
    Distributor validDistributor= new Distributor(new DistributorName("Gara"),new DistributorPhone("123123123"));

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserDatabase(), new TestStorage());
    }

    @Test
    public void execute_newPerson_success() {
        Product validProduct = new ProductBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.addPerson(validProduct);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validProduct,validDistributor), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validProduct), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Product productInList = model.getProductInfoBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(productInList,validDistributor), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PRODUCT);
    }

}
