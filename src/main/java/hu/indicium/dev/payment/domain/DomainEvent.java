package hu.indicium.dev.payment.domain;

import java.util.Date;

public interface DomainEvent {

    int eventVersion();

    Date occurredOn();
}
