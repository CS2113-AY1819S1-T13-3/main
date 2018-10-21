package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ROE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalDistributors.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditDistributorCommand.EditDistributorDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.distributor.Distributor;
import seedu.address.testutil.DistributorBuilder;
import seedu.address.testutil.EditDistributorDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditDCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Distributor editedDistributor = new DistributorBuilder().build();
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(editedDistributor).build();
        EditDistributorCommand editCommand = new EditDistributorCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedDistributor);

        Model expectedModel = new ModelManager(new AddressBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(model.getFilteredDistributorList().get(0), editedDistributor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDistributor = Index.fromOneBased(model.getFilteredDistributorList().size());
        Distributor lastDistributor = model.getFilteredDistributorList().get(indexLastDistributor.getZeroBased());

        DistributorBuilder distributorInList = new DistributorBuilder(lastDistributor);
        Distributor editedDistributor = distributorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(indexLastDistributor, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedDistributor);

        Model expectedModel = new ModelManager(new AddressBook(model.getProductInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(lastDistributor, editedDistributor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editDCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_FIRST_PERSON, new EditDistributorDescriptor());
        Distributor editedDistributor = model.getFilteredDistributorList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedDistributor);

        Model expectedModel = new ModelManager(new AddressBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editDCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Distributor distributorInFilteredList = model.getFilteredDistributorList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Distributor editedDistributor = new DistributorBuilder(distributorInFilteredList)
                .withName(VALID_NAME_BOB).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_FIRST_PERSON,
                new EditDistributorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedDistributor);

        Model expectedModel = new ModelManager(new AddressBook(model.getProductInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(model.getFilteredDistributorList().get(0), editedDistributor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editDCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Distributor firstDistributor = model.getFilteredDistributorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(firstDistributor).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editDCommand, model, commandHistory, EditDistributorCommand.MESSAGE_DUPLICATE_DISTRIBUTOR);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit product in filtered list into a duplicate in address book
        Distributor distributorInList = model.getDistributorInfoBook().getDistributorList()
                .get(INDEX_SECOND_PERSON.getZeroBased());
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_FIRST_PERSON,
                new EditDistributorDescriptorBuilder(distributorInList).build());

        assertCommandFailure(editDCommand, model, commandHistory, EditDistributorCommand.MESSAGE_DUPLICATE_DISTRIBUTOR);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editDCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDistributorInfoBook()
                .getDistributorList().size());

        EditDistributorCommand editDCommand = new EditDistributorCommand(outOfBoundIndex,
                new EditDistributorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editDCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Distributor editedDistributor = new DistributorBuilder().build();
        Distributor distributorToEdit = model.getFilteredDistributorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(editedDistributor).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getProductInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(distributorToEdit, editedDistributor);
        expectedModel.commitAddressBook();

        // edit -> first product edited
        editDCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered product list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first product edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editDCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Product} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited product in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the product object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Distributor editedDistributor = new DistributorBuilder().build();
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(editedDistributor).build();
        EditDistributorCommand editDCommand = new EditDistributorCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getProductInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Distributor distributorToEdit = model.getFilteredDistributorList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updateDistributor(distributorToEdit, editedDistributor);
        expectedModel.commitAddressBook();

        // edit -> edits second product in unfiltered product list / first product in filtered product list
        editDCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered product list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredDistributorList().get(INDEX_FIRST_PERSON.getZeroBased()), distributorToEdit);
        // redo -> edits same second product in unfiltered product list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditDistributorCommand standardCommand = new EditDistributorCommand(INDEX_FIRST_PERSON, DESC_JOE);

        // same values -> returns true
        EditDistributorDescriptor copyDescriptor = new EditDistributorDescriptor(DESC_JOE);
        EditDistributorCommand commandWithSameValues = new EditDistributorCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDistributorCommand(INDEX_SECOND_PERSON, DESC_JOE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDistributorCommand(INDEX_FIRST_PERSON, DESC_ROE)));
    }

}
