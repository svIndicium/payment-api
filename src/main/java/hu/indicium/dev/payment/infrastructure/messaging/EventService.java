package hu.indicium.dev.payment.infrastructure.messaging;

public interface EventService {
    void emitEvent(Event event);
}
