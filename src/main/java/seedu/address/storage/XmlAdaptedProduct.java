package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Address;
import seedu.address.model.product.Email;
import seedu.address.model.product.Name;
import seedu.address.model.product.Phone;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Product.
 */
public class XmlAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String distname;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedProduct.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProduct() {}

    /**
     * Constructs an {@code XmlAdaptedProduct} with the given product details.
     */
    public XmlAdaptedProduct(String name, String phone, String distname, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.distname = distname;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Product into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedProduct
     */
    public XmlAdaptedProduct(Product source) {
        name = source.getName().fullName;
        phone = source.getSerialNumber().value;
        distname = source.getDistributor().fullDistName;
        address = source.getProductInfo().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted product object into the model's Product object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public Product toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (distname == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!DistributorName.isValidName(distname)) {
            throw new IllegalValueException(DistributorName.MESSAGE_NAME_CONSTRAINTS);
        }
        final DistributorName modelDistName = new DistributorName(distname);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Product(modelName, modelPhone, modelDistName, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProduct)) {
            return false;
        }

        XmlAdaptedProduct otherPerson = (XmlAdaptedProduct) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(distname, otherPerson.distname)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
