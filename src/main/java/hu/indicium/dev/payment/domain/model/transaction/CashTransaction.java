package hu.indicium.dev.payment.domain.model.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
public class CashTransaction extends Transaction {
    public CashTransaction(TransactionId transactionId, double amount) {
        this.setTransactionId(transactionId);
        this.setAmount(amount);
        this.setStatus(TransactionStatus.PAID);
        this.setFinishedAt(new Date());
    }
}
