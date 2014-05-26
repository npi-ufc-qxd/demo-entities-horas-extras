package domain;

import entities.annotations.EntityDescriptor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Canceled")
@EntityDescriptor(hidden = true)
public class StatusCanceled extends Status {

    public StatusCanceled() {
        this.setId(4L);
        this.setDescription("Canceled");
    }

    StatusCanceled(Overtime overtime) {
        this();
        this.overtime = overtime;
    }

    @Override
    public void approve() {
        throw new IllegalStateException("This request is canceled.");
    }

    @Override
    public void pay() {
        throw new IllegalStateException("This request is canceled.");
    }

    @Override
    public void reject() {
        throw new IllegalStateException("This request is canceled.");
    }

    @Override
    public void revert(String remark) {
        throw new IllegalStateException("This request is canceled.");
    }
}