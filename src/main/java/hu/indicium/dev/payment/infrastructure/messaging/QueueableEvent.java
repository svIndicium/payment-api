package hu.indicium.dev.payment.infrastructure.messaging;

public abstract class QueueableEvent extends Event {

    private final String queueName;

    public QueueableEvent(String routingKey, String queueName) {
        super(routingKey);
        this.queueName = queueName;
    }

    public QueueableEvent(String routingKey, String queueName, int version) {
        super(routingKey, version);
        this.queueName = queueName;
    }
}
