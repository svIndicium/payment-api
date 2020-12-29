package hu.indicium.dev.payment.domain.model.transaction.info;

import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public abstract class BaseDetails {
    private TransactionStatus transactionStatus;

    private Date transferredAt;

    public BaseDetails(TransactionStatus transactionStatus, Date transferredAt) {
        this.transactionStatus = transactionStatus;
        this.transferredAt = transferredAt;
    }
}
