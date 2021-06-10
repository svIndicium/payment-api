package hu.indicium.dev.events;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.infrastructure.messaging.Event;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentUpdatedEvent extends Event {

    private final UUID paymentId;

    public PaymentUpdatedEvent(Payment payment) {
        super("service.members.payments", 1);
        this.paymentId = payment.getPaymentId().getId();
    }
}
