package hu.indicium.dev.payment.infrastructure.messaging;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Event implements Serializable {
    private String routingKey;

    private final int version;

    public Event(String routingKey) {
        this.routingKey = routingKey;
        this.version = 1;
    }

    public Event(String routingKey, int version) {
        this.routingKey = routingKey;
        this.version = version;
    }
}
