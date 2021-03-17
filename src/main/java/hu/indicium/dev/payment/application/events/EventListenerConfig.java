package hu.indicium.dev.payment.application.events;

import hu.indicium.dev.payment.application.events.listeners.PaymentUpdatedListener;
import hu.indicium.dev.payment.domain.DomainEventPublisher;
import hu.indicium.dev.payment.domain.DomainEventSubscriber;
import hu.indicium.dev.payment.infrastructure.messaging.EventService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Configuration
public class EventListenerConfig implements CommandLineRunner {

    private final EventService eventService;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run(String... args) throws Exception {
        List<DomainEventSubscriber> subscribers = Arrays.asList(
            new PaymentUpdatedListener(eventService)
        );
        for (DomainEventSubscriber subscriber : subscribers) {
            DomainEventPublisher.instance().subscribe(subscriber);
        }
    }
}
