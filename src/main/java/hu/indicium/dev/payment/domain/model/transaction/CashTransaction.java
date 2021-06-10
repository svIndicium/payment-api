package hu.indicium.dev.payment.domain.model.transaction;

import hu.indicium.dev.payment.domain.model.transaction.info.BaseDetails;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
public class CashTransaction extends Transaction {
    public CashTransaction(TransactionId transactionId, Double amount) {
        super(transactionId, amount);
        this.setStatus(TransactionStatus.PAID);
        this.setFinishedAt(new Date());
    }

    @Override
    public void updateTransaction(BaseDetails updatedTransactionInfo) {
        throw new IllegalStateException("Cash transactions can't be updated.");
    }

    @Override
    public TransactionType getType() {
        return TransactionType.CASH;
    }
}
