package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Product;
import seedu.address.testutil.PersonBuilder;

public class ProductCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Product productWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(productWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, productWithNoTags, 1);

        // with tags
        Product productWithTags = new PersonBuilder().build();
        personCard = new PersonCard(productWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, productWithTags, 2);
    }

    @Test
    public void equals() {
        Product product = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(product, 0);

        // same product, same index -> returns true
        PersonCard copy = new PersonCard(product, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different product, same index -> returns false
        Product differentProduct = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentProduct, 0)));

        // same product, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(product, 1)));
    }

    /* Asserts that {@code personCard} displays the details of {@code expectedProduct} correctly and matches
     * {@code expectedId}.
     */

    private void assertCardDisplay(PersonCard personCard, Product expectedProduct, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify product details are displayed correctly
        assertCardDisplaysPerson(expectedProduct, personCardHandle);
    }
}
