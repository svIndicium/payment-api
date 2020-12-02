package hu.indicium.dev.payment.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DomainEventPublisher {

    private static DomainEventPublisher instance;

    private boolean publishing;

    @SuppressWarnings("rawtypes")
    private List subscribers;

    private DomainEventPublisher() {
        super();

        this.setPublishing(false);
        this.ensureSubscribersList();
    }

    public static DomainEventPublisher instance() {
        if (instance == null) {
            instance = new DomainEventPublisher();
        }
        return instance;
    }

    public <T> void publish(final T aDomainEvent) {
        if (!this.isPublishing() && this.hasSubscribers()) {

            try {
                this.setPublishing(true);

                Class<?> eventType = aDomainEvent.getClass();

                @SuppressWarnings("unchecked")
                List<hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber<T>> allSubscribers = this.subscribers();

                for (hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber<T> subscriber : allSubscribers) {
                    Class<?> subscribedToType = subscriber.subscribedToEventType();

                    if (eventType == subscribedToType || subscribedToType == hu.indicium.dev.ledenadministratie.domain.DomainEvent.class) {
                        subscriber.handleEvent(aDomainEvent);
                    }
                }

            } finally {
                this.setPublishing(false);
            }
        }
    }

    public void publishAll(Collection<hu.indicium.dev.ledenadministratie.domain.DomainEvent> aDomainEvents) {
        for (hu.indicium.dev.ledenadministratie.domain.DomainEvent domainEvent : aDomainEvents) {
            this.publish(domainEvent);
        }
    }

    public void reset() {
        if (!this.isPublishing()) {
            this.setSubscribers(null);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> DomainEventPublisher subscribe(hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber<T> aSubscriber) {
        if (!this.isPublishing()) {
            this.ensureSubscribersList();

            this.subscribers().add(aSubscriber);
        }
        return this;
    }

    @SuppressWarnings("rawtypes")
    private void ensureSubscribersList() {
        if (!this.hasSubscribers()) {
            this.setSubscribers(new ArrayList());
        }
    }

    private boolean isPublishing() {
        return this.publishing;
    }

    private void setPublishing(boolean aFlag) {
        this.publishing = aFlag;
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    @SuppressWarnings("rawtypes")
    private List subscribers() {
        return this.subscribers;
    }

    @SuppressWarnings("rawtypes")
    private void setSubscribers(List aSubscriberList) {
        this.subscribers = aSubscriberList;
    }
}