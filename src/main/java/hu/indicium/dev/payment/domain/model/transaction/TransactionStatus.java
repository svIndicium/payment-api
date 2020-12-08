package hu.indicium.dev.payment.domain.model.transaction;

public enum TransactionStatus {
    OPEN,
    PENDING,
    PAID,
    EXPIRED,
    FAILED,
    CANCELED
}
