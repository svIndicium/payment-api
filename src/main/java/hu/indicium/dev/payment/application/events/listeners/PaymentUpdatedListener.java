package hu.indicium.dev.payment.application.events.listeners;

import hu.indicium.dev.events.PaymentUpdatedEvent;
import hu.indicium.dev.payment.domain.DomainEventSubscriber;
import hu.indicium.dev.payment.domain.model.payment.PaymentUpdated;
import hu.indicium.dev.payment.infrastructure.messaging.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class PaymentUpdatedListener implements DomainEventSubscriber<PaymentUpdated> {

    private final EventService eventService;

    @Override
    public void handleEvent(PaymentUpdated aDomainEvent) {
        PaymentUpdatedEvent paymentUpdatedEvent = new PaymentUpdatedEvent(aDomainEvent.getPayment());
        eventService.emitEvent(paymentUpdatedEvent);
        log.info(aDomainEvent.getPayment().getPaymentId().toString());
    }

    @Override
    public Class<PaymentUpdated> subscribedToEventType() {
        return PaymentUpdated.class;
    }
}
