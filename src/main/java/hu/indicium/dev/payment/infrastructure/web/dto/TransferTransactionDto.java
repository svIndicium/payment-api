package hu.indicium.dev.payment.infrastructure.web.dto;

import hu.indicium.dev.payment.domain.model.transaction.TransferTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransferTransactionDto extends TransactionDto {
    private String description;

    private Date transferredAt;

    public TransferTransactionDto(TransferTransaction transaction) {
        super(transaction);
        this.description = transaction.getDescription();
        this.transferredAt = transaction.getTransferredAt();
    }
}
