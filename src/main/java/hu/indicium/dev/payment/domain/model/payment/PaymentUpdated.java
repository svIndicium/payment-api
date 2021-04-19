package hu.indicium.dev.payment.domain.model.payment;

import hu.indicium.dev.payment.domain.DomainEvent;
import lombok.Getter;

import java.util.Date;

@Getter
public class PaymentUpdated implements DomainEvent {

    private final Payment payment;

    private final Date occurredOn;

    public PaymentUpdated(Payment payment) {
        this.payment = payment;
        this.occurredOn = new Date();
    }

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
