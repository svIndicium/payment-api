package hu.indicium.dev.payment.infrastructure.messaging;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Event implements Serializable {
    private final String routingKey;

    private final int version;

    protected Event(String routingKey) {
        this.routingKey = routingKey;
        this.version = 1;
    }

    protected Event(String routingKey, int version) {
        this.routingKey = routingKey;
        this.version = version;
    }
}
