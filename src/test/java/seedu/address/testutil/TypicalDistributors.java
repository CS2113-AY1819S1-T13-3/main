package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.distributor.Distributor;

/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalDistributors {

    public static final Distributor AHA = new DistributorBuilder().withName("Ah Bee").withPhone("94351253").build();
    public static final Distributor AHB = new DistributorBuilder().withName("Ah Cee").withPhone("94351258").build();
    public static final Distributor AHC = new DistributorBuilder().withName("Ah Dee").withPhone("95352563").build();
    public static final Distributor AHD = new DistributorBuilder().withName("Ah Eee").withPhone("87652533").build();
    public static final Distributor AHE = new DistributorBuilder().withName("Elle Meyer").withPhone("9482224").build();
    public static final Distributor AHF = new DistributorBuilder().withName("Fiona Kunz").withPhone("9482427").build();
    public static final Distributor AHG = new DistributorBuilder().withName("George Best").withPhone("9482442").build();

    // Manually added
    public static final Distributor AHH = new DistributorBuilder().withName("Hoon Meier").withPhone("8482424").build();
    public static final Distributor AHI = new DistributorBuilder().withName("Ida Mueller").withPhone("8482131").build();

    public static final String KEYWORD_MATCHING_EEE = "Eee"; // A keyword that matches MEIER

    private TypicalDistributors() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical distributors.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Distributor distributor : getTypicalDistributors()) {
            ab.addDistributor(distributor);
        }
        return ab;
    }

    public static List<Distributor> getTypicalDistributors() {
        return new ArrayList<>(Arrays.asList(AHA, AHB, AHC, AHD, AHE, AHF, AHG));
    }
}
