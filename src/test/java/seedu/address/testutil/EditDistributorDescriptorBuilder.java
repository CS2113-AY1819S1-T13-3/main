package seedu.address.testutil;

import seedu.address.logic.commands.EditDCommand.EditDistributorDescriptor;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;

/**
 * A utility class to help with building EditDistributorDescriptor objects.
 */
public class EditDistributorDescriptorBuilder {

    private EditDistributorDescriptor descriptor;

    public EditDistributorDescriptorBuilder() {
        descriptor = new EditDistributorDescriptor();
    }

    public EditDistributorDescriptorBuilder(EditDistributorDescriptor descriptor) {
        this.descriptor = new EditDistributorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDistributorDescriptor} with fields containing {@code product}'s details
     */
    public EditDistributorDescriptorBuilder(Distributor distributor) {
        descriptor = new EditDistributorDescriptor();
        descriptor.setDistName(distributor.getDistName());
        descriptor.setDistPhone(distributor.getDistPhone());
    }

    /**
     * Sets the {@code Name} of the {@code EditDistributorDescriptor} that we are building.
     */
    public EditDistributorDescriptorBuilder withName(String name) {
        descriptor.setDistName(new DistributorName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditDistributorDescriptor} that we are building.
     */
    public EditDistributorDescriptorBuilder withPhone(String phone) {
        descriptor.setDistPhone(new DistributorPhone(phone));
        return this;
    }


    public EditDistributorDescriptor build() {
        return descriptor;
    }
}
