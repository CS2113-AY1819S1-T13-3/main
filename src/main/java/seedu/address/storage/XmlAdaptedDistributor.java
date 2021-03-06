package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Distributor.
 */
public class XmlAdaptedDistributor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Distributor's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement
    private List<XmlAdaptedDistProd> distprods = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedDistributor.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDistributor() {}

    /**
     * Constructs an {@code XmlAdaptedDistributor} with the given distributor details.
     */
    public XmlAdaptedDistributor(String name, String phone,
                                 List<XmlAdaptedDistProd> prods, List<XmlAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.distprods = prods;
        this.tagged = tags;
    }

    /**
     * Converts a given Distributor into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDistributor
     */
    public XmlAdaptedDistributor(Distributor source) {
        name = source.getDistName().fullDistName;
        phone = source.getDistPhone().value;
        distprods = source.getDistProds().stream()
                .map(XmlAdaptedDistProd::new)
                .collect(Collectors.toList());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted distributor object into the model's Distributor object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted distributor
     */
    public Distributor toModelType() throws IllegalValueException {
        final List<DistributorProduct> distributorProds = new ArrayList<>();
        final List<Tag> distributorTags = new ArrayList<>();

        for (XmlAdaptedDistProd prod : distprods) {
            distributorProds.add(prod.toModelType());
        }
        for (XmlAdaptedTag tag : tagged) {
            distributorTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DistributorName.class.getSimpleName()));
        }
        if (!DistributorName.isValidName(name)) {
            throw new IllegalValueException(DistributorName.MESSAGE_NAME_CONSTRAINTS);
        }
        final DistributorName modelName = new DistributorName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DistributorPhone.class.getSimpleName()));
        }
        if (!DistributorPhone.isValidPhone(phone)) {
            throw new IllegalValueException(DistributorPhone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final DistributorPhone modelPhone = new DistributorPhone(phone);
        final Set<DistributorProduct> modelProds = new HashSet<>(distributorProds);
        final Set<Tag> modelTags = new HashSet<>(distributorTags);

        return new Distributor(modelName, modelPhone, modelProds, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDistributor)) {
            return false;
        }

        XmlAdaptedDistributor otherDistributor = (XmlAdaptedDistributor) other;
        return Objects.equals(name, otherDistributor.name)
                && Objects.equals(phone, otherDistributor.phone);
    }

}
