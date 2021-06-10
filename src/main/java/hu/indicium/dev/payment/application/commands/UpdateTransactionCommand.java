package hu.indicium.dev.payment.application.commands;

import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UpdateTransactionCommand {
    private UUID paymentId;

    private UUID transactionId;

    private TransactionStatus transactionStatus;

    private String description;

    private Date transferredAt;

    private Double paid;
}
