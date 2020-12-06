package hu.indicium.dev.payment.domain.model.transaction;

public enum TransactionStatus {
    PENDING,
    PAID,
    EXPIRED,
    FAILED,
    CANCELED
}
