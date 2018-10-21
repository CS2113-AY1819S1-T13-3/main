package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Address;
import seedu.address.model.product.Email;
import seedu.address.model.product.Name;
import seedu.address.model.product.Phone;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_NAME = "Apple";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "Ah Huat";
    public static final String DEFAULT_DISTRIBUTOR_NAME = "Gara";
    public static final String DEFAULT_ADDRESS = "fruit";

    private Name name;
    private Phone phone;
    private DistributorName distname;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public ProductBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        distname = new DistributorName(DEFAULT_DISTRIBUTOR_NAME);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        name = productToCopy.getName();
        phone = productToCopy.getSerialNumber();
        distname = productToCopy.getDistributor();
        address = productToCopy.getProductInfo();
        tags = new HashSet<>(productToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Product} that we are building.
     */
    public ProductBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public ProductBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Product} that we are building.
     */
    public ProductBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Product} that we are building.
     */
    public ProductBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Product} that we are building.
     */
    public ProductBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Product build() {
        return new Product(name, phone, distname, address, tags);
    }

}
