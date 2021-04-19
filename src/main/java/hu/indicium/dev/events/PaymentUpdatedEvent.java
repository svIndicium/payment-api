package hu.indicium.dev.events;

import hu.indicium.dev.payment.domain.model.payment.Payment;
import hu.indicium.dev.payment.infrastructure.messaging.Event;
import hu.indicium.dev.payment.infrastructure.messaging.QueueableEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentUpdatedEvent extends QueueableEvent {

    private final UUID paymentId;

    public PaymentUpdatedEvent(Payment payment) {
        super("hu.indicium.api.members", "payment", 1);
        this.paymentId = payment.getPaymentId().getId();
    }
}
