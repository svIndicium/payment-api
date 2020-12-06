package hu.indicium.dev.payment.domain.model.transaction.info;

import hu.indicium.dev.payment.domain.model.transaction.TransactionStatus;
import lombok.Getter;

@Getter
public abstract class BaseDetails {
    private TransactionStatus transactionStatus;
}
