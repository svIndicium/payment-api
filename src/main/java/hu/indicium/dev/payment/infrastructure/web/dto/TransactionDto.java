package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.transaction.Transaction;
import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import hu.indicium.dev.payment.domain.model.transaction.TransactionType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TransactionDto {
    private UUID id;

    private TransactionStatus status;

    private UUID paymentId;

    private Double amount;

    private Date createdAt;

    private Date updatedAt;

    private Date finishedAt;

    private TransactionType type;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getTransactionId().getId();
        this.status = transaction.getStatus();
        this.paymentId = transaction.getPayment().getPaymentId().getId();
        this.amount = transaction.getAmount();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.finishedAt = transaction.getFinishedAt();
        this.type = transaction.getType();
    }
}
