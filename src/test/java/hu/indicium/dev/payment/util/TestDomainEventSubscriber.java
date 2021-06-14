package hu.indicium.dev.payment.util;

import hu.indicium.dev.payment.domain.DomainEvent;
import hu.indicium.dev.payment.domain.DomainEventPublisher;
import hu.indicium.dev.payment.domain.DomainEventSubscriber;

import java.util.ArrayList;
import java.util.List;

public class TestDomainEventSubscriber implements DomainEventSubscriber<DomainEvent> {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private TestDomainEventSubscriber() {
    }

    public static TestDomainEventSubscriber subscribe() {
        TestDomainEventSubscriber subscriber = new TestDomainEventSubscriber();
        DomainEventPublisher.instance().subscribe(subscriber);
        return subscriber;
    }

    @Override
    public void handleEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    @Override
    public Class<DomainEvent> subscribedToEventType() {
        return DomainEvent.class;
    }

    public void clear() {
        this.domainEvents.clear();
    }

    public List<DomainEvent> getEvents() {
        return this.domainEvents;
    }
}